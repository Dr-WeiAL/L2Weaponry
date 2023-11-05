package dev.xkmc.l2weaponry.content.item.types;

import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.base.GenericWeaponItem;
import dev.xkmc.l2weaponry.events.ClientRenderEvents;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class NunchakuItem extends GenericWeaponItem {

	public NunchakuItem(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config) {
		super(tier, damage, speed, prop, config, BlockTags.MINEABLE_WITH_HOE);
		LWItems.NUNCHAKU_DECO.add(this);
	}

	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		if (pHand == InteractionHand.OFF_HAND)
			return InteractionResultHolder.pass(itemstack);
		pPlayer.startUsingItem(pHand);
		return InteractionResultHolder.consume(itemstack);
	}

	@Override
	public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remain) {
		if (level.isClientSide() && entity instanceof Player player) {
			ClientRenderEvents.onNunchakuUse(player, stack);
		}
	}

	public int getUseDuration(ItemStack p_43107_) {
		return 72000;
	}


}
