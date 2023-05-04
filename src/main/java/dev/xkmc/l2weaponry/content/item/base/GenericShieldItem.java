package dev.xkmc.l2weaponry.content.item.base;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.xkmc.l2library.init.materials.generic.ExtraToolConfig;
import dev.xkmc.l2library.init.materials.generic.GenericTieredItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class GenericShieldItem extends BaseShieldItem implements GenericTieredItem {

	private final Tier tier;
	private final ExtraToolConfig config;

	public GenericShieldItem(Tier tier, Properties prop, ExtraToolConfig config, int maxDefense, double recover, boolean lightWeight) {
		super(prop.defaultDurability(tier.getUses()), maxDefense, 4 + recover, lightWeight);
		this.tier = tier;
		this.config = config;
	}

	public int getEnchantmentValue() {
		return this.tier.getEnchantmentValue();
	}

	public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
		return this.tier.getRepairIngredient().test(pRepair) || super.isValidRepairItem(pToRepair, pRepair);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, level, entity, slot, selected);
		config.inventoryTick(stack, level, entity, slot, selected);
	}

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
		return config.damageItem(stack, super.damageItem(stack, amount, entity, onBroken), entity);
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
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(stack, level, list, flag);
		config.addTooltip(stack, list);
	}

}
