package dev.xkmc.l2weaponry.content.item.legendary;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public interface LegendaryWeapon {

	default boolean isImmuneTo(DamageSource source) {
		return false;
	}

	default void modifySource(DamageSource source, LivingEntity player, LivingEntity target) {

	}

	default void onHurt(LivingHurtEvent event) {

	}

	default boolean cancelFreeze() {
		return false;
	}

	default void onCrit(Player entity, Entity target) {

	}
}
