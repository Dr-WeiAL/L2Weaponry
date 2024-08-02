package dev.xkmc.l2weaponry.content.enchantments;

import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2weaponry.content.item.base.BaseThrowableWeaponItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

public class HeavyEnchantment extends LegacyEnchantment implements AttributeEnchantment {

	@Override
	public void addAttributes(int level, ItemAttributeModifierEvent event) {
		event.addModifier(Attributes.ATTACK_SPEED,
				new AttributeModifier(id(), -LWConfig.SERVER.heavySpeedReduction.get() * level,
						AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
		event.addModifier(L2DamageTracker.CRIT_DMG.holder(),
				new AttributeModifier(id(), LWConfig.SERVER.heavyCritBonus.get() * level,
						AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
		if (event.getItemStack().getItem() instanceof BaseThrowableWeaponItem) {
			event.addModifier(L2DamageTracker.BOW_STRENGTH.holder(),
					new AttributeModifier(id(), LWConfig.SERVER.heavyCritBonus.get() * level,
							AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
		}

	}

}
