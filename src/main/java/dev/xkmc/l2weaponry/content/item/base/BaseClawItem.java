package dev.xkmc.l2weaponry.content.item.base;

import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class BaseClawItem extends DoubleWieldItem {

	private static final String KEY_COUNT = "hit_count", KEY_TIME = "last_hit_time";

	public static int getHitCount(ItemStack stack) {
		return stack.getOrCreateTag().getInt(KEY_COUNT);
	}

	public static long getLastTime(ItemStack stack) {
		return stack.getOrCreateTag().getLong(KEY_TIME);
	}

	public BaseClawItem(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config) {
		super(tier, damage, speed, prop, config, BlockTags.MINEABLE_WITH_HOE);
		LWItems.CLAW_DECO.add(this);
	}

	public void accumulateDamage(ItemStack stack, LivingEntity entity) {
		long gameTime = entity.level().getGameTime();
		long last = stack.getOrCreateTag().getLong(KEY_TIME);
		if (gameTime > last + LWConfig.SERVER.claw_timeout.get()) {
			stack.getOrCreateTag().putInt(KEY_COUNT, 1);
		} else {
			int count = stack.getOrCreateTag().getInt(KEY_COUNT);
			count = Math.min(count + 1, getMaxStack(stack, entity));
			stack.getOrCreateTag().putInt(KEY_COUNT, count);
		}
		stack.getOrCreateTag().putLong(KEY_TIME, gameTime);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, level, entity, slot, selected);
		long gameTime = entity.level().getGameTime();
		long last = stack.getOrCreateTag().getLong(KEY_TIME);
		if (gameTime > last + LWConfig.SERVER.claw_timeout.get()) {
			stack.getOrCreateTag().remove(KEY_COUNT);
			stack.getOrCreateTag().remove(KEY_TIME);
		}
	}

	public int getMaxStack(ItemStack stack, LivingEntity user) {
		int max = LWConfig.SERVER.claw_max.get();
		if (user.getOffhandItem().getItem() == this) {
			max *= 2;
		}
		return max;
	}

	@Override
	public float getMultiplier(DamageData.Offence event) {
		int count = event.getWeapon().getOrCreateTag().getInt(KEY_COUNT);
		if (count > 1) {
			int max = getMaxStack(event.getWeapon(), event.getAttacker());
			return (float) (1 + LWConfig.SERVER.claw_bonus.get() * Mth.clamp(count - 1, 0, max));
		}
		return super.getMultiplier(event);
	}

	public float getBlockTime(LivingEntity player) {
		return 0;
	}

}
