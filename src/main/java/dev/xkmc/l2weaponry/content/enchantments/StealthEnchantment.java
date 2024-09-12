package dev.xkmc.l2weaponry.content.enchantments;

import dev.xkmc.l2complements.content.enchantment.core.SourceModifierEnchantment;
import dev.xkmc.l2core.init.reg.ench.CustomDescEnchantment;
import dev.xkmc.l2core.init.reg.ench.EnchColor;
import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import dev.xkmc.l2damagetracker.contents.attack.CreateSourceEvent;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.data.LWDamageStates;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class StealthEnchantment extends LegacyEnchantment implements SourceModifierEnchantment, CustomDescEnchantment {

	@Override
	public void modify(CreateSourceEvent event, ItemStack stack, int level) {
		double chance = LWConfig.SERVER.stealthChance.get() * level;
		if (event.getAttacker().getRandom().nextDouble() < chance) {
			event.enable(LWDamageStates.NO_ANGER);
		}
	}

	@Override
	public List<Component> descFull(int lv, String key, boolean alt, boolean book, EnchColor color) {
		double chance = LWConfig.SERVER.stealthChance.get() * lv;
		var comp = Component.literal(Math.round(chance * 100) + "").withStyle(ChatFormatting.AQUA);
		return List.of(Component.translatable(key, comp).withStyle(color.desc()));
	}

}
