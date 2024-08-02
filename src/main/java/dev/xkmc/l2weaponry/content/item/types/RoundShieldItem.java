package dev.xkmc.l2weaponry.content.item.types;

import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2library.content.raytrace.FastItem;
import dev.xkmc.l2weaponry.content.item.base.GenericShieldItem;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class RoundShieldItem extends GenericShieldItem implements FastItem {

	public RoundShieldItem(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config, true);
	}

	@Override
	public boolean isFast(ItemStack itemStack) {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack pStack, TooltipContext pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.TOOL_ROUND_SHIELD.get());
		super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
	}

	@Override
	public double getDefenseRecover(ItemStack stack) {
		return 1/2f;
	}

}
