package dev.xkmc.l2weaponry.init.materials;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.l2weaponry.content.client.WeaponBEWLR;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class LWGenItem {

	public static LWItemModelProvider ALT;

	@SuppressWarnings({"unchecked", "unsafe", "rawtypes"})
	public static ItemEntry<Item>[][] generate(ILWToolMats... values) {
		ItemEntry[][] ans = new ItemEntry[values.length][LWToolTypes.values().length];
		for (int j = 0; j < LWToolTypes.values().length; j++) {
			for (int i = 0; i < values.length; i++) {
				ILWToolMats mat = values[i];
				LWToolTypes type = LWToolTypes.values()[j];
				if (!mat.hasTool(type)) continue;
				String mat_name = mat.name().toLowerCase(Locale.ROOT);
				String english = mat.englishName();
				String tool_name = type.name().toLowerCase(Locale.ROOT);
				ans[i][j] = L2Weaponry.REGISTRATE.item(mat_name + "_" + tool_name,
								p -> mat.type().getToolConfig().sup().get(mat.type(), type, mat.fireRes() ? p.fireResistant() : p))
						.transform(e -> mat.isOptional() ? e.asOptional() : e).tag(type.tag)
						.model((ctx, pvd) -> model(type, mat, ctx, pvd, mat_name, tool_name, mat.is3D(type)))
						.tab(LWItems.TAB.key(), (ctx, e) -> e.accept(LWConfig.RECIPE.defaultEnchantmentOnWeapons.get() ?
								mat.getToolEnchanted(e.getParameters().holders(), type) : mat.getTool(type).getDefaultInstance()))
						.transform(e -> type == LWToolTypes.NUNCHAKU ? e.clientExtension(() -> () -> WeaponBEWLR.EXTENSIONS) : e)
						.lang(mat.prefix() + RegistrateLangProvider.toEnglishName(english + "_" + tool_name)).register();
			}
		}
		return ans;
	}

	public static <T extends Item> void model(LWToolTypes type, ILWToolMats mat, DataGenContext<Item, T> ctx,
											  RegistrateItemModelProvider pvd, String matName, String toolName,
											  boolean is3D) {
		boolean iconic = type.hasIcon();
		ResourceLocation tex3d = pvd.modLoc("item/3d/" + toolName + "/" + matName);
		ResourceLocation icon = pvd.modLoc("item/icon/" + matName + "/" + toolName);
		ResourceLocation tex2d = pvd.modLoc("item/generated/" + matName + "/" + toolName);
		if (!iconic) icon = tex2d;
		if (is3D) {
			if (matName.equals("legendary")) {
				// TODO javelin
				genSeparate(pvd, pvd.getBuilder(ctx.getName()), mat, type, "3d_legendary/" + toolName, null, icon);
			} else {
				String parent = "3d_" + toolName;
				var model = pvd.getBuilder(ctx.getName());
				if (type == LWToolTypes.JAVELIN) {
					throwing(ctx, pvd, model, mat, type, parent + "_throwing", tex3d, iconic, icon);
				}
				genSeparate(pvd, model, mat, type, parent, tex3d, icon);
			}
			pvd = ALT;
		}
		if (type == LWToolTypes.ROUND_SHIELD) {
			shield(ctx, pvd, mat, type, "round", tex2d);
		} else if (type == LWToolTypes.PLATE_SHIELD) {
			shield(ctx, pvd, mat, type, "plate", tex2d);
		} else {
			var model = defaultModel(ctx, pvd, mat, type, iconic, tex2d, icon);
			if (type == LWToolTypes.THROWING_AXE) {
				throwing(ctx, pvd, model, mat, type, "handheld_throwing", tex2d, iconic, icon);
			} else if (type == LWToolTypes.JAVELIN) {
				throwing(ctx, pvd, model, mat, type, "long_weapon_throwing", tex2d, iconic, icon);
			} else if (type == LWToolTypes.NUNCHAKU) {
				spinning(ctx, pvd, model, mat, type, tex2d);
			}
		}
	}

	public static <T extends Item> ItemModelBuilder defaultModel(
			DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd, ILWToolMats mat, LWToolTypes type,
			boolean iconic, ResourceLocation tex2d, ResourceLocation icon) {
		ItemModelBuilder model;
		if (type.customModel() != null) {
			if (iconic) {
				model = pvd.getBuilder(pvd.name(ctx));
				genSeparate(pvd, model, mat, type, type.customModel(), tex2d, icon);
			} else {
				model = pvd.withExistingParent(pvd.name(ctx), pvd.modLoc("item/" + type.customModel()))
						.texture("layer0", tex2d);
				mat.model(type, model);
			}
		} else {
			model = pvd.handheld(ctx, tex2d);
			mat.model(type, model);
		}
		return model;
	}

	private static <T extends Item> void shield(
			DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd, ILWToolMats mat, LWToolTypes type,
			String kind, ResourceLocation tex2d) {
		String str = mat.emissive() ? "_emissive" : "";
		mat.model(type, pvd.withExistingParent(pvd.name(ctx), pvd.modLoc("item/" + kind + "_shield" + str))
						.texture("0", tex2d))
				.override().predicate(pvd.modLoc("blocking"), 1)
				.model(new ModelFile.UncheckedModelFile(pvd.modLoc("item/" + pvd.name(ctx) + "_blocking"))).end();
		mat.model(type, pvd.withExistingParent(pvd.name(ctx) + "_blocking", pvd.modLoc("item/" + kind + "_shield_blocking" + str))
				.texture("0", tex2d));
	}

	private static <T extends Item> void throwing(
			DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd, ItemModelBuilder model,
			ILWToolMats mat, LWToolTypes type, String throwing, ResourceLocation tex2d,
			boolean iconic, ResourceLocation icon) {
		model.override().predicate(pvd.modLoc("throwing"), 1)
				.model(new ModelFile.UncheckedModelFile(pvd.modLoc("item/" + pvd.name(ctx) + "_throwing"))).end();
		if (iconic) {
			genSeparate(pvd, pvd.getBuilder(pvd.name(ctx) + "_throwing"), mat, type, throwing, tex2d, icon);
		} else {
			mat.model(type, pvd.withExistingParent(pvd.name(ctx) + "_throwing", pvd.modLoc("item/" + throwing)).texture("layer0", tex2d));
		}
	}

	private static <T extends Item> void spinning(
			DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd, ItemModelBuilder model,
			ILWToolMats mat, LWToolTypes type, ResourceLocation tex2d) {
		model.override().predicate(pvd.modLoc("spinning"), 1)
				.model(new ModelFile.UncheckedModelFile(pvd.modLoc("item/nunchaku_spinning"))).end();
		mat.model(type, pvd.withExistingParent(pvd.name(ctx) + "_roll", pvd.modLoc("item/nunchaku_roll"))
				.texture("layer0", tex2d.withSuffix("_roll")));
		mat.model(type, pvd.withExistingParent(pvd.name(ctx) + "_unroll", pvd.modLoc("item/nunchaku_unroll"))
				.texture("layer0", tex2d.withSuffix("_unroll")));
	}

	private static void genSeparate(
			RegistrateItemModelProvider pvd, ItemModelBuilder model, ILWToolMats mat, LWToolTypes type,
			String parent, @Nullable ResourceLocation tex, ResourceLocation icon) {
		model.guiLight(BlockModel.GuiLight.FRONT);
		var baseModel = new ItemModelBuilder(null, pvd.existingFileHelper)
				.parent(pvd.getExistingFile(pvd.modLoc("item/" + parent)));
		if (tex != null) baseModel.texture("layer0", tex);
		var iconModel = new ItemModelBuilder(null, pvd.existingFileHelper)
				.parent(pvd.getExistingFile(pvd.mcLoc("item/generated")))
				.texture("layer0", icon);
		model.customLoader(SeparateTransformsModelBuilder::begin)
				.base(mat.model(type, baseModel))
				.perspective(ItemDisplayContext.GUI,
						mat.model(type, iconModel));
	}


}