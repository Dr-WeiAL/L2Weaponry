package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.types.ClawItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.data.LangData;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class CheaterClaw extends ClawItem implements LegendaryWeapon {

	public CheaterClaw(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config);
	}

	@Override
	public void onDamageFinal(DamageData.DefenceMax cache, LivingEntity le) {
		if (cache.getStrength() < 0.95f) return;
		if (cache.getTarget().hurtTime > 0) return;
		float diff = cache.getDamageIncoming() - cache.getDamageFinal();
		ItemStack stack = cache.getWeapon();
		LWItems.LAST_TARGET.set(stack, cache.getTarget().getUUID());
		double rate = LWConfig.SERVER.determinationRate.get();
		LWItems.DAMAGE_BONUS.set(stack, diff * (float) rate);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.CHEATER_CLAW.get());
	}

	@Override
	public void onHurt(DamageData.Offence event, LivingEntity le, ItemStack stack) {
		var target = LWItems.LAST_TARGET.get(stack);
		if (target != null && event.getTarget().getUUID().equals(target)) {
			event.addHurtModifier(DamageModifier.addExtra(LWItems.DAMAGE_BONUS.getOrDefault(stack, 0f), id()));
		}
	}
}
