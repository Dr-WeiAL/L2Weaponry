package dev.xkmc.l2weaponry.events;

import dev.xkmc.l2library.init.events.attack.*;
import dev.xkmc.l2weaponry.content.entity.BaseThrownWeaponEntity;
import dev.xkmc.l2weaponry.content.item.base.BaseClawItem;
import dev.xkmc.l2weaponry.content.item.base.GenericWeaponItem;
import dev.xkmc.l2weaponry.content.item.legendary.LegendaryWeapon;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import java.util.Optional;

public class LWAttackEventListener implements AttackListener {

	@Override
	public void onCreateSource(CreateSourceEvent event) {
		if (event.getOriginal().equals(DamageTypes.MOB_ATTACK) || event.getOriginal().equals(DamageTypes.PLAYER_ATTACK)) {
			if (event.getAttacker().getMainHandItem().getItem() instanceof GenericWeaponItem item) {
				Entity target = Optional.of(event)
						.map(CreateSourceEvent::getPlayerAttackCache)
						.map(PlayerAttackCache::getPlayerAttackEntityEvent)
						.map(AttackEntityEvent::getTarget).orElse(null);
				item.modifySource(event.getAttacker(), event, event.getAttacker().getMainHandItem(), target);
			}
		} else if (event.getDirect() instanceof BaseThrownWeaponEntity<?> thrown) {
			if (thrown.getItem().getItem() instanceof GenericWeaponItem weapon) {
				if (thrown.getOwner() instanceof LivingEntity le) {
					weapon.modifySource(le, event, thrown.getItem(), thrown.targetCache);
				}
			}
		}
	}

	@Override
	public void onHurt(AttackCache cache, ItemStack stack) {
		LivingHurtEvent event = cache.getLivingHurtEvent();
		assert event != null;
		if (event.getSource().getDirectEntity() instanceof LivingEntity le) {
			if (!stack.isEmpty() && stack.getItem() instanceof GenericWeaponItem w) {
				if (le instanceof Player && cache.getCriticalHitEvent() != null) {
					if (cache.getStrength() < 0.7f) {
						cache.addHurtModifier(DamageModifier.nonlinearPost(10000, e -> 0.1f));
						return;
					}
				}
				cache.addHurtModifier(DamageModifier.multPost(w.getMultiplier(cache)));
			}
			if (!stack.isEmpty() && stack.getItem() instanceof BaseClawItem claw) {
				claw.accumulateDamage(stack, cache.getAttacker().getLevel().getGameTime());
			}
			if (stack.getItem() instanceof LegendaryWeapon weapon) {
				weapon.onHurt(cache, le);
			}
		} else if (event.getSource().getDirectEntity() instanceof BaseThrownWeaponEntity<?> thrown) {
			if (thrown.getItem().getItem() instanceof LegendaryWeapon weapon && thrown.getOwner() instanceof LivingEntity le) {
				weapon.onHurt(cache, le);
			}
		}
	}

	@Override
	public void onHurtMaximized(AttackCache cache, ItemStack stack) {
		LivingHurtEvent event = cache.getLivingHurtEvent();
		assert event != null;
		if (event.getSource().getDirectEntity() instanceof LivingEntity le) {
			if (stack.getItem() instanceof LegendaryWeapon weapon) {
				weapon.onHurtMaximized(cache, le);
			}
		} else if (event.getSource().getDirectEntity() instanceof BaseThrownWeaponEntity<?> thrown) {
			if (thrown.getItem().getItem() instanceof LegendaryWeapon weapon && thrown.getOwner() instanceof LivingEntity le) {
				weapon.onHurtMaximized(cache, le);
			}
		}
	}

	@Override
	public void onDamageFinalized(AttackCache cache, ItemStack stack) {
		LivingDamageEvent event = cache.getLivingDamageEvent();
		assert event != null;
		if (event.getSource().getDirectEntity() instanceof LivingEntity le) {
			if (stack.getItem() instanceof LegendaryWeapon weapon) {
				weapon.onDamageFinal(cache, le);
			}
		} else if (event.getSource().getDirectEntity() instanceof BaseThrownWeaponEntity<?> thrown) {
			if (thrown.getItem().getItem() instanceof LegendaryWeapon weapon && thrown.getOwner() instanceof LivingEntity le) {
				weapon.onDamageFinal(cache, le);
			}
		}
	}

}
