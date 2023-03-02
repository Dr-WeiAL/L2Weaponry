package dev.xkmc.l2weaponry.init.data;

import dev.xkmc.l2library.repack.registrate.providers.RegistrateLangProvider;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.Locale;

public enum LangData {

	TOOL_DAGGER("tool.dagger", "Deal high damage to enemies not targeting attacker", 0),
	TOOL_CLAW("tool.claw", "Deal damage to all surrounding mobs. Increase damage for consecutive attack. Double weld for larger sweep range and higher damage stack limit.", 0),
	TOOL_HAMMER("tool.hammer", "Double hand weapon. Break through armors", 0),
	TOOL_BATTLE_AXE("tool.battle_axe", "Double Hand weapon. Sweeping attack", 0),
	TOOL_SPEAR("tool.spear", "Sweeping attack, long attack range.", 0),
	TOOL_MACHETE("tool.machete", "Deal damage to all surrounding mobs. Increase damage for consecutive attack. Double weld for larger sweep range and higher damage stack limit.", 0),
	TOOL_ROUND_SHIELD("tool.round_shield", "Move fast while blocking.", 0),
	TOOL_PLATE_SHIELD("tool.plate_shield", "Resistant against attacks that would break regular shields.", 0),

	TOOL_THROWING_AXE("tool.throwing_axe", "You can throw this axe toward target.", 0),
	TOOL_JAVELIN("tool.javelin", "Pierce through multiple enemies when thrown", 0),

	FLAME_AXE("legendary.axe_of_cursed_flame", "Inflict soul flame on targets. Protect holder from fire damage.", 0),
	BLACK_HAMMER("legendary.hammer_of_incarceration", "Immobilize enemies within %s blocks when performed a falling attack.", 1),
	FROZEN_SPEAR("legendary.spear_of_winter_storm", "Frost targets. Protect holder from powdered snow.", 0),
	STORM_JAVELIN("legendary.poseidon_madness", "Thunder strike all enemies hit, thrown or sweep, regardless of position and weather. Protect holder from fire and thunder damage.", 0),
	ENDER_DAGGER("legendary.shadow_hunter", "Right click target within %s blocks to teleport to its back, and wipe its anger. Damage penetrates armor if target does not target you.", 1),
	ENDER_JAVELIN("legendary.void_escape", "Not affected by gravity. When hit a block, teleport to that position. Holder gets levitation and slow falling in void.", 0),
	ENDER_SPEAR("legendary.haunting_demon_of_the_end", "Aim at a target within %s blocks and right click to teleport near it.", 1),

	ABYSS_DAGGER("legendary.abyss_shock", "Damage dealt to enemies not targeting user will bypass magical protections.", 0),
	ABYSS_MACHETE("legendary.abyss_resonance", "For over %s consecutive attacks, damage will bypass magical protections.", 1),
	ABYSS_HAMMER("legendary.abyss_echo", "Critical hit will bypass magical protections.", 0),
	ABYSS_AXE("legendary.abyss_terror", "Damage dealt to enemies targeting user will bypass magical protections.", 0),
	BLOOD_CLAW("legendary.vampire_desire", "Restore health based on damage dealt. Increase damage stack limit based on enemies killed.", 0),
	BLACK_AXE("legendary.barbaric_hallow", "Damage penetrates armor, damage increase target's armor value.", 0),
	ENDER_MACHETE("legendary.shadow_shredder", "Inflict levitation and slow falling for a short time to enemies.", 0),

	CHEATER_CLAW("legendary.claw_of_determination", "When damage dealt is reduced, next consecutive attack to the same target will increase double of the amount reduced.", 0),
	CHEATER_MACHETE("legendary.blade_of_illusion", "When target has less lost health than consecutive damage dealt, next consecutive attack to the same target will increase that difference.", 0),
	HOLY_AXE("legendary.dogmatic_standoff", "Gain damage absorption equal to %s%% of target health. Would not exceed %s%% of target health.", 2),
	HOLY_HAMMER("legendary.dogmatic_punishment", "On critical hit, increase damage by user's damage absorption.", 0),

	STAT_KILL("stat.kill_count", "Enemies killed: %s", 1),
	STAT_BONUS_CLAW("stat.claw_bonus", "Damage stack limit: %s", 1);

	private final String id, def;
	private final int count;

	LangData(String id, String def, int count) {
		this.id = id;
		this.def = def;
		this.count = count;
	}

	public MutableComponent get(Object... objs) {
		if (objs.length != count)
			throw new IllegalArgumentException("for " + name() + ": expect " + count + " parameters, got " + objs.length);
		var ans = translate(L2Weaponry.MODID + "." + id, objs);
		if (id.startsWith("tool.")) {
			ans = ans.withStyle(ChatFormatting.DARK_GREEN);
		}

		if (id.startsWith("legendary.")) {
			ans = ans.withStyle(ChatFormatting.GOLD);
		}

		if (id.startsWith("stat.")) {
			ans = ans.withStyle(ChatFormatting.AQUA);
		}
		return ans;
	}


	public static void addTranslations(RegistrateLangProvider pvd) {
		for (LangData id : LangData.values()) {
			String[] strs = id.id.split("\\.");
			String str = strs[strs.length - 1];
			pvd.add(L2Weaponry.MODID + "." + id.id, id.def);
		}

		pvd.add("itemGroup.l2weaponry.generated", "L2Weaponry Items");
		pvd.add("attribute.name.shield_defense", "Shield Defense");
		pvd.add("entity.l2weaponry.throwing_axe", "Throwing Axe");
		pvd.add("entity.l2weaponry.javelin", "Javelin");

	}

	public static String asId(String name) {
		return name.toLowerCase(Locale.ROOT);
	}

	public static MutableComponent translate(String key, Object... objs) {
		return Component.translatable(key, objs);
	}

}
