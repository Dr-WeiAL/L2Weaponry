package dev.xkmc.l2weaponry.init.data;

import dev.xkmc.l2library.repack.registrate.providers.RegistrateLangProvider;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import javax.annotation.Nullable;
import java.util.Locale;

public class LangData {

	public enum IDS {
		;

		final String id, def;
		final int count;

		IDS(String id, String def, int count) {
			this.id = id;
			this.def = def;
			this.count = count;
		}

		public MutableComponent get(Object... objs) {
			if (objs.length != count)
				throw new IllegalArgumentException("for " + name() + ": expect " + count + " parameters, got " + objs.length);
			return translate(L2Weaponry.MODID + "." + id, objs);
		}

	}

	public interface LangEnum<T extends Enum<T> & LangEnum<T>> {

		int getCount();

		@Nullable
		default Class<? extends Enum<?>> mux() {
			return null;
		}

		@SuppressWarnings({"unchecked"})
		default T getThis() {
			return (T) this;
		}

	}

	public static void addTranslations(RegistrateLangProvider pvd) {
		for (IDS id : IDS.values()) {
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
