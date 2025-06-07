package dev.xkmc.l2weaponry.init.materials;

import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2weaponry.content.entity.BaseThrownWeaponEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface LWExtraConfig {

	default void onShieldBlock(ItemStack stack, LivingEntity user, LivingEntity entity) {

	}

	default double onShieldReflect(ItemStack stack, LivingEntity user, LivingEntity entity, double original, double reflect) {
		return reflect;
	}

	default void addEnchants(List<LWToolTypes.DefaultEnch> list, LWToolTypes type, Item tool) {
	}

	@Nullable
	default DamageSource getReflectSource(Player player) {
		return null;
	}

	default int getExtraStacking(ItemStack stack, @Nullable LivingEntity user) {
		return 0;
	}

	default void onDamageFinal(DamageData.OffenceMax data, LivingEntity le, ItemStack stack){

	}

	default void onHitBlock(BaseThrownWeaponEntity<?> entity, ItemStack stack) {

	}

	default void onHitEntity(BaseThrownWeaponEntity<?> entity, ItemStack stack, LivingEntity target) {

	}

}
