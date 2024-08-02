package dev.xkmc.l2weaponry.content.item.base;

import dev.xkmc.l2core.util.Proxy;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.client.Minecraft;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class SlowWieldItem extends GenericWeaponItem implements DoubleHandItem {

	public SlowWieldItem(Tier tier, Properties prop, ExtraToolConfig config, TagKey<Block> blocks) {
		super(tier, prop, config, blocks);
	}

	@Override
	public InteractionResult useOn(UseOnContext pContext) {
		if (pContext.getPlayer() == null || disableOffHand(pContext.getPlayer(), pContext.getItemInHand()))
			return InteractionResult.CONSUME;
		return super.useOn(pContext);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
		if (disableOffHand(pPlayer, pPlayer.getItemInHand(pUsedHand)))
			return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
		return super.use(pLevel, pPlayer, pUsedHand);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		ItemStack off = Proxy.getClientPlayer().getOffhandItem().copy();
		Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().offHandItem = off;
		LWItems.REEQUIP.set(off, Unit.INSTANCE);
		return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
	}

	@Override
	public boolean isHeavy() {
		return true;
	}

	@Override
	public boolean isSwordLike() {
		return false;
	}

}
