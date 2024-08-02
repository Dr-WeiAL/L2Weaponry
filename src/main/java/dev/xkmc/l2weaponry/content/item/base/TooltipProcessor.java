package dev.xkmc.l2weaponry.content.item.base;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.ArrayList;

public class TooltipProcessor {

	public static HashMultimap<Attribute, AttributeModifier> processTooltip(Multimap<Attribute, AttributeModifier> ans) {
		HashMultimap<Attribute, AttributeModifier> copy = HashMultimap.create();
		copy.putAll(ans);
		sumOp(copy, Attributes.ENTITY_INTERACTION_RANGE.value(), AttributeModifier.Operation.ADD_VALUE);
		sumOp(copy, Attributes.BLOCK_INTERACTION_RANGE.value(), AttributeModifier.Operation.ADD_VALUE);
		sumOp(copy, Attributes.ATTACK_DAMAGE.value(), AttributeModifier.Operation.ADD_VALUE);
		sumOp(copy, Attributes.ATTACK_SPEED.value(), AttributeModifier.Operation.ADD_VALUE);
		sumOp(copy, L2DamageTracker.CRIT_DMG.get(), AttributeModifier.Operation.ADD_VALUE);
		sumOp(copy, LWItems.SHIELD_DEFENSE.get(), AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
		wrapMultiplier(copy, L2DamageTracker.CRIT_RATE.get());
		wrapMultiplier(copy, L2DamageTracker.CRIT_DMG.get());
		wrapMultiplier(copy, L2DamageTracker.BOW_STRENGTH.get());
		return copy;
	}

	private static void sumOp(Multimap<Attribute, AttributeModifier> ans, Attribute attr, AttributeModifier.Operation op) {
		var list = new ArrayList<>(ans.get(attr));
		AttributeModifier mod = null;
		double add = 0;
		for (var e : list) {
			if (e.operation() == op) {
				if (mod == null) {
					mod = e;
				} else {
					add += e.amount();
					ans.remove(attr, e);
				}
			}
		}
		if (add != 0) {
			ans.remove(attr, mod);
			add += mod.amount();
			ans.put(attr, new AttributeModifier(mod.id(), add, op));
		}
	}

	private static void wrapMultiplier(Multimap<Attribute, AttributeModifier> ans, Attribute attr) {
		var list = new ArrayList<>(ans.get(attr));
		for (var e : list) {
			if (e.operation() == AttributeModifier.Operation.ADD_VALUE) {
				ans.remove(attr, e);
				ans.put(attr, new AttributeModifier(e.id(), e.amount(), AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
			}
		}
	}

}
