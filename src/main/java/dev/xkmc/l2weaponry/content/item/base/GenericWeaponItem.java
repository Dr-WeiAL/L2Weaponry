package dev.xkmc.l2weaponry.content.item.base;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2complements.content.item.generic.GenericTieredItem;
import dev.xkmc.l2library.init.events.attack.AttackCache;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class GenericWeaponItem extends WeaponItem implements GenericTieredItem {

	private final ExtraToolConfig config;

	public GenericWeaponItem(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config, TagKey<Block> blocks) {
		super(tier, damage, speed, prop, blocks);
		this.config = config;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
		config.inventoryTick(stack, level, entity, slot, selected);
		super.inventoryTick(stack, level, entity, slot, selected);
	}

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
		return config.damageItem(stack, amount, entity);
	}

	@Override
	public boolean canBeDepleted() {
		return config.canBeDepleted;
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity user) {
		config.onHit(stack, target, user);
		if (config.sword_hit > 0)
			stack.hurtAndBreak(config.sword_hit, user, (level) -> level.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		return true;
	}

	@Override
	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
		if (config.sword_mine > 0 && state.getDestroySpeed(level, pos) != 0.0F) {
			stack.hurtAndBreak(config.sword_mine, entity, (l) -> l.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}
		return true;
	}

	@Override
	public ExtraToolConfig getExtraConfig() {
		return config;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
		var parent = super.getAttributeModifiers(slot, stack);
		if (slot != EquipmentSlot.MAINHAND) return parent;
		Multimap<Attribute, AttributeModifier> cur = HashMultimap.create();
		cur.putAll(parent);
		return config.modify(cur, slot, stack);
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		float old = super.getDestroySpeed(stack, state);
		return config.getDestroySpeed(stack, state, old);
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
		config.addTooltip(pStack, pTooltipComponents);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
		if (toolAction == ToolActions.SWORD_SWEEP) return canSweep();
		return false;
	}

	public float getMultiplier(AttackCache event) {
		return 1;
	}

	protected boolean isSharp() {
		return true;
	}

	protected boolean canSweep() {
		return false;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		if (enchantment == Enchantments.SWEEPING_EDGE) {
			return canSweep();
		}
		if (enchantment == Enchantments.SHARPNESS) {
			return isSharp();
		}
		if (enchantment.category == EnchantmentCategory.WEAPON) {
			return true;
		}
		return super.canApplyAtEnchantingTable(stack, enchantment);
	}

}
