package dev.xkmc.l2weaponry.content.item.base;

import com.google.common.collect.Multimap;
import dev.xkmc.l2complements.content.item.generic.GenericTieredItem;
import dev.xkmc.l2library.init.events.attack.AttackCache;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;

public interface LWTieredItem extends GenericTieredItem {

	default float getMultiplier(AttackCache event) {
		return 1;
	}

}
