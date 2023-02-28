package dev.xkmc.l2weaponry.events;

import dev.xkmc.l2weaponry.content.item.legendary.LegendaryWeapon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LegendaryWeaponEvents {

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onAttackPre(LivingAttackEvent event) {
		if (event.getSource().getDirectEntity() instanceof LivingEntity le) {
			if (le.getMainHandItem().getItem() instanceof LegendaryWeapon weapon) {
				weapon.modifySource(event.getSource(), le, event.getEntity());
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onAttackPost(LivingAttackEvent event) {
		if (event.getEntity().getMainHandItem().getItem() instanceof LegendaryWeapon weapon) {
			if (weapon.isImmuneTo(event.getSource())) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onHurtPre(LivingHurtEvent event) {
		if (event.getSource().getDirectEntity() instanceof LivingEntity le) {
			if (le.getMainHandItem().getItem() instanceof LegendaryWeapon weapon) {
				weapon.onHurt(event);
			}
		}
	}

	@SubscribeEvent
	public static void onCriticalHit(CriticalHitEvent event) {
		if (event.isVanillaCritical() && event.getEntity().getMainHandItem().getItem() instanceof LegendaryWeapon weapon) {
			if (!event.getEntity().level.isClientSide)
				weapon.onCrit(event.getEntity(), event.getTarget());
		}
	}

}
