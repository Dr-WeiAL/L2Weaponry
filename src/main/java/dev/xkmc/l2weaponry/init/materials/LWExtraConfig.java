package dev.xkmc.l2weaponry.init.materials;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface LWExtraConfig {

	default void onShieldBlock(ItemStack stack, LivingEntity user, LivingEntity entity) {

	}

	default double onShieldReflect(ItemStack stack, LivingEntity user, LivingEntity entity, double original, double reflect) {
		return reflect;
	}

	default ItemStack getDefaultStack(Item item) {
		return item.getDefaultInstance();
	}

}
