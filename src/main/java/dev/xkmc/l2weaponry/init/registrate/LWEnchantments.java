package dev.xkmc.l2weaponry.init.registrate;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2complements.content.enchantment.core.UnobtainableEnchantment;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2weaponry.content.enchantments.EnderHandEnchantment;
import dev.xkmc.l2weaponry.content.enchantments.HeavyEnchantment;
import dev.xkmc.l2weaponry.content.enchantments.StealthEnchantment;
import dev.xkmc.l2weaponry.content.item.base.BaseThrowableWeaponItem;
import dev.xkmc.l2weaponry.content.item.base.LWTieredItem;
import dev.xkmc.l2weaponry.content.item.types.DaggerItem;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.codehaus.plexus.util.dag.DAG;

public class LWEnchantments {

	public static final EnchantmentCategory THROWABLE = EnchantmentCategory.create("throwable", (e) ->
			e instanceof BaseThrowableWeaponItem);

	public static final EnchantmentCategory DAGGER = EnchantmentCategory.create("dagger", (e) ->
			e instanceof DaggerItem);

	public static final EnchantmentCategory HEACY_WEAPON = EnchantmentCategory.create("heavy_weapon", (e) ->
			e instanceof LWTieredItem t && t.isHeavy() || e instanceof AxeItem);

	public static final RegistryEntry<EnderHandEnchantment> ENDER_HAND = L2Weaponry.REGISTRATE
			.enchantment("ender_hand", THROWABLE, EnderHandEnchantment::new)
			.rarity(Enchantment.Rarity.RARE).addSlots(EquipmentSlot.MAINHAND)
			.defaultLang().register();

	public static final RegistryEntry<StealthEnchantment> NO_AGGRO = L2Weaponry.REGISTRATE
			.enchantment("stealth", DAGGER, StealthEnchantment::new)
			.rarity(Enchantment.Rarity.RARE).addSlots(EquipmentSlot.MAINHAND)
			.defaultLang().register();

	public static final RegistryEntry<HeavyEnchantment> HEAVY = L2Weaponry.REGISTRATE
			.enchantment("heavy", HEACY_WEAPON, HeavyEnchantment::new)
			.rarity(Enchantment.Rarity.RARE).addSlots(EquipmentSlot.MAINHAND)
			.defaultLang().register();

	public static void register() {

	}

}
