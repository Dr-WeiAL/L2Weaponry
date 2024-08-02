package dev.xkmc.l2weaponry.init.materials;

import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2damagetracker.contents.materials.api.ITool;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2damagetracker.contents.materials.vanilla.RawToolFactory;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2weaponry.content.item.types.*;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import dev.xkmc.l2weaponry.init.data.TagGen;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import javax.annotation.Nullable;
import java.util.List;

public enum LWToolTypes implements ITool {
	CLAW(TagGen.CLAW, ClawItem::new, 0.7f, 3, -1, "claw_base", new DefaultEnch(Enchantments.SWEEPING_EDGE, 2)),
	DAGGER(TagGen.DAGGER, DaggerItem::new, 0.7f, 4, -1),
	MACHETE(TagGen.MACHETE, MacheteItem::new, 1.2f, 1f, 0, new DefaultEnch(Enchantments.SWEEPING_EDGE, 1)),
	THROWING_AXE(TagGen.THROWING_AXE, ThrowingAxeItem::new, 1.4f, 1f, -0.5f),
	HAMMER(TagGen.HAMMER, HammerItem::new, 2f, 0.7f, 0, new DefaultEnch(LCEnchantments.CUBIC.id(), 1)),
	BATTLE_AXE(TagGen.BATTLE_AXE, BattleAxeItem::new, 2f, 0.7f, 0, "battle_axe", new DefaultEnch(LCEnchantments.TREE.id(), 1)),
	SPEAR(TagGen.SPEAR, SpearItem::new, 1f, 1f, 2, "long_weapon", new DefaultEnch(LCEnchantments.PLANE.id(), 1)),
	JAVELIN(TagGen.JAVELIN, JavelinItem::new, 1f, 1.2f, 2, "long_weapon", new DefaultEnch(LCEnchantments.DRILL.id(), 1)),
	ROUND_SHIELD(TagGen.ROUND_SHIELD, RoundShieldItem::new, 5f, 0, 0),
	PLATE_SHIELD(TagGen.PLATE_SHIELD, PlateShieldItem::new, 20f, 0, 0),
	NUNCHAKU(TagGen.NUNCHAKU, NunchakuItem::new, 0.5f, 4, 0);

	public final TagKey<Item> tag;
	private final RawToolFactory fac;
	private final float damage, speed, range;
	private final String customModel;
	private final List<DefaultEnch> enchs;

	LWToolTypes(TagKey<Item> tag, RawToolFactory fac, float damage, float speed, float range, @Nullable String customModel, DefaultEnch... enchs) {
		this.tag = tag;
		this.fac = fac;
		this.damage = damage;
		this.speed = speed;
		this.range = range;
		this.customModel = customModel;
		this.enchs = List.of(enchs);
	}

	LWToolTypes(TagKey<Item> tag, RawToolFactory fac, float damage, float speed, float range, DefaultEnch... enchs) {
		this(tag, fac, damage, speed, range, null, enchs);
	}


	@Override
	public void configure(ItemAttributeModifiers.Builder builder, int dmg, float speed) {
		var id = L2Weaponry.loc(name().toLowerCase());
		if (this == ROUND_SHIELD) {
			builder.add(LWItems.SHIELD_DEFENSE.holder(), new AttributeModifier(id, dmg,
					AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
		} else if (this == PLATE_SHIELD) {
			builder.add(LWItems.SHIELD_DEFENSE.holder(), new AttributeModifier(id, dmg,
					AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
			builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
					Math.round(dmg * 0.035) - 1,
					AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
			builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, 16,
					AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
			builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(id, -0.95,
					AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.MAINHAND);
			builder.add(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(id, 4,
					AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
			builder.add(LWItems.REFLECT_TIME.holder(), new AttributeModifier(id, 20,
					AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
			builder.add(L2DamageTracker.CRIT_DMG.holder(), new AttributeModifier(id, 1.5,
					AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
		} else if (this == BATTLE_AXE || this == HAMMER) {
			builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
					dmg - 1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
			float raw_speed = speed + dmg;
			float reduce = 1f - Math.round(speed / raw_speed * 100) * 0.01f;
			raw_speed = speed / (1 - reduce);
			builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID,
					raw_speed - 4, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
			builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(id, -reduce,
					AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.MAINHAND);
		} else {
			if (range != 0) {
				AttributeModifier attr = new AttributeModifier(id, range,
						AttributeModifier.Operation.ADD_VALUE);
				builder.add(Attributes.ENTITY_INTERACTION_RANGE, attr, EquipmentSlotGroup.MAINHAND);
				if (range > 0) {
					builder.add(Attributes.BLOCK_INTERACTION_RANGE, attr, EquipmentSlotGroup.MAINHAND);
				}
			}
			ITool.super.configure(builder, dmg, speed);
		}
	}

	@Override
	public int getDamage(int base_damage) {
		return Math.round(base_damage * damage);
	}

	@Override
	public float getAtkSpeed(float base_speed) {
		return Math.round(base_speed * speed * 10) * 0.1f;
	}

	@Override
	public Item create(Tier tier, Item.Properties properties, ExtraToolConfig extraToolConfig) {
		return fac.get(tier, properties, extraToolConfig);
	}

	public @Nullable
	String customModel() {
		return customModel;
	}

	public <T extends Item> LegendaryTool<T> legendary(LegendaryToolFactory<T> fac) {
		return new LegendaryTool<>(this, fac);
	}

	public List<DefaultEnch> getEnchs() {
		return enchs;
	}

	public record DefaultEnch(ResourceKey<Enchantment> key, int lv) {

	}

}
