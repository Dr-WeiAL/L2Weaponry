package dev.xkmc.l2weaponry.events;

import dev.xkmc.l2complements.content.item.generic.GenericTieredItem;
import dev.xkmc.l2library.init.events.attack.AttackCache;
import dev.xkmc.l2library.init.events.attack.AttackListener;
import dev.xkmc.l2weaponry.content.entity.BaseThrownWeaponEntity;
import dev.xkmc.l2weaponry.content.item.base.BaseClawItem;
import dev.xkmc.l2weaponry.content.item.base.GenericWeaponItem;
import dev.xkmc.l2weaponry.content.item.base.LWTieredItem;
import dev.xkmc.l2weaponry.content.item.legendary.LegendaryWeapon;
import dev.xkmc.l2weaponry.content.item.types.PlateShieldItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class LWAttackEventListener implements AttackListener {

	@Override
	public void onAttack(AttackCache cache, ItemStack x) {
		var event = cache.getLivingAttackEvent();
		assert event != null;
		if (event.getSource().getDirectEntity() instanceof LivingEntity le) {
			if (le.getMainHandItem().getItem() instanceof LegendaryWeapon weapon) {
				weapon.modifySource(event.getSource(), le, event.getEntity(), le.getMainHandItem(), cache);
			}
		} else if (event.getSource().getDirectEntity() instanceof BaseThrownWeaponEntity<?> thrown) {
			if (thrown.getItem().getItem() instanceof LegendaryWeapon weapon) {
				if (thrown.getOwner() instanceof LivingEntity le) {
					weapon.modifySource(event.getSource(), le, event.getEntity(), thrown.getItem(), cache);
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
						cache.setDamageModified(0.1f);
						return;
					}
				}
			}
			if (!stack.isEmpty() && stack.getItem() instanceof LWTieredItem w) {
				cache.setDamageModified(cache.getDamageModified() * w.getMultiplier(cache));
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

	@Override
	public void onCriticalHit(AttackCache cache) {
		var event = cache.getCriticalHitEvent();
		assert event != null;
		if (!event.isVanillaCritical()) return;
		if (event.getEntity().getMainHandItem().getItem() instanceof PlateShieldItem) {
			event.setDamageModifier(event.getDamageModifier() * 2);
		}
	}
}
