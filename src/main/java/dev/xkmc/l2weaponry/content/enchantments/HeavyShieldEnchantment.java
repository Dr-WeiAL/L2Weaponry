package dev.xkmc.l2weaponry.content.enchantments;

import dev.xkmc.l2complements.content.enchantment.core.AttributeEnchantment;
import dev.xkmc.l2complements.content.enchantment.core.UnobtainableEnchantment;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2weaponry.content.item.base.BaseThrowableWeaponItem;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class HeavyShieldEnchantment extends UnobtainableEnchantment implements AttributeEnchantment {

	private static final String NAME_DEF = "heavyshield_defense";
	private static final String NAME_SPEED = "heavyshield_speed";

	private static final UUID ID_DEF = MathHelper.getUUIDFromString(NAME_DEF);
	private static final UUID ID_SPEED = MathHelper.getUUIDFromString(NAME_SPEED);

	public HeavyShieldEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
		super(pRarity, pCategory, pApplicableSlots);
	}

	@Override
	public void addAttributes(int level, ItemAttributeModifierEvent event) {
		if (event.getSlotType() == EquipmentSlot.MAINHAND) {
			event.addModifier(Attributes.MOVEMENT_SPEED, new AttributeModifier(ID_SPEED, NAME_SPEED, -LWConfig.COMMON.heavyShieldSpeedReduction.get() * level, AttributeModifier.Operation.MULTIPLY_BASE));
			event.addModifier(LWItems.SHIELD_DEFENSE.get(), new AttributeModifier(ID_DEF, NAME_DEF, LWConfig.COMMON.heavyShieldDefenseBonus.get() * level, AttributeModifier.Operation.MULTIPLY_BASE));
		}
	}

	public ChatFormatting getColor() {
		return ChatFormatting.LIGHT_PURPLE;
	}

	public int getMinLevel() {
		return 1;
	}

	public int getMaxLevel() {
		return 5;
	}

}
