package dev.xkmc.l2weaponry.events;

import dev.xkmc.l2weaponry.content.entity.BaseThrownWeaponEntity;
import dev.xkmc.l2weaponry.content.item.legendary.LegendaryWeapon;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = L2Weaponry.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LegendaryWeaponEvents {

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onAttackPost(LivingAttackEvent event) {
		if (event.getEntity().getMainHandItem().getItem() instanceof LegendaryWeapon weapon) {
			if (weapon.isImmuneTo(event.getSource())) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onDeath(LivingDeathEvent event) {
		if (event.getSource().getDirectEntity() instanceof LivingEntity le) {
			ItemStack stack = le.getMainHandItem();
			if (stack.getItem() instanceof LegendaryWeapon weapon) {
				weapon.onKill(stack, event.getEntity(), le);
			}
		} else if (event.getSource().getDirectEntity() instanceof BaseThrownWeaponEntity e) {
			if (e.getItem().getItem() instanceof LegendaryWeapon weapon) {
				if (event.getSource().getEntity() instanceof LivingEntity le) {
					weapon.onKill(e.getItem(), event.getEntity(), le);
				}
			}
		}
	}

}
