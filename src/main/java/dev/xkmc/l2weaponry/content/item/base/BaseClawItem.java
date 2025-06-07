package dev.xkmc.l2weaponry.content.item.base;

import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class BaseClawItem extends DoubleWieldItem {

	public BaseClawItem(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config) {
		super(tier, damage, speed, prop, config, BlockTags.MINEABLE_WITH_HOE);
	}

	@Override
	protected int getMaxStackIntrinsic(ItemStack stack) {
		return LWConfig.COMMON.claw_max.get();
	}

	@Override
	protected int getMaxStackUserBonus(int count, ItemStack stack, LivingEntity user) {
		if (user.getOffhandItem().getItem() == this) {
			count *= 2;
		}
		return count;
	}

	@Override
	public float getMultiplier(AttackCache event) {
		int count = event.getWeapon().getOrCreateTag().getInt(KEY_COUNT);
		if (count > 0) {
			int max = getMaxStack(event.getWeapon(), event.getAttacker());
			return (float) (1 + LWConfig.COMMON.claw_bonus.get() * Mth.clamp(count, 0, max));
		}
		return super.getMultiplier(event);
	}

}
