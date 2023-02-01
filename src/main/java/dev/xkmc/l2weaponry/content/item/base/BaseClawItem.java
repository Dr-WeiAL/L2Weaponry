package dev.xkmc.l2weaponry.content.item.base;

import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2library.init.events.attack.AttackCache;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class BaseClawItem extends DoubleWieldItem {

	public BaseClawItem(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config) {
		super(tier, damage, speed, prop, config, BlockTags.MINEABLE_WITH_HOE);
	}

	public void accumulateDamage(ItemStack stack, long gameTime) {
		long last = stack.getOrCreateTag().getLong("last_hit_time");
		if (gameTime > last + LWConfig.COMMON.claw_timeout.get()) {
			stack.getOrCreateTag().putInt("hit_count", 1);
		} else {
			stack.getOrCreateTag().putInt("hit_count", stack.getOrCreateTag().getInt("hit_count") + 1);
		}
		stack.getOrCreateTag().putLong("last_hit_time", gameTime);
	}

	@Override
	public float getMultiplier(AttackCache event) {
		int count = event.getWeapon().getOrCreateTag().getInt("hit_count");
		if (count > 1) {
			int max = LWConfig.COMMON.claw_max.get();
			if (event.getAttacker().getOffhandItem().getItem() == this) {
				max *= 2;
			}
			return (float) (1 + LWConfig.COMMON.claw_bonus.get() * Mth.clamp(count - 1, 0, max));
		}
		return super.getMultiplier(event);
	}

}
