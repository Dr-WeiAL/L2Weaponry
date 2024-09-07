package dev.xkmc.l2weaponry.init.registrate;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.l2core.init.reg.registrate.SimpleEntry;
import dev.xkmc.l2core.init.reg.simple.DCReg;
import dev.xkmc.l2core.init.reg.simple.DCVal;
import dev.xkmc.l2weaponry.compat.twilightforest.TFCompat;
import dev.xkmc.l2weaponry.content.item.legendary.*;
import dev.xkmc.l2weaponry.content.item.types.NunchakuItem;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import dev.xkmc.l2weaponry.init.materials.LWGenItem;
import dev.xkmc.l2weaponry.init.materials.LWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWToolTypes;
import dev.xkmc.l2weaponry.init.materials.LegendaryToolFactory;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.ModList;
import twilightforest.TwilightForestMod;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings({"unsafe"})
@MethodsReturnNonnullByDefault
public class LWItems {

	public static final List<Item> BLOCK_DECO = new ArrayList<>();
	public static final List<Item> THROW_DECO = new ArrayList<>();
	public static final List<Item> CLAW_DECO = new ArrayList<>();
	public static final List<NunchakuItem> NUNCHAKU_DECO = new ArrayList<>();

	public static final SimpleEntry<CreativeModeTab> TAB =
			L2Weaponry.REGISTRATE.buildL2CreativeTab("weaponry", "L2 Weaponry", b -> b
					.icon(LWItems.GEN_ITEM[LWToolMats.SCULKIUM.ordinal()][LWToolTypes.PLATE_SHIELD.ordinal()]::asStack));

	public static final SimpleEntry<Attribute> SHIELD_DEFENSE = new SimpleEntry<>(L2Weaponry.REGISTRATE
			.simple("shield_defense", Registries.ATTRIBUTE, () -> new RangedAttribute(
					"attribute.name.shield_defense", 0, 0, 1000).setSyncable(true)));

	public static final SimpleEntry<Attribute> REFLECT_TIME = new SimpleEntry<>(L2Weaponry.REGISTRATE
			.simple("reflect_time", Registries.ATTRIBUTE, () -> new RangedAttribute(
					"attribute.name.reflect_time", 0, 0, 1000).setSyncable(true)));

	public static final ItemEntry<Item> HANDLE;

	public static final ItemEntry<StormJavelin> STORM_JAVELIN;
	public static final ItemEntry<FlameAxe> FLAME_AXE;
	public static final ItemEntry<FrozenSpear> FROZEN_SPEAR;
	public static final ItemEntry<BlackHammer> BLACK_HAMMER;
	public static final ItemEntry<EnderSpear> ENDER_SPEAR;
	public static final ItemEntry<EnderJavelin> ENDER_JAVELIN;
	public static final ItemEntry<EnderDagger> ENDER_DAGGER;
	public static final ItemEntry<EnderMachete> ENDER_MACHETE;
	public static final ItemEntry<AbyssDagger> ABYSS_DAGGER;
	public static final ItemEntry<AbyssMachete> ABYSS_MACHETE;
	public static final ItemEntry<AbyssHammer> ABYSS_HAMMER;
	public static final ItemEntry<AbyssAxe> ABYSS_AXE;
	public static final ItemEntry<BlackAxe> BLACK_AXE;
	public static final ItemEntry<BloodClaw> BLOOD_CLAW;
	public static final ItemEntry<CheaterClaw> CHEATER_CLAW;
	public static final ItemEntry<CheaterMachete> CHEATER_MACHETE;
	public static final ItemEntry<HolyAxe> HOLY_AXE;
	public static final ItemEntry<HolyHammer> HOLY_HAMMER;
	public static final ItemEntry<DeathScythe> DEATH_SCYTHE;
	public static final ItemEntry<Item>[][] GEN_ITEM;

	public static final DCReg DC = DCReg.of(L2Weaponry.REG);
	public static final DCVal<Unit> REEQUIP = DC.unit("reequip");
	public static final DCVal<Integer> HIT_COUNT = DC.intVal("hit_count");
	public static final DCVal<Long> HIT_TIME = DC.longVal("last_hit_time");
	public static final DCVal<Integer> BLOCKED_DAMAGE = DC.intVal("blocked_damage");
	public static final DCVal<UUID> LAST_TARGET = DC.uuid("last_target");
	public static final DCVal<Float> DAMAGE_BONUS = DC.floatVal("accumulated_damage");
	public static final DCVal<Integer> KILL_COUNT = DC.intVal("kill_count");

	static {
		HANDLE = L2Weaponry.REGISTRATE.item("reinforced_handle", Item::new).defaultModel().defaultLang().register();
		BLOOD_CLAW = regLegendary("vampire_desire", BloodClaw::new, LWToolTypes.CLAW, LWToolMats.TOTEMIC_GOLD, Rarity.RARE, false);
		CHEATER_CLAW = regLegendary("claw_of_determination", CheaterClaw::new, LWToolTypes.CLAW, LWToolMats.ETERNIUM, Rarity.EPIC, false);
		ENDER_DAGGER = regLegendary("shadow_hunter", EnderDagger::new, LWToolTypes.DAGGER, LWToolMats.SHULKERATE, Rarity.EPIC, false);
		ABYSS_DAGGER = regLegendary("abyss_shock", AbyssDagger::new, LWToolTypes.DAGGER, LWToolMats.SCULKIUM, Rarity.RARE, false);
		ENDER_MACHETE = regLegendary("shadow_shredder", EnderMachete::new, LWToolTypes.MACHETE, LWToolMats.SHULKERATE, Rarity.RARE, false);
		ABYSS_MACHETE = regLegendary("abyss_resonance", AbyssMachete::new, LWToolTypes.MACHETE, LWToolMats.SCULKIUM, Rarity.RARE, false);
		CHEATER_MACHETE = regLegendary("blade_of_illusion", CheaterMachete::new, LWToolTypes.MACHETE, LWToolMats.ETERNIUM, Rarity.EPIC, false);
		BLACK_AXE = regLegendary("barbaric_hallow", BlackAxe::new, LWToolTypes.THROWING_AXE, LWToolMats.NETHERITE, Rarity.RARE, false);

		BLACK_HAMMER = regLegendary("hammer_of_incarceration", BlackHammer::new, LWToolTypes.HAMMER, LWToolMats.NETHERITE, Rarity.UNCOMMON, true);
		ABYSS_HAMMER = regLegendary("abyss_echo", AbyssHammer::new, LWToolTypes.HAMMER, LWToolMats.SCULKIUM, Rarity.RARE, true);
		HOLY_HAMMER = regLegendary("dogmatic_punishment", HolyHammer::new, LWToolTypes.HAMMER, LWToolMats.TOTEMIC_GOLD, Rarity.RARE, true);

		FLAME_AXE = regLegendary("axe_of_cursed_flame", FlameAxe::new, LWToolTypes.BATTLE_AXE, LWToolMats.NETHERITE, Rarity.UNCOMMON, true);
		ABYSS_AXE = regLegendary("abyss_terror", AbyssAxe::new, LWToolTypes.BATTLE_AXE, LWToolMats.SCULKIUM, Rarity.RARE, true);
		HOLY_AXE = regLegendary("dogmatic_standoff", HolyAxe::new, LWToolTypes.BATTLE_AXE, LWToolMats.TOTEMIC_GOLD, Rarity.RARE, true);
		DEATH_SCYTHE = regLegendary("death_scythe", DeathScythe::new, LWToolTypes.SCYTHE, LWToolMats.NETHERITE, Rarity.RARE, false);
		FROZEN_SPEAR = regLegendary("spear_of_winter_storm", FrozenSpear::new, LWToolTypes.SPEAR, LWToolMats.IRON, Rarity.UNCOMMON, true);
		ENDER_SPEAR = regLegendary("haunting_demon_of_the_end", EnderSpear::new, LWToolTypes.SPEAR, LWToolMats.SHULKERATE, Rarity.EPIC, true);
		STORM_JAVELIN = regLegendary("poseidon_madness", StormJavelin::new, LWToolTypes.JAVELIN, LWToolMats.POSEIDITE, Rarity.EPIC, false);
		ENDER_JAVELIN = regLegendary("void_escape", EnderJavelin::new, LWToolTypes.JAVELIN, LWToolMats.SHULKERATE, Rarity.RARE, false);

		GEN_ITEM = LWGenItem.generate(LWToolMats.values());

		if (ModList.get().isLoaded(TwilightForestMod.ID)) {
			TFCompat.register();
		}

		/* TODO compat
		if (ModList.get().isLoaded(IceAndFire.MODID)) {
			DragonCompat.register();
		}
		if (ModList.get().isLoaded(Undergarden.MODID)) {
			UGCompat.register();
		}
		if (ModList.get().isLoaded(AerialHell.MODID)) {
			AHCompat.register();
		}
		*/

	}

	private static <T extends Item> ItemEntry<T> regLegendary(String name, LegendaryToolFactory<T> fac, LWToolTypes type, LWToolMats mat, Rarity r, boolean is3D) {
		return L2Weaponry.REGISTRATE.item(name, p -> type.legendary(fac).parse(mat, p.rarity(r)))
				.model((ctx, pvd) -> LWGenItem.model(type, mat, ctx, pvd, "legendary", name, is3D))
				.tag(type.tag).defaultLang().register();
	}


	public static void register() {
	}

}
