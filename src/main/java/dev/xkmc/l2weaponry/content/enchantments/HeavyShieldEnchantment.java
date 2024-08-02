package dev.xkmc.l2weaponry.content.enchantments;

import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

public class HeavyShieldEnchantment extends LegacyEnchantment implements AttributeEnchantment {

	@Override
	public void addAttributes(int level, ItemAttributeModifierEvent event) {
		event.addModifier(Attributes.MOVEMENT_SPEED, new AttributeModifier(id(),
				-LWConfig.SERVER.heavyShieldSpeedReduction.get() * level,
				AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.MAINHAND);
		event.addModifier(LWItems.SHIELD_DEFENSE.holder(), new AttributeModifier(id(),
				LWConfig.SERVER.heavyShieldDefenseBonus.get() * level,
				AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.MAINHAND);
	}

}
