package dev.xkmc.l2weaponry.content.item.base;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class WeaponItem extends TieredItem {

	protected float attackDamage, attackSpeed;
	/**
	 * Modifiers applied when the item is in the mainhand of a user.
	 */
	private final Multimap<Attribute, AttributeModifier> defaultModifiers;
	private final TagKey<Block> blocks;

	public WeaponItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties, TagKey<Block> blocks) {
		super(pTier, pProperties);
		this.blocks = blocks;
		this.attackDamage = pAttackDamageModifier;
		this.attackSpeed = pAttackSpeedModifier;
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		addModifiers(builder);
		this.defaultModifiers = builder.build();
	}

	protected void addModifiers(ImmutableMultimap.Builder<Attribute, AttributeModifier> builder) {
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));

	}

	public boolean isSwordLike() {
		return true;
	}

	public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
		return !isSwordLike() || !pPlayer.isCreative();
	}


	public float getDestroySpeed(ItemStack pStack, BlockState pState) {
		if (isSwordLike() && pState.is(Blocks.COBWEB)) {
			return 15;
		}
		return pState.is(this.blocks) ? this.getTier().getSpeed() : 1.0F;
	}

	/**
	 * Check whether this Item can harvest the given Block
	 */
	@Deprecated // FORGE: Use stack sensitive variant below
	public boolean isCorrectToolForDrops(BlockState pBlock) {
		if (net.minecraftforge.common.TierSortingRegistry.isTierSorted(getTier())) {
			return net.minecraftforge.common.TierSortingRegistry.isCorrectTierForDrops(getTier(), pBlock) && pBlock.is(this.blocks);
		}
		int i = this.getTier().getLevel();
		if (i < 3 && pBlock.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
			return false;
		} else if (i < 2 && pBlock.is(BlockTags.NEEDS_IRON_TOOL)) {
			return false;
		} else {
			return (i >= 1 || !pBlock.is(BlockTags.NEEDS_STONE_TOOL)) && pBlock.is(this.blocks);
		}
	}

	// FORGE START
	@Override
	public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
		return state.is(blocks) && net.minecraftforge.common.TierSortingRegistry.isCorrectTierForDrops(getTier(), state);
	}

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
		pStack.hurtAndBreak(1, pAttacker, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		return true;
	}

	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
	 */
	public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
		if (pState.getDestroySpeed(pLevel, pPos) != 0.0F) {
			pStack.hurtAndBreak(1, pEntityLiving, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}
		return true;
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
		return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
	}

	public AABB getSweepHitBoxImpl(ItemStack stack, LivingEntity attacker, Entity target) {
		return target.getBoundingBox().inflate(1.0D, 0.25D, 1.0D);
	}

	@Override
	public final @NotNull AABB getSweepHitBox(@NotNull ItemStack stack, @NotNull Player player, @NotNull Entity target) {
		return getSweepHitBoxImpl(stack, player, target);
	}

}