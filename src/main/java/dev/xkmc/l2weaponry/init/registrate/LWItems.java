package dev.xkmc.l2weaponry.init.registrate;

import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import dev.xkmc.l2weaponry.init.materials.LWGenItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import java.util.function.BiFunction;
import java.util.function.Supplier;

@SuppressWarnings({"rawtypes", "unsafe"})
@MethodsReturnNonnullByDefault
public class LWItems {

	public static class Tab extends CreativeModeTab {

		private final Supplier<ItemEntry> icon;

		public Tab(String id, Supplier<ItemEntry> icon) {
			super(L2Weaponry.MODID + "." + id);
			this.icon = icon;
		}

		@Override
		public ItemStack makeIcon() {
			return icon.get().asStack();
		}
	}

	public static final Tab TAB_GENERATED = new Tab("generated", () -> null);

	static {
		L2Weaponry.REGISTRATE.creativeModeTab(() -> TAB_GENERATED);
	}

	public static final ItemEntry<Item>[][] GEN_ITEM;

	static {
		GEN_ITEM = LWGenItem.generate();
	}

	public static <T extends Item> ItemEntry<T> simpleItem(String id, BiFunction<Item.Properties, Supplier<MutableComponent>, T> func, Rarity r, Supplier<MutableComponent> sup) {
		return L2Weaponry.REGISTRATE.item(id, p -> func.apply(p.fireResistant().rarity(r), sup)).defaultModel().defaultLang().register();
	}

	public static void register() {
	}

}
