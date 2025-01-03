package dev.xkmc.l2weaponry.compat.undergarden;

import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.init.materials.LWExtraConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import quek.undergarden.registry.UGEffects;

import java.util.List;

public class FroststeelTool extends ExtraToolConfig implements LWExtraConfig {

	@Override
	public void onHurt(DamageData.Offence cache, LivingEntity attacker, ItemStack stack) {
		cache.getTarget().addEffect(new MobEffectInstance(UGEffects.CHILLY, 600, 2, false, false));
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(Component.translatable("tooltip.undergarden.froststeel_weapon").withStyle(ChatFormatting.AQUA));
	}

}
