package dev.xkmc.l2weaponry.compat.dragons;

import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class IceDragonBoneTool extends ExtraToolConfig {

	@Override
	public void onDamage(DamageData.Offence cache, ItemStack stack) {
		super.onDamage(cache, stack);
		var user = cache.getAttacker();
		if (user == null) return;
		IAFProxy.get().iceHit(stack, cache.getTarget(), user);
	}


	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		IAFProxy.get().iceDesc(stack, list);
	}

}
