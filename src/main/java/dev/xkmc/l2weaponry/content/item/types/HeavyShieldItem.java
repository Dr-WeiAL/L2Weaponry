package dev.xkmc.l2weaponry.content.item.types;

import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.l2weaponry.content.item.base.DoubleHandItem;
import dev.xkmc.l2weaponry.content.item.base.GenericShieldItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HeavyShieldItem extends GenericShieldItem implements DoubleHandItem {

	public HeavyShieldItem(Tier tier, int maxDefense, float recover, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config, maxDefense, recover, false);
	}

	@Override
	public InteractionResult useOn(UseOnContext pContext) {
		if (pContext.getPlayer() == null || disableOffHand(pContext.getPlayer(), pContext.getItemInHand()))
			return InteractionResult.CONSUME;
		return super.useOn(pContext);
	}

	@Override
	public boolean disableOffHand(Player player, ItemStack stack) {
		return !lightWeight(stack);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		if (!lightWeight(newStack)) {
			ItemStack off = Proxy.getClientPlayer().getOffhandItem().copy();
			Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().offHandItem = off;
			off.getOrCreateTag().putBoolean("reequip", true);
		}
		return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
	}

}
