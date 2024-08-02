package dev.xkmc.l2weaponry.compat.twilightforest;

import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.base.BaseShieldItem;
import dev.xkmc.l2weaponry.content.item.base.GenericWeaponItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.data.LangData;
import dev.xkmc.l2weaponry.init.materials.LWExtraConfig;
import dev.xkmc.l2weaponry.init.materials.LWToolTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.List;

public class FieryTool extends ExtraToolConfig implements LWExtraConfig {

	@Override
	public void addEnchants(List<EnchantmentInstance> list, LWToolTypes type, Item tool) {
		if (tool instanceof GenericWeaponItem) {
			list.add(new EnchantmentInstance(LCEnchantments.SMELT.get(), 1));
		}
	}

	private static MobEffectInstance getEffect() {
		return new MobEffectInstance(MobEffects.FIRE_RESISTANCE, LWConfig.SERVER.fieryDuration.get() * 20);
	}

	@Override
	public void onShieldBlock(ItemStack stack, LivingEntity user, LivingEntity entity) {
		if (!entity.fireImmune())
			entity.setSecondsOnFire(LWConfig.SERVER.fieryDuration.get());
		user.addEffect(getEffect());
	}

	@Override
	public void onDamage(AttackCache cache, ItemStack stack) {
		if (!cache.getAttackTarget().fireImmune()) {
			double bonus = LWConfig.SERVER.fieryBonus.get();
			cache.addHurtModifier(DamageModifier.multBase((float) bonus));
			cache.getAttackTarget().setSecondsOnFire(LWConfig.SERVER.fieryDuration.get());
		}
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		double bonus = LWConfig.SERVER.knightmetalBonus.get();
		list.add(LangData.MATS_FIERY.get((int) Math.round(bonus * 100), LWConfig.SERVER.fieryDuration.get()));
		if (stack.getItem() instanceof BaseShieldItem) {
			list.add(LangData.MATS_EFFECT.get(LangData.getTooltip(getEffect())));
		}
	}

}
