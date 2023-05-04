package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2library.init.events.attack.AttackCache;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface LegendaryWeapon {

	default boolean isImmuneTo(DamageSource source) {
		return false;
	}

	default void onHurt(AttackCache event, LivingEntity le) {

	}

	default boolean cancelFreeze() {
		return false;
	}

	default void onCrit(Player entity, Entity target) {

	}

	default void onDamageFinal(AttackCache cache, LivingEntity le) {

	}

	default void onKill(ItemStack stack, LivingEntity target, LivingEntity user) {

	}

	default void onHurtMaximized(AttackCache cache, LivingEntity le) {

	}
}
