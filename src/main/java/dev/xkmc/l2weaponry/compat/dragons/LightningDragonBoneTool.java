package dev.xkmc.l2weaponry.compat.dragons;

import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class LightningDragonBoneTool extends ExtraToolConfig {

	@Override
	public void onHit(ItemStack stack, LivingEntity target, LivingEntity user) {
		super.onHit(stack, target, user);
		IAFProxy.get().lightningHit(stack, target, user);
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		IAFProxy.get().lightningDesc(stack, list);
	}
}
