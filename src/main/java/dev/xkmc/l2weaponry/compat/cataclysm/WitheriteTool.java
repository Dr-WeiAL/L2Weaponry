package dev.xkmc.l2weaponry.compat.cataclysm;

import dev.xkmc.l2core.events.SchedulerHandler;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2damagetracker.init.data.L2DamageTypes;
import dev.xkmc.l2library.content.explosion.*;
import dev.xkmc.l2weaponry.content.entity.BaseThrownWeaponEntity;
import dev.xkmc.l2weaponry.content.item.base.GenericWeaponItem;
import dev.xkmc.l2weaponry.content.item.base.IExplosionSource;
import dev.xkmc.l2weaponry.content.item.types.*;
import dev.xkmc.l2weaponry.init.data.LangData;
import dev.xkmc.l2weaponry.init.materials.LWExtraConfig;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class WitheriteTool extends ExtraToolConfig implements LWExtraConfig {

	@Override
	public void onDamageFinal(DamageData.DefenceMax data, LivingEntity attacker, ItemStack stack) {
		if (data.getStrength() < 0.95) return;
		int vis = 1;
		if (!data.getSource().is(L2DamageTypes.DIRECT)) {
			if (!(data.getSource().getDirectEntity() instanceof BaseThrownWeaponEntity<?>))
				return;
			vis = 2;
		}
		if (!(stack.getItem() instanceof GenericWeaponItem w)) return;
		int radius = switch (w) {
			case BattleAxeItem ignored -> 3;
			case HammerItem ignored -> 3;
			case ScytheItem ignored -> 2;
			case JavelinItem ignored -> 2;
			case ThrowingAxeItem ignored -> 2;
			default -> 1;
		};
		if ((!attacker.onGround() || !w.canSweep()) && radius >= vis)
			vis = radius;
		var target = data.getTarget();
		var pos = target.position().add(0, target.getBbHeight() / 2, 0);
		if (attacker.level() instanceof ServerLevel sl) {
			sl.sendParticles(DustParticleOptions.REDSTONE, pos.x, pos.y, pos.z, radius * 5, 0, 0, 0, 0);
		}
		int visual = vis;
		SchedulerHandler.schedulePersistent(new ToolTicker(10,
				() -> makeExplosion(attacker, target, pos, radius, visual, stack))::tick);
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(LangData.MATS_WITHERITE.get());
	}

	private void makeExplosion(LivingEntity attacker, LivingEntity target, Vec3 pos, int radius, int visual, ItemStack stack) {
		BaseExplosionContext base = new BaseExplosionContext(target.level(), pos.x(), pos.y(), pos.z(), radius);
		Explosion.BlockInteraction type = Explosion.BlockInteraction.KEEP;
		VanillaExplosionContext mc = new VanillaExplosionContext(null, null, null, false, type);
		ModExplosionContext mod = (entity) -> this.onExplosionHurt(attacker, target, entity, stack);
		ExplosionHandler.explode(new BaseExplosion(base, mc, mod, ParticleExplosionContext.of(visual)));
	}

	private boolean onExplosionHurt(LivingEntity attacker, LivingEntity target, Entity entity, ItemStack stack) {
		boolean ans = shouldExplosionHurt(attacker, target, entity);
		if (ans) {
			if (stack.getItem() instanceof IExplosionSource s){
				s.onAffecting(attacker, entity, stack);
			}
		}
		return ans;
	}

	private boolean shouldExplosionHurt(LivingEntity attacker, LivingEntity target, Entity entity) {
		if (entity == attacker || entity.isAlliedTo(attacker) || attacker.isAlliedTo(entity))
			return false;
		if (entity == target) return true;
		if (entity instanceof LivingEntity le) {
			if (le.getLastHurtByMob() == attacker)
				return true;
			if (le instanceof Mob mob) {
				if (mob.getTarget() == attacker)
					return true;
			}
		}
		return false;
	}

}
