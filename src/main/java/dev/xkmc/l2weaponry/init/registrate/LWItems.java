package dev.xkmc.l2weaponry.init.registrate;

import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import dev.xkmc.l2weaponry.init.materials.LWGenItem;
import dev.xkmc.l2weaponry.init.materials.LWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWToolTypes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

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

	public static final Tab TAB_GENERATED = new Tab("generated", () -> LWItems.GEN_ITEM[LWToolMats.DIAMOND.ordinal()][LWToolTypes.CLAW.ordinal()]);

	static {
		L2Weaponry.REGISTRATE.creativeModeTab(() -> TAB_GENERATED);
	}

	public static final ItemEntry<Item> HANDLE;
	public static final ItemEntry<Item>[][] GEN_ITEM;

	static {
		HANDLE = L2Weaponry.REGISTRATE.item("reinforced_handle", Item::new).register();
		GEN_ITEM = LWGenItem.generate();
	}

	public static void register() {
	}

}
