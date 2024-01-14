package dev.xkmc.l2weaponry.init.materials;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import javax.annotation.Nullable;
import java.util.List;

public interface LWExtraConfig {

	default void onShieldBlock(ItemStack stack, LivingEntity user, LivingEntity entity) {

	}

	default double onShieldReflect(ItemStack stack, LivingEntity user, LivingEntity entity, double original, double reflect) {
		return reflect;
	}

	default void addEnchants(List<EnchantmentInstance> list, LWToolTypes type, Item tool) {
	}

	@Nullable
	default DamageSource getReflectSource(Player player) {
		return null;
	}
}
