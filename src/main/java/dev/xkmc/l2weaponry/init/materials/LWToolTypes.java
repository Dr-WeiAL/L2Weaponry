package dev.xkmc.l2weaponry.init.materials;

import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2complements.init.materials.api.ITool;
import dev.xkmc.l2complements.init.materials.vanilla.RawToolFactory;
import dev.xkmc.l2weaponry.content.item.types.*;
import dev.xkmc.l2weaponry.init.data.TagGen;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;

public enum LWToolTypes implements ITool {
	CLAW(TagGen.CLAW, ClawItem::new, 0.7f, 2),
	DAGGER(TagGen.DAGGER, DaggerItem::new, 0.7f, 4),
	HAMMER(TagGen.HAMMER, HammerItem::new, 2f, 0.7f),
	BATTLE_AXE(TagGen.BATTLE_AXE, BattleAxeItem::new, 2f, 0.7f),
	SPEAR(TagGen.SPEAR, SpearItem::new, 1f, 1.2f),
	HEAVY_CLAW(TagGen.HAEVY_CLAW, HeavyClawItem::new, 1.4f, 0.7f),
	LIGHT_SHIELD(TagGen.LIGHT_SHIELD, LightShieldItem::new, 5f, 1f),
	HEAVY_SHIELD(TagGen.HEAVY_SHIELD, HeavyShieldItem::new, 20f, 1f);

	public final TagKey<Item> tag;
	private final RawToolFactory fac;
	private final float damage, speed;

	LWToolTypes(TagKey<Item> tag, RawToolFactory fac, float damage, float speed) {
		this.tag = tag;
		this.fac = fac;
		this.damage = damage;
		this.speed = speed;
	}

	@Override
	public int getDamage(int base_damage) {
		return Math.round(base_damage * damage);
	}

	@Override
	public float getSpeed(float base_speed) {
		return Math.round(base_speed * speed * 10) * 0.1f;
	}

	@Override
	public Item create(Tier tier, int i, float v, Item.Properties properties, ExtraToolConfig extraToolConfig) {
		return fac.get(tier, i, v, properties, extraToolConfig);
	}
}
