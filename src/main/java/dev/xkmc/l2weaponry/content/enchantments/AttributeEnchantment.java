package dev.xkmc.l2weaponry.content.enchantments;

import dev.xkmc.l2core.init.L2LibReg;
import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

public interface AttributeEnchantment {

	void addAttributes(int level, ItemAttributeModifierEvent event);

	default ResourceLocation id() {
		var ans = L2LibReg.ENCH.reg().getKey((LegacyEnchantment) this);
		assert ans != null;
		return ans;
	}

	default ResourceLocation id(EquipmentSlot e) {
		return id().withSuffix("_" + e.getSerializedName());
	}

}
