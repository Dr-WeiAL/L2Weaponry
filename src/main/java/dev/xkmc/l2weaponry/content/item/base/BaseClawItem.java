package dev.xkmc.l2weaponry.content.item.base;

import dev.xkmc.l2library.init.events.attack.AttackCache;
import dev.xkmc.l2library.init.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.init.L2WeaponryClient;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

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
		L2WeaponryClient.CLAW_DECO.add(this);
	}

	public void accumulateDamage(ItemStack stack, long gameTime) {
		long last = stack.getOrCreateTag().getLong(KEY_TIME);
		if (gameTime > last + LWConfig.COMMON.claw_timeout.get()) {
			stack.getOrCreateTag().putInt(KEY_COUNT, 1);
		} else {
			stack.getOrCreateTag().putInt(KEY_COUNT, stack.getOrCreateTag().getInt(KEY_COUNT) + 1);
		}
		stack.getOrCreateTag().putLong(KEY_TIME, gameTime);
	}

	public int getMaxStack(ItemStack stack, LivingEntity user) {
		int max = LWConfig.COMMON.claw_max.get();
		if (user.getOffhandItem().getItem() == this) {
			max *= 2;
		}
		return max;
	}

	@Override
	public float getMultiplier(AttackCache event) {
		int count = event.getWeapon().getOrCreateTag().getInt("hit_count");
		if (count > 1) {
			int max = getMaxStack(event.getWeapon(), event.getAttacker());
			return (float) (1 + LWConfig.COMMON.claw_bonus.get() * Mth.clamp(count - 1, 0, max));
		}
		return super.getMultiplier(event);
	}

}
