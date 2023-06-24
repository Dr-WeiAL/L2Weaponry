package dev.xkmc.l2weaponry.content.enchantments;

import dev.xkmc.l2complements.content.enchantment.core.AttributeEnchantment;
import dev.xkmc.l2complements.content.enchantment.core.UnobtainableEnchantment;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class HardShieldEnchantment extends UnobtainableEnchantment implements AttributeEnchantment {

	private static final String NAME_DEF_MAIN = "hardshield_mainhand";
	private static final String NAME_DEF_OFF = "hardshield_offhand";

	private static final UUID ID_DEF_MAIN = MathHelper.getUUIDFromString(NAME_DEF_MAIN);
	private static final UUID ID_DEF_OFF = MathHelper.getUUIDFromString(NAME_DEF_OFF);

	public HardShieldEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
		super(pRarity, pCategory, pApplicableSlots);
	}

	@Override
	public void addAttributes(int level, ItemAttributeModifierEvent event) {
		if (event.getSlotType() == EquipmentSlot.MAINHAND) {
			event.addModifier(LWItems.SHIELD_DEFENSE.get(), new AttributeModifier(ID_DEF_MAIN, NAME_DEF_MAIN, LWConfig.COMMON.hardShieldDefenseBonus.get() * level, AttributeModifier.Operation.MULTIPLY_BASE));
		}
		if (event.getSlotType() == EquipmentSlot.OFFHAND) {
			event.addModifier(LWItems.SHIELD_DEFENSE.get(), new AttributeModifier(ID_DEF_OFF, NAME_DEF_OFF, LWConfig.COMMON.hardShieldDefenseBonus.get() * level, AttributeModifier.Operation.MULTIPLY_BASE));
		}
	}

	public int getMinLevel() {
		return 1;
	}

	public int getMaxLevel() {
		return 5;
	}

}
