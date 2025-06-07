package dev.xkmc.l2weaponry.content.item.base;

import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeMod;

public class DoubleWieldItem extends GenericWeaponItem {

	public DoubleWieldItem(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config, TagKey<Block> blocks) {
		super(tier, damage, speed, prop, config, blocks);
	}

	@Override
	protected final boolean canSweep() {
		return true;
	}

	@Override
	public AABB getSweepHitBoxImpl(ItemStack stack, LivingEntity player, Entity target) {
		double r = player.getAttributeValue(ForgeMod.ENTITY_REACH.get());
		if (player.getOffhandItem().getItem() == this) {
			return player.getBoundingBox().inflate(r + 1, r + 0.25, r + 1);
		} else {
			return super.getSweepHitBoxImpl(stack, player, target);
		}
	}

}
