package dev.xkmc.l2weaponry.content.item.types;

import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2library.util.raytrace.FastItem;
import dev.xkmc.l2weaponry.content.item.base.GenericShieldItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class LightShieldItem extends GenericShieldItem implements FastItem {

	public LightShieldItem(Tier tier, int maxDefense, float recover, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config, maxDefense, recover, true);
	}

	@Override
	public boolean isFast(ItemStack itemStack) {
		return true;
	}

}
