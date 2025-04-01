package dev.xkmc.l2weaponry.compat.atm;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class ATMTool extends ExtraToolConfig {

	private final int rank;

	public ATMTool(int rank) {
		this.rank = rank;
	}

	@Override
	public void configureAttributes(ItemAttributeModifiers.Builder builder) {
		var prev = builder.build();
		var speed = prev.modifiers().stream().filter(
				e -> e.attribute().is(Attributes.ATTACK_SPEED.getKey()) &&
						e.modifier().operation() == AttributeModifier.Operation.ADD_VALUE
		).findFirst();
		var heavy = prev.modifiers().stream().filter(e ->
				e.attribute().is(Attributes.ATTACK_SPEED.getKey()) &&
						e.modifier().operation() != AttributeModifier.Operation.ADD_VALUE
		).findFirst();

		if (heavy.isPresent()) {
			builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(L2Complements.loc("atm_tool"), 0.2 * rank, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.MAINHAND);
		} else if (speed.isPresent() && speed.get().modifier().amount() > -2) {
			builder.add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(L2Complements.loc("atm_tool"), 0.5 * rank, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
			builder.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(L2Complements.loc("atm_tool"), 0.5 * rank, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
		} else {
			builder.add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(L2Complements.loc("atm_tool"), 0.25 * rank, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
			builder.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(L2Complements.loc("atm_tool"), 0.25 * rank, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
			builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(L2Complements.loc("atm_tool"), 0.1 * rank, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.MAINHAND);
		}
	}

}
