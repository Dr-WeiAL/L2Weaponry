package dev.xkmc.l2weaponry.content.enchantments;

import dev.xkmc.l2complements.content.enchantment.core.AttributeEnchantment;
import dev.xkmc.l2complements.content.enchantment.core.SourceModifierEnchantment;
import dev.xkmc.l2complements.content.enchantment.core.UnobtainableEnchantment;
import dev.xkmc.l2damagetracker.contents.attack.CreateSourceEvent;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.data.LWDamageStates;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class StealthEnchantment extends UnobtainableEnchantment implements SourceModifierEnchantment, AttributeEnchantment {

	private static final String NAME_DAMAGE = "stealth_enchantment_damage";
	private static final UUID ID_DAMAGE = MathHelper.getUUIDFromString(NAME_DAMAGE);

	public StealthEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
		super(pRarity, pCategory, pApplicableSlots);
	}

	@Override
	public void modify(CreateSourceEvent event, ItemStack stack, int level) {
		double chance = LWConfig.COMMON.stealthChance.get() * level;
		if (event.getAttacker().getRandom().nextDouble() < chance) {
			event.enable(LWDamageStates.NO_ANGER);
		}
	}

	@Override
	public void addAttributes(int level, ItemAttributeModifierEvent event) {
		if (event.getSlotType() == EquipmentSlot.MAINHAND) {
			event.addModifier(Attributes.ATTACK_DAMAGE,
					new AttributeModifier(ID_DAMAGE, NAME_DAMAGE,
							-LWConfig.COMMON.stealthDamageReduction.get() * level,
							AttributeModifier.Operation.MULTIPLY_TOTAL));
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
