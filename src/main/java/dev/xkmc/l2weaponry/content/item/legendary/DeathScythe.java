package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.types.ScytheItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class DeathScythe extends ScytheItem implements LegendaryWeapon {

	public DeathScythe(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config);
	}

	@Override
	public void onHurt(DamageData.Offence event, LivingEntity le, ItemStack stack) {
		if (event.getStrength() < 0.9) return;
		float health = event.getTarget().getHealth();
		float max = event.getTarget().getMaxHealth();
		float factor = (float) (double) LWConfig.SERVER.deathScytheMax.get();
		event.addHurtModifier(DamageModifier.multTotal(1 + factor * (1 - health / max), id()));
	}

	@Override
	public void appendHoverText(ItemStack pStack, TooltipContext pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		int max = (int) Math.round(LWConfig.SERVER.deathScytheMax.get() * 100);
		list.add(LangData.DEATH_SCYTHE.get(max));
	}

}
