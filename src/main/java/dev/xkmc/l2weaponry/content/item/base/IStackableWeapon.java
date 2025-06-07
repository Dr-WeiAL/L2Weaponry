package dev.xkmc.l2weaponry.content.item.base;

import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface IStackableWeapon {

	String KEY_COUNT = "hit_count", KEY_TIME = "last_hit_time";

	static int getHitCount(ItemStack stack) {
		return stack.getOrCreateTag().getInt(KEY_COUNT);
	}

	static long getLastTime(ItemStack stack) {
		return stack.getOrCreateTag().getLong(KEY_TIME);
	}

	default void register(Item item) {
		LWItems.CLAW_DECO.add(item);
	}

	default int getMaxStack(ItemStack stack, @Nullable LivingEntity user) {
		return 0;
	}

	default void accumulateDamage(ItemStack stack, LivingEntity entity) {
		long gameTime = entity.level().getGameTime();
		long last = stack.getOrCreateTag().getLong(KEY_TIME);
		if (gameTime > last + LWConfig.COMMON.claw_timeout.get()) {
			stack.getOrCreateTag().putInt(KEY_COUNT, 1);
		} else {
			int count = stack.getOrCreateTag().getInt(KEY_COUNT);
			count = Math.min(count + 1, getMaxStack(stack, entity));
			stack.getOrCreateTag().putInt(KEY_COUNT, count);
		}
		stack.getOrCreateTag().putLong(KEY_TIME, gameTime);
	}

	default void tick(ItemStack stack, Entity entity) {
		if (getMaxStack(stack, null) <= 0) return;
		long gameTime = entity.level().getGameTime();
		long last = stack.getOrCreateTag().getLong(KEY_TIME);
		if (gameTime > last + LWConfig.COMMON.claw_timeout.get()) {
			stack.getOrCreateTag().remove(KEY_COUNT);
			stack.getOrCreateTag().remove(KEY_TIME);
		}
	}

}
