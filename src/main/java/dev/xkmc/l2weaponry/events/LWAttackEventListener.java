package dev.xkmc.l2weaponry.events;

import dev.xkmc.l2complements.content.enchantment.core.SourceModifierEnchantment;
import dev.xkmc.l2damagetracker.contents.attack.*;
import dev.xkmc.l2damagetracker.contents.materials.generic.GenericTieredItem;
import dev.xkmc.l2weaponry.content.entity.BaseThrownWeaponEntity;
import dev.xkmc.l2weaponry.content.item.base.DoubleWieldItem;
import dev.xkmc.l2weaponry.content.item.base.LWTieredItem;
import dev.xkmc.l2weaponry.content.item.legendary.LegendaryWeapon;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;

import java.util.Optional;
import java.util.function.BiConsumer;

public class LWAttackEventListener implements AttackListener {

	@Override
	public void onCreateSource(CreateSourceEvent event) {
		if (event.getOriginal().equals(DamageTypes.MOB_ATTACK) || event.getOriginal().equals(DamageTypes.PLAYER_ATTACK)) {
			ItemStack stack = event.getAttacker().getMainHandItem();
			if (stack.getItem() instanceof LWTieredItem item) {
				Entity target = Optional.of(event)
						.map(CreateSourceEvent::getPlayerAttackCache)
						.map(PlayerAttackCache::getPlayerAttackEntityEvent)
						.map(AttackEntityEvent::getTarget).orElse(null);
				item.modifySource(event.getAttacker(), event, event.getAttacker().getMainHandItem(), target);
			}
		} else if (event.getDirect() instanceof BaseThrownWeaponEntity<?> thrown) {
			if (thrown.getItem().getItem() instanceof LWTieredItem weapon) {
				if (thrown.getOwner() instanceof LivingEntity le) {
					weapon.modifySource(le, event, thrown.getItem(), thrown.targetCache);
					SourceModifierEnchantment.modifySource(thrown.getItem(), event);
				}
			}
		}
	}

	@Override
	public void setupProfile(AttackCache cache, BiConsumer<LivingEntity, ItemStack> setup) {
		if (cache.getLivingAttackEvent().getSource().getDirectEntity() instanceof BaseThrownWeaponEntity<?> thrown) {
			if (thrown.getOwner() instanceof LivingEntity le) {
				setup.accept(le, thrown.getItem());
			}
		}
	}

	@Override
	public void onHurt(AttackCache cache, ItemStack stack) {
		LivingHurtEvent event = cache.getLivingHurtEvent();
		assert event != null;
		if (event.getSource().getDirectEntity() instanceof LivingEntity le && le == cache.getAttacker()) {
			if (!stack.isEmpty() && stack.getItem() instanceof GenericTieredItem) {
				if (le instanceof Player && cache.getCriticalHitEvent() != null) {
					if (cache.getStrength() < 0.7f) {
						cache.addHurtModifier(DamageModifier.nonlinearFinal(10000, f -> 0.1f));
						return;
					}
				}
			}
			if (!stack.isEmpty() && stack.getItem() instanceof DoubleWieldItem claw) {
				claw.accumulateDamage(stack, cache.getAttacker().level().getGameTime());
			}
		}
		if (!stack.isEmpty() && stack.getItem() instanceof LWTieredItem w) {
			cache.addHurtModifier(DamageModifier.multAttr(w.getMultiplier(cache)));
		}
		if (cache.getAttacker() != null && stack.getItem() instanceof LegendaryWeapon weapon) {
			weapon.onHurt(cache, cache.getAttacker(), stack);
		}
	}

	@Override
	public void onHurtMaximized(AttackCache cache, ItemStack stack) {
		LivingHurtEvent event = cache.getLivingHurtEvent();
		assert event != null;
		if (cache.getAttacker() != null && stack.getItem() instanceof LegendaryWeapon weapon) {
			weapon.onHurtMaximized(cache, cache.getAttacker());
		}
	}

	@Override
	public void onDamageFinalized(AttackCache cache, ItemStack stack) {
		LivingDamageEvent event = cache.getLivingDamageEvent();
		assert event != null;
		if (cache.getAttacker() != null && stack.getItem() instanceof LegendaryWeapon weapon) {
			weapon.onDamageFinal(cache, cache.getAttacker());
		}
	}

	@Override
	public boolean onCriticalHit(PlayerAttackCache cache, CriticalHitEvent event) {
		if (!event.isVanillaCritical() && event.getResult() != Event.Result.ALLOW) return false;
		if (event.getEntity().level().isClientSide()) return false;
		if (cache.getWeapon().getItem() instanceof LegendaryWeapon weapon) {
			weapon.onCrit(event.getEntity(), event.getTarget());
		}
		return false;
	}

}
