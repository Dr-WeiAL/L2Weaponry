package dev.xkmc.l2weaponry.content.item.base;

import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class GenericWeaponItem extends WeaponItem implements LWTieredItem {

	private final ExtraToolConfig config;

	public GenericWeaponItem(Tier tier, Properties prop, ExtraToolConfig config, TagKey<Block> tags) {
		super(tier, tier.createToolProperties(tags), prop);
		this.config = config;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
		config.inventoryTick(stack, level, entity, slot, selected);
	}

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken) {
		return config.damageItem(stack, amount, entity);
	}

	@Override
	public ExtraToolConfig getExtraConfig() {
		return config;
	}

	@Override
	public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
		var parent = super.getDefaultAttributeModifiers(stack);
		var b = ItemAttributeModifiers.builder();
		for (var e : parent.modifiers()) b.add(e.attribute(), e.modifier(), e.slot());
		config.modifyDynamicAttributes(b, stack);
		return b.build();
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		float old = super.getDestroySpeed(stack, state);
		return config.getDestroySpeed(stack, state, old);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
		config.addTooltip(stack, list);
	}

	public boolean isSharp() {
		return true;
	}

	protected boolean canSweep() {
		return false;
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ItemAbility toolAction) {
		if (toolAction == ItemAbilities.SWORD_DIG) return true;
		if (toolAction == ItemAbilities.SWORD_SWEEP) return canSweep();
		return false;
	}

}
