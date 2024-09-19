package dev.xkmc.l2weaponry.content.enchantments;

import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class HeavyShieldEnchantment extends AttributeDescEnchantment {

	public HeavyShieldEnchantment() {
		super(new AttributeDescEnchantment.Entry(
				Attributes.MOVEMENT_SPEED,
				() -> -LWConfig.SERVER.heavyShieldSpeedReduction.get(),
				AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
				EquipmentSlotGroup.MAINHAND
		), new AttributeDescEnchantment.Entry(
				LWItems.SHIELD_DEFENSE,
				LWConfig.SERVER.heavyShieldDefenseBonus,
				AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
				EquipmentSlotGroup.MAINHAND
		));
	}

}
