package dev.xkmc.l2weaponry.content.enchantments;

import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

public class HardShieldEnchantment extends LegacyEnchantment implements AttributeEnchantment {

	@Override
	public void addAttributes(int level, ItemAttributeModifierEvent event) {
		event.addModifier(LWItems.SHIELD_DEFENSE.holder(),
				new AttributeModifier(id(EquipmentSlot.MAINHAND),
						LWConfig.SERVER.hardShieldDefenseBonus.get() * level,
						AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
				EquipmentSlotGroup.MAINHAND);
		event.addModifier(LWItems.SHIELD_DEFENSE.holder(),
				new AttributeModifier(id(EquipmentSlot.OFFHAND),
						LWConfig.SERVER.hardShieldDefenseBonus.get() * level,
						AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
				EquipmentSlotGroup.OFFHAND);
	}


}
