package dev.xkmc.l2weaponry.init.registrate;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2weaponry.content.enchantments.*;
import dev.xkmc.l2weaponry.content.item.base.BaseShieldItem;
import dev.xkmc.l2weaponry.content.item.base.BaseThrowableWeaponItem;
import dev.xkmc.l2weaponry.content.item.base.LWTieredItem;
import dev.xkmc.l2weaponry.content.item.types.DaggerItem;
import dev.xkmc.l2weaponry.content.item.types.MacheteItem;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class LWEnchantments {

	public static final EnchantmentCategory THROWABLE = EnchantmentCategory.create("throwable", (e) ->
			e instanceof BaseThrowableWeaponItem);

	public static final EnchantmentCategory DAGGER = EnchantmentCategory.create("dagger", (e) ->
			e instanceof DaggerItem);

	public static final EnchantmentCategory HEAVY_WEAPON = EnchantmentCategory.create("heavy_weapon", (e) ->
			e instanceof LWTieredItem t && t.isHeavy() || e instanceof AxeItem);

	public static final EnchantmentCategory SHIELDS = EnchantmentCategory.create("shields", e ->
			e instanceof BaseShieldItem);

	public static final EnchantmentCategory MACHETES = EnchantmentCategory.create("machetes", e ->
			e instanceof MacheteItem);

	public static final RegistryEntry<EnderHandEnchantment> ENDER_HAND = L2Weaponry.REGISTRATE
			.enchantment("ender_hand", THROWABLE, EnderHandEnchantment::new)
			.rarity(Enchantment.Rarity.RARE).addSlots(EquipmentSlot.MAINHAND)
			.defaultLang().register();

	public static final RegistryEntry<StealthEnchantment> NO_AGGRO = L2Weaponry.REGISTRATE
			.enchantment("stealth", DAGGER, StealthEnchantment::new)
			.rarity(Enchantment.Rarity.RARE).addSlots(EquipmentSlot.MAINHAND)
			.defaultLang().register();

	public static final RegistryEntry<HeavyEnchantment> HEAVY = L2Weaponry.REGISTRATE
			.enchantment("heavy", HEAVY_WEAPON, HeavyEnchantment::new)
			.rarity(Enchantment.Rarity.RARE).addSlots(EquipmentSlot.MAINHAND)
			.defaultLang().register();

	public static final RegistryEntry<HardShieldEnchantment> HARD_SHIELD = L2Weaponry.REGISTRATE
			.enchantment("hard_shield", SHIELDS, HardShieldEnchantment::new)
			.rarity(Enchantment.Rarity.RARE).addSlots(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND)
			.defaultLang().register();

	public static final RegistryEntry<HeavyShieldEnchantment> HEAVY_SHIELD = L2Weaponry.REGISTRATE
			.enchantment("heavy_shield", SHIELDS, HeavyShieldEnchantment::new)
			.rarity(Enchantment.Rarity.RARE).addSlots(EquipmentSlot.MAINHAND)
			.defaultLang().register();

	public static final RegistryEntry<EnergizedWillEnchantment> ENERGIZED_WILL = L2Weaponry.REGISTRATE
			.enchantment("energized_will", MACHETES, EnergizedWillEnchantment::new)
			.rarity(Enchantment.Rarity.RARE).addSlots(EquipmentSlot.MAINHAND)
			.defaultLang().register();

	public static final RegistryEntry<RaisedSpiritEnchantment> RAISED_SPIRIT = L2Weaponry.REGISTRATE
			.enchantment("raised_spirit", MACHETES, RaisedSpiritEnchantment::new)
			.rarity(Enchantment.Rarity.RARE).addSlots(EquipmentSlot.MAINHAND)
			.defaultLang().register();

	public static void register() {

	}

}
