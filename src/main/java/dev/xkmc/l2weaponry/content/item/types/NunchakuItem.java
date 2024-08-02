package dev.xkmc.l2weaponry.content.item.types;

import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2library.content.raytrace.FastItem;
import dev.xkmc.l2weaponry.content.item.base.GenericWeaponItem;
import dev.xkmc.l2weaponry.events.ClientRenderEvents;
import dev.xkmc.l2weaponry.init.data.LangData;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NunchakuItem extends GenericWeaponItem implements FastItem {

	public NunchakuItem(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config, BlockTags.MINEABLE_WITH_HOE);
		LWItems.NUNCHAKU_DECO.add(this);
	}

	public static boolean check(@Nullable LivingEntity entity, ItemStack stack) {
		if (entity == null) return false;
		if (!entity.isUsingItem()) return false;
		if (entity.getMainHandItem() != stack) return false;
		return entity.getUseItem() == stack ||
				entity.getUseItem().canPerformAction(ItemAbilities.SHIELD_BLOCK);
	}

	public static boolean delegate(Player player) {
		return player.getOffhandItem().canPerformAction(ItemAbilities.SHIELD_BLOCK)
				&& !player.getCooldowns().isOnCooldown(player.getOffhandItem().getItem());
	}

	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (hand == InteractionHand.OFF_HAND || delegate(player))
			return InteractionResultHolder.pass(itemstack);
		player.startUsingItem(hand);
		return InteractionResultHolder.consume(itemstack);
	}

	@Override
	public void onUseTick(Level level, LivingEntity le, ItemStack stack, int remain) {
		if (le instanceof Player player && delegate(player))
			le.stopUsingItem();
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
		if (selected && level.isClientSide() && entity instanceof Player player && player.isUsingItem()) {
			ClientRenderEvents.onNunchakuUse(player, stack);
		}
	}

	@Override
	public void appendHoverText(ItemStack pStack, TooltipContext pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.TOOL_NUNCHAKU.get());
		super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
	}

	public int getUseDuration(ItemStack stack, LivingEntity le) {
		return 72000;
	}

	@Override
	public boolean isSharp() {
		return false;
	}

	@Override
	public boolean isFast(ItemStack itemStack) {
		return true;
	}

	public ModelResourceLocation roll() {
		return ModelResourceLocation.standalone(BuiltInRegistries.ITEM.getKey(this).withPath(e -> "item/" + e + "_roll"));
	}

	public ModelResourceLocation unroll() {
		return ModelResourceLocation.standalone(BuiltInRegistries.ITEM.getKey(this).withPath(e -> "item/" + e + "_unroll"));
	}

}
