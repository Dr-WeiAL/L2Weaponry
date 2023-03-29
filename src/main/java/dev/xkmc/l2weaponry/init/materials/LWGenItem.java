package dev.xkmc.l2weaponry.init.materials;

import dev.xkmc.l2library.repack.registrate.providers.DataGenContext;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateItemModelProvider;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.Locale;

public class LWGenItem {

	@SuppressWarnings({"unchecked", "unsafe", "rawtypes"})
	public static ItemEntry<Item>[][] generate() {
		ItemEntry[][] ans = new ItemEntry[LWToolMats.values().length][LWToolTypes.values().length];
		for (int j = 0; j < LWToolTypes.values().length; j++) {
			for (int i = 0; i < LWToolMats.values().length; i++) {
				LWToolMats mat = LWToolMats.values()[i];
				String mat_name = mat.name().toLowerCase(Locale.ROOT);
				LWToolTypes type = LWToolTypes.values()[j];
				String tool_name = type.name().toLowerCase(Locale.ROOT);
				ans[i][j] = L2Weaponry.REGISTRATE.item(mat_name + "_" + tool_name,
								p -> mat.type.getToolConfig().sup().get(mat.type, type, mat.fireRes ? p.fireResistant() : p))
						.model((ctx, pvd) -> model(type, ctx, pvd, mat_name, tool_name))
						.tag(type.tag).defaultLang().register();
			}
		}
		return ans;
	}


	public static <T extends Item> void model(LWToolTypes type, DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd, String id, String suf) {
		if (type == LWToolTypes.ROUND_SHIELD) {
			pvd.withExistingParent(pvd.name(ctx), pvd.modLoc("item/round_shield"))
					.texture("0", pvd.modLoc("item/generated/" + id + "/" + suf))
					.override().predicate(pvd.modLoc("blocking"), 1)
					.model(new ModelFile.UncheckedModelFile(pvd.modLoc("item/" + pvd.name(ctx) + "_blocking"))).end();
			pvd.withExistingParent(pvd.name(ctx) + "_blocking", pvd.modLoc("item/round_shield_blocking"))
					.texture("0", pvd.modLoc("item/generated/" + id + "/" + suf));
		} else if (type == LWToolTypes.PLATE_SHIELD) {
			pvd.withExistingParent(pvd.name(ctx), pvd.modLoc("item/plate_shield"))
					.texture("0", pvd.modLoc("item/generated/" + id + "/" + suf))
					.override().predicate(pvd.modLoc("blocking"), 1)
					.model(new ModelFile.UncheckedModelFile(pvd.modLoc("item/" + pvd.name(ctx) + "_blocking"))).end();
			pvd.withExistingParent(pvd.name(ctx) + "_blocking", pvd.modLoc("item/plate_shield_blocking"))
					.texture("0", pvd.modLoc("item/generated/" + id + "/" + suf));
		} else if (type == LWToolTypes.THROWING_AXE) {
			pvd.handheld(ctx, pvd.modLoc("item/generated/" + id + "/" + suf))
					.override().predicate(pvd.modLoc("throwing"), 1)
					.model(new ModelFile.UncheckedModelFile(pvd.modLoc("item/" + pvd.name(ctx) + "_throwing"))).end();
			pvd.withExistingParent(pvd.name(ctx) + "_throwing", pvd.modLoc("item/handheld_throwing"))
					.texture("layer0", pvd.modLoc("item/generated/" + id + "/" + suf));
		} else if (type == LWToolTypes.JAVELIN) {
			pvd.withExistingParent(pvd.name(ctx), pvd.modLoc("item/long_weapon"))
					.texture("layer0", pvd.modLoc("item/generated/" + id + "/" + suf))
					.override().predicate(pvd.modLoc("throwing"), 1)
					.model(new ModelFile.UncheckedModelFile(pvd.modLoc("item/" + pvd.name(ctx) + "_throwing"))).end();
			pvd.withExistingParent(pvd.name(ctx) + "_throwing", pvd.modLoc("item/long_weapon_throwing"))
					.texture("layer0", pvd.modLoc("item/generated/" + id + "/" + suf));
		} else if (type.customModel() != null) {
			pvd.withExistingParent(pvd.name(ctx), pvd.modLoc("item/" + type.customModel()))
					.texture("layer0", pvd.modLoc("item/generated/" + id + "/" + suf));
		} else {
			pvd.handheld(ctx, pvd.modLoc("item/generated/" + id + "/" + suf));
		}
	}

}
