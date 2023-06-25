package dev.xkmc.l2weaponry.content.enchantments;

import dev.xkmc.l2complements.content.enchantment.core.AttributeEnchantment;
import dev.xkmc.l2complements.content.enchantment.core.UnobtainableEnchantment;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2weaponry.content.item.base.BaseClawItem;
import dev.xkmc.l2weaponry.content.item.types.MacheteItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class EnergizedWillEnchantment extends BaseMacheteEnchantment implements AttributeEnchantment {

	private static final String NAME_REACH = "energized_will_reach";

	private static final UUID ID_REACH = MathHelper.getUUIDFromString(NAME_REACH);

	public EnergizedWillEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
		super(pRarity, pCategory, pApplicableSlots);
	}

	@Override
	public void addAttributes(int level, ItemAttributeModifierEvent event) {
		if (event.getSlotType() == EquipmentSlot.MAINHAND) {
			if (event.getItemStack().getItem() instanceof MacheteItem) {
				double bonus = LWConfig.COMMON.energizedWillReachBonus.get() *
						BaseClawItem.getHitCount(event.getItemStack()) * level;
				event.addModifier(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(ID_REACH, NAME_REACH, bonus,
						AttributeModifier.Operation.ADDITION));
			}
		}
	}

}
