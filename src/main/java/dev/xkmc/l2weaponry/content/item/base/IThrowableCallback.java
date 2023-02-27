package dev.xkmc.l2weaponry.content.item.base;

import dev.xkmc.l2weaponry.content.entity.BaseThrownWeaponEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IThrowableCallback {

	default void onHitBlock(BaseThrownWeaponEntity<?> entity, ItemStack item) {

	}

	default void onHitEntity(BaseThrownWeaponEntity<?> entity, ItemStack item, LivingEntity le) {

	}

}
