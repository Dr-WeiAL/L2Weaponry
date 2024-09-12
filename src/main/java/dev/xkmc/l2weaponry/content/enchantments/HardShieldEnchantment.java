package dev.xkmc.l2weaponry.content.enchantments;

import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

public class HardShieldEnchantment extends AttributeDescEnchantment {

	public HardShieldEnchantment() {
		super(new Entry(
				LWItems.SHIELD_DEFENSE,
				LWConfig.SERVER.hardShieldDefenseBonus,
				AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
				EquipmentSlotGroup.HAND
		));
	}

}
