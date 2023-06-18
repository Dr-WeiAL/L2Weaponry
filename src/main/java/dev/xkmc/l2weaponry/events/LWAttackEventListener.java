package dev.xkmc.l2weaponry.events;

import dev.xkmc.l2library.init.events.attack.*;
import dev.xkmc.l2library.init.materials.generic.GenericTieredItem;
import dev.xkmc.l2weaponry.content.entity.BaseThrownWeaponEntity;
import dev.xkmc.l2weaponry.content.item.base.BaseClawItem;
import dev.xkmc.l2weaponry.content.item.base.GenericWeaponItem;
import dev.xkmc.l2weaponry.content.item.base.LWTieredItem;
import dev.xkmc.l2weaponry.content.item.legendary.LegendaryWeapon;
import dev.xkmc.l2weaponry.content.item.types.PlateShieldItem;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;

import java.util.Optional;

public class LWAttackEventListener implements AttackListener {

	@Override
	public void onCreateSource(CreateSourceEvent event) {
		if (event.getOriginal().equals(DamageTypes.MOB_ATTACK) || event.getOriginal().equals(DamageTypes.PLAYER_ATTACK)) {
			if (event.getAttacker().getMainHandItem().getItem() instanceof LWTieredItem item) {
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
				}
			}
		}
	}

	@Override
	public void onHurt(AttackCache cache, ItemStack stack) {
		LivingHurtEvent event = cache.getLivingHurtEvent();
		assert event != null;
		if (event.getSource().getDirectEntity() instanceof LivingEntity le) {
			if (!stack.isEmpty() && stack.getItem() instanceof GenericTieredItem) {
				if (le instanceof Player && cache.getCriticalHitEvent() != null) {
					if (cache.getStrength() < 0.7f) {
						cache.addHurtModifier(DamageModifier.nonlinearPost(10000, f -> 0.1f));
						return;
					}
				}
			}
			if (!stack.isEmpty() && stack.getItem() instanceof LWTieredItem w) {
				cache.addHurtModifier(DamageModifier.multPre(w.getMultiplier(cache)));
			}
			if (!stack.isEmpty() && stack.getItem() instanceof BaseClawItem claw) {
				claw.accumulateDamage(stack, cache.getAttacker().getLevel().getGameTime());
			}
			if (stack.getItem() instanceof LegendaryWeapon weapon) {
				weapon.onHurt(cache, le, stack);
			}
		} else if (event.getSource().getDirectEntity() instanceof BaseThrownWeaponEntity<?> thrown) {
			if (thrown.getOwner() instanceof LivingEntity le) {
				Item item = thrown.getItem().getItem();
				if (item instanceof GenericTieredItem tiered) {
					tiered.getExtraConfig().onDamage(cache, thrown.getItem());
				}
				if (thrown.getItem().getItem() instanceof LegendaryWeapon weapon) {
					weapon.onHurt(cache, le, thrown.getItem());
				}
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

	@Override
	public boolean onCriticalHit(PlayerAttackCache cache, CriticalHitEvent event) {
		if (!event.isVanillaCritical()) return false;
		if (event.getEntity().getMainHandItem().getItem() instanceof PlateShieldItem) {
			event.setDamageModifier(event.getDamageModifier() * 2);
		}
		return false;
	}

}
