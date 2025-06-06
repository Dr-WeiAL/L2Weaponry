package dev.xkmc.l2weaponry.content.item.base;

import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class BaseClawItem extends DoubleWieldItem {

	public BaseClawItem(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config, BlockTags.MINEABLE_WITH_HOE);
	}

	@Override
	protected int getMaxStackUserBonus(int count, ItemStack stack, LivingEntity user) {
		if (user.getOffhandItem().getItem() == this) return count * 2;
		return count;
	}

	@Override
	protected int getMaxStackIntrinsic(ItemStack stack) {
		return LWConfig.SERVER.claw_max.get();
	}

	@Override
	public float getMultiplier(DamageData.Offence event) {
		int count = IStackableWeapon.getHitCount(event.getWeapon());
		var attacker = event.getAttacker();
		if (count > 1 && attacker != null) {
			int max = getMaxStack(event.getWeapon(), attacker);
			return (float) (1 + LWConfig.SERVER.claw_bonus.get() * Mth.clamp(count - 1, 0, max));
		}
		return super.getMultiplier(event);
	}

}
