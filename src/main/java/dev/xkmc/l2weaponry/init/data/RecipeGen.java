package dev.xkmc.l2weaponry.init.data;

import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2library.base.ingredients.EnchantmentIngredient;
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
import net.minecraft.world.item.enchantment.Enchantments;
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
		currentFolder = "legendary/";
		{
			unlock(pvd, new ShapedRecipeBuilder(LWItems.STORM_JAVELIN.get(), 1)::unlockedBy, LCItems.GUARDIAN_EYE.get())
					.pattern("cds").pattern("wgd").pattern("jwc")
					.define('d', LWToolMats.POSEIDITE.getTool(LWToolTypes.DAGGER))
					.define('j', LWToolMats.POSEIDITE.getTool(LWToolTypes.JAVELIN))
					.define('s', LWToolMats.POSEIDITE.getTool(LWToolTypes.SPEAR))
					.define('g', LCItems.GUARDIAN_EYE.get())
					.define('c', new EnchantmentIngredient(Enchantments.CHANNELING, 3))
					.define('w', LCItems.STORM_CORE.get())
					.save(pvd, getID(LWItems.STORM_JAVELIN.get()));

			smithing(pvd, LWToolMats.NETHERITE.getTool(LWToolTypes.BATTLE_AXE), LCItems.SOUL_FLAME.get(), LWItems.FLAME_AXE.get());
			smithing(pvd, LWToolMats.NETHERITE.getTool(LWToolTypes.HAMMER), LCItems.BLACKSTONE_CORE.get(), LWItems.BLACK_HAMMER.get());
			smithing(pvd, LWToolMats.DIAMOND.getTool(LWToolTypes.SPEAR), LCItems.HARD_ICE.get(), LWItems.FROZEN_SPEAR.get());

			smithing(pvd, LWToolMats.SHULKERATE.getTool(LWToolTypes.SPEAR), LCItems.VOID_EYE.get(), LWItems.ENDER_SPEAR.get());
			smithing(pvd, LWToolMats.SHULKERATE.getTool(LWToolTypes.JAVELIN), LCItems.VOID_EYE.get(), LWItems.ENDER_JAVELIN.get());
			smithing(pvd, LWToolMats.SHULKERATE.getTool(LWToolTypes.DAGGER), LCItems.VOID_EYE.get(), LWItems.ENDER_DAGGER.get());
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
		for (LWToolTypes t : LWToolTypes.values()) {
			smithing(pvd, base.getTool(t), mat.getIngot(), mat.getTool(t));
		}
	}

	public static void tools(RegistrateRecipeProvider pvd, Item handle, Item ingot, LWToolMats mat) {
		currentFolder = "generated/craft/";
		buildTool(pvd, handle, ingot, mat, LWToolTypes.CLAW, "III", "HLH", "H H");
		buildTool(pvd, handle, ingot, mat, LWToolTypes.DAGGER, " I", "H ");
		buildTool(pvd, handle, ingot, mat, LWToolTypes.HAMMER, "III", "IHI", " H ");
		buildTool(pvd, handle, ingot, mat, LWToolTypes.BATTLE_AXE, "III", "IH ", "H  ");
		buildTool(pvd, handle, ingot, mat, LWToolTypes.SPEAR, " II", " HI", "H  ");
		buildTool(pvd, handle, ingot, mat, LWToolTypes.MACHETE, "III", "ILI", "H H");
		buildTool(pvd, handle, ingot, mat, LWToolTypes.ROUND_SHIELD, " I ", "IHI", " I ");
		buildTool(pvd, handle, ingot, mat, LWToolTypes.PLATE_SHIELD, "III", "IHI", " I ");
		buildTool(pvd, handle, ingot, mat, LWToolTypes.THROWING_AXE, "II", "IH");
		buildTool(pvd, handle, ingot, mat, LWToolTypes.JAVELIN, "  I", " H ", "I  ");
		currentFolder = "generated/upgrade/";
		for (LWToolTypes t : LWToolTypes.values()) {
			smithing(pvd, t.tag, mat.getBlock(), mat.getTool(t));
		}
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
