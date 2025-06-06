package dev.xkmc.l2weaponry.content.item.base;

import net.minecraft.world.entity.LivingEntity;

public interface IAttackBlockingWeapon extends IStackableWeapon {

	default float getBlockTime(LivingEntity player) {
		return 0;
	}

}
