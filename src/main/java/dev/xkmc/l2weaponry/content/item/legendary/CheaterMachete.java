package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.types.MacheteItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.data.LangData;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class CheaterMachete extends MacheteItem implements LegendaryWeapon {

	public CheaterMachete(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.CHEATER_MACHETE.get());
	}

	@Override
	public void onHurt(DamageData.Offence cache, LivingEntity le, ItemStack stack) {
		var target = LWItems.LAST_TARGET.get(stack);
		if (target != null && cache.getTarget().getUUID().equals(target)) {
			float lost = cache.getTarget().getMaxHealth() - cache.getTarget().getHealth();
			float acc = LWItems.DAMAGE_BONUS.getOrDefault(stack, 0f);
			if (lost < acc) {
				double factor = LWConfig.SERVER.illusionRate.get();
				cache.addHurtModifier(DamageModifier.addExtra((float) factor * (acc - lost), id()));
			}
		}
	}

	@Override
	public void onHurtMaximized(DamageData.OffenceMax cache, LivingEntity le) {
		if (cache.getStrength() < 0.95f) return;
		if (cache.getTarget().hurtTime > 0) return;
		ItemStack stack = cache.getWeapon();
		var target = LWItems.LAST_TARGET.get(stack);
		if (target != null && cache.getTarget().getUUID().equals(target)) {
			float damage = LWItems.DAMAGE_BONUS.getOrDefault(stack, 0f);
			LWItems.DAMAGE_BONUS.set(stack, damage + cache.getDamageIncoming());
		} else {
			LWItems.DAMAGE_BONUS.set(stack, cache.getDamageIncoming());
		}
		LWItems.LAST_TARGET.set(stack, cache.getTarget().getUUID());

	}
}
