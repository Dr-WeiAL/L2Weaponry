package dev.xkmc.l2weaponry.content.item.types;

import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.base.GenericWeaponItem;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class SpearItem extends GenericWeaponItem {

	public SpearItem(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config, BlockTags.MINEABLE_WITH_SHOVEL);
	}

	@Override
	protected boolean canSweep() {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack pStack, TooltipContext pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.TOOL_SPEAR.get());
		super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
	}

	@Override
	public AABB getSweepHitBoxImpl(ItemStack stack, LivingEntity player, Entity target) {
		return target.getBoundingBox().inflate(3.0D, 0.5D, 3.0D);
	}

}
