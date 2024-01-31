package dev.xkmc.l2weaponry.init.materials;

import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2damagetracker.contents.materials.api.ITool;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2damagetracker.contents.materials.vanilla.RawToolFactory;
import dev.xkmc.l2weaponry.content.item.types.*;
import dev.xkmc.l2weaponry.init.data.TagGen;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public enum LWToolTypes implements ITool {
	CLAW(TagGen.CLAW, ClawItem::new, 0.7f, 3, "claw_base", () -> new EnchantmentInstance(Enchantments.SWEEPING_EDGE, 2)),
	DAGGER(TagGen.DAGGER, DaggerItem::new, 0.7f, 4),
	MACHETE(TagGen.MACHETE, MacheteItem::new, 1.2f, 1f, () -> new EnchantmentInstance(Enchantments.SWEEPING_EDGE, 1)),
	THROWING_AXE(TagGen.THROWING_AXE, ThrowingAxeItem::new, 1.4f, 1f),
	HAMMER(TagGen.HAMMER, HammerItem::new, 2f, 0.7f, () -> new EnchantmentInstance(LCEnchantments.CUBIC.get(), 1)),
	BATTLE_AXE(TagGen.BATTLE_AXE, BattleAxeItem::new, 2f, 0.7f, "battle_axe", () -> new EnchantmentInstance(LCEnchantments.TREE.get(), 1)),
	SPEAR(TagGen.SPEAR, SpearItem::new, 1f, 1f, "long_weapon", () -> new EnchantmentInstance(LCEnchantments.PLANE.get(), 1)),
	JAVELIN(TagGen.JAVELIN, JavelinItem::new, 1f, 1.2f, "long_weapon", () -> new EnchantmentInstance(LCEnchantments.DRILL.get(), 1)),
	ROUND_SHIELD(TagGen.ROUND_SHIELD, RoundShieldItem::new, 5f, 1f / 2),
	PLATE_SHIELD(TagGen.PLATE_SHIELD, PlateShieldItem::new, 20f, 1f / 8),
	NUNCHAKU(TagGen.NUNCHAKU, NunchakuItem::new, 0.5f, 4);

	public final TagKey<Item> tag;
	private final RawToolFactory fac;
	private final float damage, speed;
	private final String customModel;
	private final List<Supplier<EnchantmentInstance>> enchs;

	@SafeVarargs
	LWToolTypes(TagKey<Item> tag, RawToolFactory fac, float damage, float speed, @Nullable String customModel, Supplier<EnchantmentInstance>... enchs) {
		this.tag = tag;
		this.fac = fac;
		this.damage = damage;
		this.speed = speed;
		this.customModel = customModel;
		this.enchs = List.of(enchs);
	}


	@SafeVarargs
	LWToolTypes(TagKey<Item> tag, RawToolFactory fac, float damage, float speed, Supplier<EnchantmentInstance>... enchs) {
		this(tag, fac, damage, speed, null, enchs);
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

	public @Nullable
	String customModel() {
		return customModel;
	}

	public <T extends Item> LegendaryTool<T> legendary(LegendaryToolFactory<T> fac) {
		return new LegendaryTool<>(this, fac);
	}

	public List<EnchantmentInstance> getEnchs() {
		return enchs.stream().map(Supplier::get).toList();
	}

}
