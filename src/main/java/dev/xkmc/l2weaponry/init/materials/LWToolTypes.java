package dev.xkmc.l2weaponry.init.materials;

import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2complements.init.materials.api.ITool;
import dev.xkmc.l2complements.init.materials.vanilla.RawToolFactory;
import dev.xkmc.l2weaponry.content.item.types.ClawItem;
import dev.xkmc.l2weaponry.content.item.types.DaggerItem;
import dev.xkmc.l2weaponry.content.item.types.HammerItem;
import dev.xkmc.l2weaponry.content.item.types.LargeAxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;

public enum LWToolTypes implements ITool {
	CLAW(ClawItem::new, 0.7f, 2),
	DAGGER(DaggerItem::new, 0.7f, 2),
	HAMMER(HammerItem::new, 2f, 0.7f),
	LARGE_AXE(LargeAxeItem::new, 2f, 0.7f);

	private final RawToolFactory fac;
	private final float damage, speed;

	LWToolTypes(RawToolFactory fac, float damage, float speed) {
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
	public TieredItem create(Tier tier, int i, float v, Item.Properties properties, ExtraToolConfig extraToolConfig) {
		return fac.get(tier, i, v, properties, extraToolConfig);
	}
}
