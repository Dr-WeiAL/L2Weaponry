package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2library.base.effects.EffectUtil;
import dev.xkmc.l2library.init.events.attack.AttackCache;
import dev.xkmc.l2library.init.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.types.MacheteItem;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnderMachete extends MacheteItem implements LegendaryWeapon {

	public EnderMachete(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config) {
		super(tier, damage, speed, prop, config);
	}

	@Override
	public void onHurt(AttackCache event, LivingEntity le, ItemStack stack) {
		EffectUtil.addEffect(event.getAttackTarget(), new MobEffectInstance(MobEffects.LEVITATION, 20), EffectUtil.AddReason.NONE, le);
		EffectUtil.addEffect(event.getAttackTarget(), new MobEffectInstance(MobEffects.SLOW_FALLING, 40), EffectUtil.AddReason.NONE, le);
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.ENDER_MACHETE.get());
	}

}
