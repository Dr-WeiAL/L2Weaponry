package dev.xkmc.l2weaponry.content.item.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WeaponItem extends TieredItem {

	public WeaponItem(Tier tier, Tool tool, Item.Properties properties) {
		super(tier, properties.component(DataComponents.TOOL, tool));
	}

	public boolean isSwordLike() {
		return true;
	}

	public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
		return !isSwordLike() || !pPlayer.isCreative();
	}

	public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
		return true;
	}

	@Override
	public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity user) {
		stack.hurtAndBreak(1, user, EquipmentSlot.MAINHAND);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ItemAbility ability) {
		return ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(ability);
	}

	public AABB getSweepHitBoxImpl(ItemStack stack, LivingEntity attacker, Entity target) {
		return target.getBoundingBox().inflate(1.0D, 0.25D, 1.0D);
	}

	@Override
	public final @NotNull AABB getSweepHitBox(@NotNull ItemStack stack, @NotNull Player player, @NotNull Entity target) {
		return getSweepHitBoxImpl(stack, player, target);
	}

}