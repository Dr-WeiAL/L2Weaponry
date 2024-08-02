package dev.xkmc.l2weaponry.content.item.types;

import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.base.BaseClawItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.data.LangData;
import dev.xkmc.l2weaponry.init.registrate.LWEnchantments;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ClawItem extends BaseClawItem {

	public ClawItem(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config);
	}

	@Override
	public float getBlockTime(LivingEntity player) {
		int ans = LWConfig.SERVER.claw_block_time.get();
		ans += LWEnchantments.CLAW_BLOCK.getLv(player.getMainHandItem());
		if (player.getOffhandItem().getItem() == this) {
			ans += LWEnchantments.CLAW_BLOCK.getLv(player.getOffhandItem());
		}
		return ans;
	}

	@Override
	public void appendHoverText(ItemStack pStack, TooltipContext pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.TOOL_CLAW.get());
		list.add(LangData.TOOL_CLAW_EXTRA.get());
		super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
	}

}
