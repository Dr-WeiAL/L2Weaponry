package dev.xkmc.l2weaponry.content.enchantments;

import dev.xkmc.l2complements.content.enchantment.core.AttributeEnchantment;
import dev.xkmc.l2complements.content.enchantment.core.UnobtainableEnchantment;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2weaponry.content.item.base.BaseClawItem;
import dev.xkmc.l2weaponry.content.item.types.MacheteItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class RaisedSpiritEnchantment extends BaseMacheteEnchantment implements AttributeEnchantment {

	private static final String NAME_SPEED = "raised_spirit_speed";

	private static final UUID ID_SPEED = MathHelper.getUUIDFromString(NAME_SPEED);

	public RaisedSpiritEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
		super(pRarity, pCategory, pApplicableSlots);
	}

	@Override
	public void addAttributes(int level, ItemAttributeModifierEvent event) {
		if (event.getSlotType() == EquipmentSlot.MAINHAND) {
			if (event.getItemStack().getItem() instanceof MacheteItem) {
				double bonus = LWConfig.COMMON.raisedSpiritSpeedBonus.get() *
						BaseClawItem.getHitCount(event.getItemStack()) * level;
				event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(ID_SPEED, NAME_SPEED, bonus,
						AttributeModifier.Operation.ADDITION));
			}
		}
	}

}
