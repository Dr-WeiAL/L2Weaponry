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
	;

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

	}

	public static String asId(String name) {
		return name.toLowerCase(Locale.ROOT);
	}

	public static MutableComponent translate(String key, Object... objs) {
		return Component.translatable(key, objs);
	}

}
