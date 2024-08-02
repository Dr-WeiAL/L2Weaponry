package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2core.base.effects.EffectUtil;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.types.MacheteItem;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class EnderMachete extends MacheteItem implements LegendaryWeapon {

	public EnderMachete(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config);
	}

	@Override
	public void onHurt(DamageData.Offence event, LivingEntity le, ItemStack stack) {
		EffectUtil.addEffect(event.getTarget(), new MobEffectInstance(MobEffects.LEVITATION, 20), le);
		EffectUtil.addEffect(event.getTarget(), new MobEffectInstance(MobEffects.SLOW_FALLING, 40), le);
	}

	@Override
	public void appendHoverText(ItemStack pStack, TooltipContext pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.ENDER_MACHETE.get());
	}

}
