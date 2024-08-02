package dev.xkmc.l2weaponry.content.item.base;

import dev.xkmc.l2core.init.reg.ench.EnchHelper;
import dev.xkmc.l2library.init.FlagMarker;
import dev.xkmc.l2weaponry.content.entity.BaseThrownWeaponEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.Vec3;

public interface IThrowableCallback {

	default void onHitBlock(BaseThrownWeaponEntity<?> entity, ItemStack item) {
		if (causeThunder(entity)) {
			thunderHit(entity);
		}
	}

	default void onHitEntity(BaseThrownWeaponEntity<?> entity, ItemStack item, LivingEntity le) {
		if (causeThunder(entity)) {
			thunderHit(entity);
		}
	}

	default boolean causeThunder(BaseThrownWeaponEntity<?> entity) {
		return entity.level().isThundering() &&
				entity.level().canSeeSky(entity.blockPosition()) &&
				EnchHelper.getLv(entity.getItem(), Enchantments.CHANNELING) > 0;
	}

	static void thunderHit(BaseThrownWeaponEntity<?> entity) {
		if (entity.level().isClientSide) return;
		BlockPos blockpos = entity.blockPosition();
		LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(entity.level());
		assert bolt != null;
		bolt.addTag(FlagMarker.LIGHTNING);
		bolt.moveTo(Vec3.atBottomCenterOf(blockpos));
		bolt.setCause(entity.getOwner() instanceof ServerPlayer ? (ServerPlayer) entity.getOwner() : null);
		entity.level().addFreshEntity(bolt);
		entity.playSound(SoundEvents.TRIDENT_THUNDER.value(), 5, 1);
	}

}
