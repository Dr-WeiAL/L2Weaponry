package dev.xkmc.l2weaponry.content.enchantments;

import dev.xkmc.l2complements.content.enchantment.core.AttributeEnchantment;
import dev.xkmc.l2complements.content.enchantment.core.UnobtainableEnchantment;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2weaponry.content.item.base.BaseThrowableWeaponItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class HeavyEnchantment extends UnobtainableEnchantment implements AttributeEnchantment {

	private static final String NAME_CRIT = "heavy_enchantment_crit";
	private static final String NAME_SPEED = "heavy_enchantment_speed";

	private static final UUID ID_CRIT = MathHelper.getUUIDFromString(NAME_CRIT);
	private static final UUID ID_SPEED = MathHelper.getUUIDFromString(NAME_SPEED);

	public HeavyEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
		super(pRarity, pCategory, pApplicableSlots);
	}

	@Override
	public void addAttributes(int level, ItemAttributeModifierEvent event) {
		if (event.getSlotType() == EquipmentSlot.MAINHAND) {
			event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(ID_SPEED, NAME_SPEED, -LWConfig.COMMON.heavySpeedReduction.get() * level, AttributeModifier.Operation.ADDITION));
			event.addModifier(L2DamageTracker.CRIT_DMG.get(), new AttributeModifier(ID_CRIT, NAME_CRIT, LWConfig.COMMON.heavyCritBonus.get() * level, AttributeModifier.Operation.ADDITION));
			if (event.getItemStack().getItem() instanceof BaseThrowableWeaponItem) {
				event.addModifier(L2DamageTracker.BOW_STRENGTH.get(), new AttributeModifier(ID_CRIT, NAME_CRIT, LWConfig.COMMON.heavyCritBonus.get() * level, AttributeModifier.Operation.ADDITION));
			}
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
