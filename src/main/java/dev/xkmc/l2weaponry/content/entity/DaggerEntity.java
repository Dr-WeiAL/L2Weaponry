package dev.xkmc.l2weaponry.content.entity;

import dev.xkmc.l2weaponry.init.registrate.LWEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DaggerEntity extends BaseThrownWeaponEntity<DaggerEntity> {

	public DaggerEntity(EntityType<DaggerEntity> type, Level pLevel) {
		super(type, pLevel);
	}

	public DaggerEntity(Level pLevel, LivingEntity pShooter, ItemStack pStack, int slot) {
		super(LWEntities.ET_DAGGER.get(), pLevel, pShooter, pStack, slot);
	}

}
