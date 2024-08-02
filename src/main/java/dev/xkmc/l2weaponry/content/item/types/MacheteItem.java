package dev.xkmc.l2weaponry.content.item.types;

import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.base.BaseClawItem;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class MacheteItem extends BaseClawItem {

	public MacheteItem(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config);
	}

	@Override
	public void appendHoverText(ItemStack pStack, TooltipContext pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.TOOL_MACHETE.get());
		list.add(LangData.TOOL_MACHETE_EXTRA.get());
		super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
	}

}
