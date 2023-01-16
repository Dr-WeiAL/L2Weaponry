package dev.xkmc.l2weaponry.init.data;

import dev.xkmc.l2library.repack.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2library.repack.registrate.util.DataIngredient;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import dev.xkmc.l2weaponry.init.materials.LWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWToolTypes;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.BiFunction;

public class RecipeGen {

	private static String currentFolder = "";

	public static void genRecipe(RegistrateRecipeProvider pvd) {
		currentFolder = "misc/";
		{
			unlock(pvd, new ShapedRecipeBuilder(LWItems.HANDLE.get(), 2)::unlockedBy, Items.STICK)
					.pattern(" SI").pattern("SIS").pattern("IS ")
					.define('S', Items.STICK)
					.define('I', Items.COPPER_INGOT)
					.save(pvd, getID(LWItems.HANDLE.get()));
		}
		currentFolder = "generated/";
		{
			for (LWToolMats mat : LWToolMats.values()) {
				if (mat == LWToolMats.NETHERITE) {
					upgrade(pvd, LWToolMats.DIAMOND, LWToolMats.NETHERITE);
				} else {
					tools(pvd, mat.getStick(), mat.getToolIngot(), mat);
				}
			}
		}
	}

	@SuppressWarnings("ConstantConditions")
	private static ResourceLocation getID(Enchantment item) {
		return new ResourceLocation(L2Weaponry.MODID, currentFolder + ForgeRegistries.ENCHANTMENTS.getKey(item).getPath());
	}

	@SuppressWarnings("ConstantConditions")
	private static ResourceLocation getID(Item item) {
		return new ResourceLocation(L2Weaponry.MODID, currentFolder + ForgeRegistries.ITEMS.getKey(item).getPath());
	}

	public static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, InventoryChangeTrigger.TriggerInstance, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCritereon(pvd));
	}

	private static void buildTool(RegistrateRecipeProvider pvd, Item handle, Item ingot, LWToolMats mat, LWToolTypes type, String... strs) {
		var b = unlock(pvd, new ShapedRecipeBuilder(mat.getTool(type), 1)::unlockedBy, mat.getIngot());
		boolean leather = false;
		for (String str : strs) {
			b = b.pattern(str);
			leather |= str.indexOf('L') >= 0;
		}
		b.define('I', ingot).define('H', handle);
		if (leather) b = b.define('L', Items.LEATHER);
		b.save(pvd, getID(mat.getTool(type)));
	}

	public static void upgrade(RegistrateRecipeProvider pvd, LWToolMats base, LWToolMats mat) {
		currentFolder = "generated/upgrade/";
		smithing(pvd, base.getTool(LWToolTypes.CLAW), mat.getIngot(), mat.getTool(LWToolTypes.CLAW));
		smithing(pvd, base.getTool(LWToolTypes.DAGGER), mat.getIngot(), mat.getTool(LWToolTypes.DAGGER));
		smithing(pvd, base.getTool(LWToolTypes.HAMMER), mat.getIngot(), mat.getTool(LWToolTypes.HAMMER));
		smithing(pvd, base.getTool(LWToolTypes.BATTLE_AXE), mat.getIngot(), mat.getTool(LWToolTypes.BATTLE_AXE));
		smithing(pvd, base.getTool(LWToolTypes.SPEAR), mat.getIngot(), mat.getTool(LWToolTypes.SPEAR));
	}

	public static void tools(RegistrateRecipeProvider pvd, Item handle, Item ingot, LWToolMats mat) {
		currentFolder = "generated/craft/";
		buildTool(pvd, handle, ingot, mat, LWToolTypes.CLAW, "III", "HLH", "H H");
		buildTool(pvd, handle, ingot, mat, LWToolTypes.DAGGER, " I", "H ");
		buildTool(pvd, handle, ingot, mat, LWToolTypes.HAMMER, "III", "IHI", " H ");
		buildTool(pvd, handle, ingot, mat, LWToolTypes.BATTLE_AXE, "III", "IH ", "H  ");
		buildTool(pvd, handle, ingot, mat, LWToolTypes.SPEAR, " II", " HI", "H  ");
		currentFolder = "generated/upgrade/";
		smithing(pvd, TagGen.CLAW, mat.getBlock(), mat.getTool(LWToolTypes.CLAW));
		smithing(pvd, TagGen.DAGGER, mat.getIngot(), mat.getTool(LWToolTypes.DAGGER));
		smithing(pvd, TagGen.HAMMER, mat.getIngot(), mat.getTool(LWToolTypes.HAMMER));
		smithing(pvd, TagGen.BATTLE_AXE, mat.getIngot(), mat.getTool(LWToolTypes.BATTLE_AXE));
		smithing(pvd, TagGen.SPEAR, mat.getIngot(), mat.getTool(LWToolTypes.SPEAR));

	}

	public static void smithing(RegistrateRecipeProvider pvd, TagKey<Item> in, Item mat, Item out) {
		unlock(pvd, UpgradeRecipeBuilder.smithing(Ingredient.of(in), Ingredient.of(mat), out)::unlocks, mat).save(pvd, getID(out));
	}

	public static void smithing(RegistrateRecipeProvider pvd, Item in, Item mat, Item out) {
		unlock(pvd, UpgradeRecipeBuilder.smithing(Ingredient.of(in), Ingredient.of(mat), out)::unlocks, mat).save(pvd, getID(out));
	}

	public static void smelting(RegistrateRecipeProvider pvd, Item source, Item result, float experience) {
		unlock(pvd, SimpleCookingRecipeBuilder.cooking(Ingredient.of(source), result, experience, 200, RecipeSerializer.SMELTING_RECIPE)::unlockedBy, source)
				.save(pvd, getID(source));
	}

	public static void blasting(RegistrateRecipeProvider pvd, Item source, Item result, float experience) {
		unlock(pvd, SimpleCookingRecipeBuilder.cooking(Ingredient.of(source), result, experience, 200, RecipeSerializer.BLASTING_RECIPE)::unlockedBy, source)
				.save(pvd, getID(source));
	}

}
