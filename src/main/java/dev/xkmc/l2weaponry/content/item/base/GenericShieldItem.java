package dev.xkmc.l2weaponry.content.item.base;

import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.init.materials.LWExtraConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public abstract class GenericShieldItem extends BaseShieldItem implements LWTieredItem {

	private final Tier tier;
	private final ExtraToolConfig config;

	public GenericShieldItem(Tier tier, Properties prop, ExtraToolConfig config, boolean lightWeight) {
		super(prop.durability(tier.getUses()), lightWeight);
		this.tier = tier;
		this.config = config;
	}

	@Override
	public boolean isHeavy() {
		return !lightWeight;
	}

	public int getEnchantmentValue(ItemStack stack) {
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
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken) {
		return config.damageItem(stack, super.damageItem(stack, amount, entity, onBroken), entity);
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
	public ExtraToolConfig getExtraConfig() {
		return config;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(stack, level, list, flag);
		config.addTooltip(stack, list);
	}

	public void onBlock(ItemStack stack, LivingEntity user, LivingEntity target) {
		if (getExtraConfig() instanceof LWExtraConfig extra) {
			extra.onShieldBlock(stack, user, target);
		}
	}

	public double onReflect(ItemStack stack, LivingEntity user, LivingEntity target, double original, double reflect) {
		if (getExtraConfig() instanceof LWExtraConfig extra) {
			return extra.onShieldReflect(stack, user, target, original, reflect);
		}
		return reflect;
	}

	@Override
	protected DamageSource getReflectSource(Player player) {
		if (getExtraConfig() instanceof LWExtraConfig extra) {
			DamageSource ans = extra.getReflectSource(player);
			if (ans != null) {
				return ans;
			}
		}
		return super.getReflectSource(player);
	}

}
