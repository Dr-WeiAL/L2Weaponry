package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2core.base.effects.EffectUtil;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.types.BattleAxeItem;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class FlameAxe extends BattleAxeItem implements LegendaryWeapon {

	public FlameAxe(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config);
	}

	@Override
	public void onHurt(DamageData.Offence event, LivingEntity le, ItemStack stack) {
		int time = LCConfig.SERVER.flameEnchantDuration.get();
		EffectUtil.addEffect(event.getTarget(), new MobEffectInstance(LCEffects.FLAME.holder(), time), le);
	}

	@Override
	public boolean isImmuneTo(DamageSource source) {
		return source.is(DamageTypeTags.IS_FIRE);
	}

	@Override
	public void appendHoverText(ItemStack pStack, TooltipContext pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.FLAME_AXE.get());
	}

}
