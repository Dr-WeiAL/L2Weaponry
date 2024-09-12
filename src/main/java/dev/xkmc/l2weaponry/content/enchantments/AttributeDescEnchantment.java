package dev.xkmc.l2weaponry.content.enchantments;

import com.google.common.base.Predicates;
import dev.xkmc.l2core.init.reg.ench.CustomDescEnchantment;
import dev.xkmc.l2core.init.reg.ench.EnchColor;
import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import dev.xkmc.l2damagetracker.contents.curios.AttrTooltip;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleSupplier;
import java.util.function.Predicate;

public class AttributeDescEnchantment extends LegacyEnchantment implements AttributeEnchantment, CustomDescEnchantment {

	public record Entry(
			Holder<Attribute> attr,
			DoubleSupplier val,
			AttributeModifier.Operation op,
			EquipmentSlotGroup group,
			Predicate<ItemStack> pred
	) {

		Entry(Holder<Attribute> attr, DoubleSupplier val, AttributeModifier.Operation op, EquipmentSlotGroup group) {
			this(attr, val, op, group, Predicates.alwaysTrue());
		}

	}

	private final Entry[] entries;

	public AttributeDescEnchantment(Entry... entries) {
		this.entries = entries;
	}

	@Override
	public void addAttributes(int level, ItemAttributeModifierEvent event) {
		for (var e : entries) {
			if (e.pred.test(event.getItemStack())) {
				var mod = new AttributeModifier(id(), e.val.getAsDouble() * level, e.op);
				event.addModifier(e.attr, mod, e.group);
			}
		}
	}

	public List<Component> descFull(int lv, String key, boolean alt, boolean book, EnchColor color) {
		List<Component> ans = new ArrayList<>();
		ans.add(Component.translatable(key).withStyle(color.desc()));
		for (var e : entries) {
			ans.add(AttrTooltip.getDesc(e.attr, e.val.getAsDouble() * lv, e.op));
		}
		return ans;
	}

}
