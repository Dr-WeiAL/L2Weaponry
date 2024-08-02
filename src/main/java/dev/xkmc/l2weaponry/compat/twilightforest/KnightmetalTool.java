package dev.xkmc.l2weaponry.compat.twilightforest;

import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.base.BaseShieldItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.data.LangData;
import dev.xkmc.l2weaponry.init.materials.LWExtraConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class KnightmetalTool extends ExtraToolConfig implements LWExtraConfig {

	@Override
	public double onShieldReflect(ItemStack stack, LivingEntity user, LivingEntity target, double original, double reflect) {
		return reflect + original * LWConfig.SERVER.knightmetalReflect.get();
	}

	@Override
	public void onDamage(AttackCache cache, ItemStack stack) {
		if (cache.getAttackTarget().getAttribute(Attributes.ARMOR) != null) {
			double bonus = LWConfig.SERVER.knightmetalBonus.get();
			double armor = cache.getAttackTarget().getAttributeValue(Attributes.ARMOR);
			cache.addHurtModifier(DamageModifier.addExtra((float) (bonus * armor)));
		}
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		double bonus = LWConfig.SERVER.knightmetalBonus.get();
		list.add(LangData.MATS_KNIGHTMETAL.get((int) Math.round(bonus * 100)));
		if (stack.getItem() instanceof BaseShieldItem) {
			double reflect = LWConfig.SERVER.knightmetalReflect.get();
			list.add(LangData.MATS_REFLECT.get((int) Math.round(reflect * 100)));
		}
	}

}
