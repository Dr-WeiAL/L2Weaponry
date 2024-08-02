package dev.xkmc.l2weaponry.content.item.types;

import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.base.GenericWeaponItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class DaggerItem extends GenericWeaponItem {

	public DaggerItem(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config, BlockTags.MINEABLE_WITH_HOE);
	}

	public float getMultiplier(DamageData.Offence event) {
		return event.getTarget() instanceof Mob le && le.getTarget() != event.getAttacker() ? (float) (double) LWConfig.SERVER.dagger_bonus.get() : 1;
	}

	@Override
	public void appendHoverText(ItemStack pStack, TooltipContext pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.TOOL_DAGGER.get());
		super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
	}

}
