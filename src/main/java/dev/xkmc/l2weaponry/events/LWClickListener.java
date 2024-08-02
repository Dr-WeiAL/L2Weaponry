package dev.xkmc.l2weaponry.events;

import dev.xkmc.l2complements.events.ItemUseEventHandler;
import dev.xkmc.l2weaponry.content.item.base.DoubleWieldItem;
import dev.xkmc.l2weaponry.content.item.base.WeaponItem;
import dev.xkmc.l2weaponry.init.registrate.LWEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class LWClickListener implements ItemUseEventHandler.ItemClickHandler {

	@Override
	public boolean predicate(ItemStack itemStack, Class<? extends PlayerEvent> aClass, PlayerEvent playerEvent) {
		return itemStack.getItem() instanceof WeaponItem;
	}

	@Override
	public void onPlayerLeftClickEmpty(ItemStack stack, PlayerInteractEvent.LeftClickEmpty event) {
		if (!event.getLevel().isClientSide()) {
			if (stack.getItem() instanceof DoubleWieldItem item) {
				doubleWeldSwing(event.getEntity(), item, stack);
			}
		}
	}

	@Override
	public void onPlayerLeftClickBlock(ItemStack stack, PlayerInteractEvent.LeftClickBlock event) {
		if (!event.getLevel().isClientSide()) {
			if (stack.getItem() instanceof DoubleWieldItem item) {
				doubleWeldSwing(event.getEntity(), item, stack);
			}
		}
	}

	private static void doubleWeldSwing(Player player, DoubleWieldItem item, ItemStack stack) {
		if (LWEnchantments.GHOST_SLASH.getLv(stack) > 0 && player.getAttackStrengthScale(0) >= 0.9) {
			item.accumulateDamage(stack, player);
			if (!player.getAbilities().instabuild) {
				stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
			}
		}
	}

}
