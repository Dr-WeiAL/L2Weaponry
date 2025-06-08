package dev.xkmc.l2weaponry.content.item.base;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IExplosionSource {

	void onAffecting(LivingEntity attacker, Entity entity, ItemStack stack);

}
