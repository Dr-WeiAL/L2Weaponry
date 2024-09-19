package dev.xkmc.l2weaponry.content.enchantments;

import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2weaponry.content.item.base.BaseThrowableWeaponItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class HeavyEnchantment extends AttributeDescEnchantment {

	public HeavyEnchantment() {
		super(new AttributeDescEnchantment.Entry(
				Attributes.ATTACK_SPEED,
				() -> -LWConfig.SERVER.heavySpeedReduction.get(),
				AttributeModifier.Operation.ADD_VALUE,
				EquipmentSlotGroup.MAINHAND
		), new AttributeDescEnchantment.Entry(
				L2DamageTracker.CRIT_DMG,
				LWConfig.SERVER.heavyCritBonus,
				AttributeModifier.Operation.ADD_VALUE,
				EquipmentSlotGroup.MAINHAND
		), new AttributeDescEnchantment.Entry(
				L2DamageTracker.BOW_STRENGTH,
				LWConfig.SERVER.heavyCritBonus,
				AttributeModifier.Operation.ADD_VALUE,
				EquipmentSlotGroup.MAINHAND,
				e -> e.getItem() instanceof BaseThrowableWeaponItem
		));
	}

}
