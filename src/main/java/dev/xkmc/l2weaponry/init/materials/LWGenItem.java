package dev.xkmc.l2weaponry.init.materials;

import dev.xkmc.l2library.repack.registrate.providers.DataGenContext;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateItemModelProvider;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.minecraft.world.item.Item;

import java.util.Locale;

public class LWGenItem {

	@SuppressWarnings({"unchecked", "unsafe", "rawtypes"})
	public static ItemEntry<Item>[][] generate() {
		ItemEntry[][] ans = new ItemEntry[LWToolMats.values().length][LWToolTypes.values().length];
		for (int i = 0; i < LWToolMats.values().length; i++) {
			LWToolMats mat = LWToolMats.values()[i];
			String mat_name = mat.name().toLowerCase(Locale.ROOT);
			for (int j = 0; j < LWToolTypes.values().length; j++) {
				LWToolTypes type = LWToolTypes.values()[j];
				String tool_name = type.name().toLowerCase(Locale.ROOT);
				ans[i][j] = L2Weaponry.REGISTRATE.item(mat_name + "_" + tool_name,
								p -> mat.type.getToolConfig().sup().get(mat.type, type, p))
						.model((ctx, pvd) -> {
							if (type.longItem()) longItem(ctx, pvd, mat_name, tool_name);
							else handHeld(ctx, pvd, mat_name, tool_name);
						}).tag(type.tag).defaultLang().register();
			}
		}
		return ans;
	}

	public static <T extends Item> void handHeld(DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd, String id, String suf) {
		pvd.handheld(ctx, pvd.modLoc("item/generated/" + id + "/" + suf));
	}

	public static <T extends Item> void longItem(DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd, String id, String suf) {
		pvd.withExistingParent(pvd.name(ctx), pvd.modLoc("item/long_weapon"))
				.texture("layer0", pvd.modLoc("item/generated/" + id + "/" + suf));
	}

}
