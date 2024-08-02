package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2core.base.effects.EffectUtil;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.types.SpearItem;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class FrozenSpear extends SpearItem implements LegendaryWeapon {

	public FrozenSpear(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config);
	}

	@Override
	public void onHurt(DamageData.Offence event, LivingEntity le, ItemStack stack) {
		int time = LCConfig.SERVER.iceEnchantDuration.get();
		EffectUtil.addEffect(event.getTarget(), new MobEffectInstance(LCEffects.ICE.holder(), time), le);
	}

	@Override
	public boolean isImmuneTo(DamageSource source) {
		return source.is(DamageTypeTags.IS_FREEZING);
	}

	@Override
	public boolean cancelFreeze() {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack pStack, TooltipContext pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.FROZEN_SPEAR.get());
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
		if (!level.isClientSide && selected && entity instanceof LivingEntity le) {
			if (le.hasEffect(LCEffects.ICE.holder())) {
				le.removeEffect(LCEffects.ICE.holder());
			}
		}
	}
}
