package dev.xkmc.l2weaponry.content.item.base;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

public class TooltipProcessor {

	public static Multimap<Attribute, AttributeModifier> processTooltip(Multimap<Attribute, AttributeModifier> ans) {
		sumAddition(ans, ForgeMod.ATTACK_RANGE.get());
		sumAddition(ans, ForgeMod.REACH_DISTANCE.get());
		return ans;
	}

	private static void sumAddition(Multimap<Attribute, AttributeModifier> ans, Attribute attr) {
		var list = ans.get(attr);
		AttributeModifier mod = null;
		double add = 0;
		for (var e : list) {
			if (e.getOperation() == AttributeModifier.Operation.ADDITION) {
				if (mod == null) {
					mod = e;
				} else {
					add += e.getAmount();
					ans.remove(attr, e);
				}
			}
		}
		if (add != 0) {
			ans.remove(attr, mod);
			add += mod.getAmount();
			ans.put(attr, new AttributeModifier(mod.getId(), mod.getName(), add, AttributeModifier.Operation.ADDITION));
		}
	}

}
