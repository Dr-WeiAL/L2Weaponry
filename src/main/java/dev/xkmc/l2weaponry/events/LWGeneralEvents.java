package dev.xkmc.l2weaponry.events;

import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import dev.xkmc.l2weaponry.content.enchantments.AttributeEnchantment;
import dev.xkmc.l2weaponry.content.entity.BaseThrownWeaponEntity;
import dev.xkmc.l2weaponry.content.item.legendary.LegendaryWeapon;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = L2Weaponry.MODID, bus = EventBusSubscriber.Bus.GAME)
public class LWGeneralEvents {

	@SubscribeEvent
	public static void onItemAttribute(ItemAttributeModifierEvent event) {
		ItemStack stack = event.getItemStack();
		if (stack.isEnchanted()) {
			LegacyEnchantment.findAll(stack, AttributeEnchantment.class, false)
					.forEach(e -> e.val().addAttributes(e.lv(), event));
		}
	}

	@SubscribeEvent
	public static void onDeath(LivingDeathEvent event) {
		if (event.getSource().getDirectEntity() instanceof LivingEntity le) {
			ItemStack stack = le.getMainHandItem();
			if (stack.getItem() instanceof LegendaryWeapon weapon) {
				weapon.onKill(stack, event.getEntity(), le);
			}
		} else if (event.getSource().getDirectEntity() instanceof BaseThrownWeaponEntity<?> e) {
			if (e.getItem().getItem() instanceof LegendaryWeapon weapon) {
				if (event.getSource().getEntity() instanceof LivingEntity le) {
					weapon.onKill(e.getItem(), event.getEntity(), le);
				}
			}
		}
	}


}
