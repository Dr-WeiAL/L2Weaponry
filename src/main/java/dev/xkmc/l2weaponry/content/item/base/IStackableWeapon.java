package dev.xkmc.l2weaponry.content.item.base;

import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface IStackableWeapon {

	static int getHitCount(ItemStack stack) {
		return LWItems.HIT_COUNT.getOrDefault(stack, 0);
	}

	static long getLastTime(ItemStack stack) {
		return LWItems.HIT_TIME.getOrDefault(stack, 0L);
	}

	default void register(Item item) {
		LWItems.CLAW_DECO.add(item);
	}

	default int getMaxStack(ItemStack stack, @Nullable LivingEntity user) {
		return 0;
	}

	default void accumulateDamage(ItemStack stack, LivingEntity entity) {
		int max = getMaxStack(stack, entity);
		if (max <= 0) return;
		long gameTime = entity.level().getGameTime();
		long last = getLastTime(stack);
		if (gameTime > last + LWConfig.SERVER.claw_timeout.get()) {
			LWItems.HIT_COUNT.set(stack, 1);
		} else {
			int count = getHitCount(stack);
			count = Math.min(count + 1, max);
			LWItems.HIT_COUNT.set(stack, count);
		}
		LWItems.HIT_TIME.set(stack, gameTime);
	}

	default void tick(ItemStack stack, Entity entity) {
		if (getMaxStack(stack, null) <= 0) return;
		long gameTime = entity.level().getGameTime();
		long last = getLastTime(stack);
		if (gameTime > last + LWConfig.SERVER.claw_timeout.get()) {
			stack.remove(LWItems.HIT_COUNT);
			stack.remove(LWItems.HIT_TIME);
		}
	}

}
