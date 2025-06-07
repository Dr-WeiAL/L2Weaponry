package dev.xkmc.l2weaponry.compat.cataclysm;

import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2damagetracker.init.data.L2DamageTypes;
import dev.xkmc.l2library.init.events.GeneralEventHandler;
import dev.xkmc.l2library.init.explosion.*;
import dev.xkmc.l2weaponry.content.entity.BaseThrownWeaponEntity;
import dev.xkmc.l2weaponry.content.item.base.GenericWeaponItem;
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
	public void onDamageFinal(AttackCache data, LivingEntity le, ItemStack stack) {
		if (data.getStrength() < 0.95) return;
		var event = data.getLivingDamageEvent();
		assert event != null;
		if (!event.getSource().is(L2DamageTypes.DIRECT)) {
			if (!(event.getSource().getDirectEntity() instanceof BaseThrownWeaponEntity<?> e))
				return;
			stack = e.getItem();
		}
		if (!(stack.getItem() instanceof GenericWeaponItem w)) return;
		int radius = 1;
		if (w instanceof BattleAxeItem || w instanceof HammerItem)
			radius = 3;
		else if (w instanceof ScytheItem || w instanceof JavelinItem || w instanceof ThrowingAxeItem)
			radius = 2;
		var target = data.getAttackTarget();
		var pos = target.position().add(0, target.getBbHeight() / 2, 0);
		if (le.level() instanceof ServerLevel sl) {
			sl.sendParticles(DustParticleOptions.REDSTONE, pos.x, pos.y, pos.z, radius * 5, 0, 0, 0, 0);
		}
		int r = radius;
		GeneralEventHandler.schedulePersistent(new Ticker(10, () -> makeExplosion(le, target, pos, r))::tick);
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(LangData.MATS_WITHERITE.get());
	}

	private void makeExplosion(LivingEntity attacker, LivingEntity target, Vec3 pos, int radius) {
		BaseExplosionContext base = new BaseExplosionContext(target.level(), pos.x(), pos.y(), pos.z(), radius);
		Explosion.BlockInteraction type = Explosion.BlockInteraction.KEEP;
		VanillaExplosionContext mc = new VanillaExplosionContext(null, null, null, false, type);
		ModExplosionContext mod = (entity) -> this.onExplosionHurt(attacker, target, entity);
		ExplosionHandler.explode(new BaseExplosion(base, mc, mod));
	}

	private boolean onExplosionHurt(LivingEntity attacker, LivingEntity target, Entity entity) {
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

	private class Ticker {

		private int time;
		private final Runnable task;

		private Ticker(int time, Runnable task) {
			this.time = time;
			this.task = task;
		}

		public boolean tick() {
			time--;
			if (time == 0) task.run();
			return time <= 0;
		}

	}

}
