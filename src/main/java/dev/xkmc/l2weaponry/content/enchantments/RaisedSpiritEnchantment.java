package dev.xkmc.l2weaponry.content.enchantments;

import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import dev.xkmc.l2weaponry.content.item.base.BaseClawItem;
import dev.xkmc.l2weaponry.content.item.types.MacheteItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

public class RaisedSpiritEnchantment extends LegacyEnchantment implements AttributeEnchantment {

	@Override
	public void addAttributes(int level, ItemAttributeModifierEvent event) {
		if (event.getItemStack().getItem() instanceof MacheteItem) {
			double bonus = LWConfig.SERVER.raisedSpiritSpeedBonus.get() *
					BaseClawItem.getHitCount(event.getItemStack()) * level;
			event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(id(), bonus,
					AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
		}
	}

}
