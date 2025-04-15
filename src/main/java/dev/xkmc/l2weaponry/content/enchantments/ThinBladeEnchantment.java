package dev.xkmc.l2weaponry.content.enchantments;

import dev.xkmc.l2weaponry.init.data.LWConfig;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ThinBladeEnchantment extends AttributeDescEnchantment {

	public ThinBladeEnchantment() {
		super(new Entry(
				Attributes.ATTACK_SPEED,
				LWConfig.SERVER.thinBladeAttackSpeedBonus,
				AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
				EquipmentSlotGroup.MAINHAND
		), new Entry(
				Attributes.ATTACK_DAMAGE,
				() -> -LWConfig.SERVER.thinBladeAttackReduction.getAsDouble(),
				AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
				EquipmentSlotGroup.MAINHAND
		));
	}

}
