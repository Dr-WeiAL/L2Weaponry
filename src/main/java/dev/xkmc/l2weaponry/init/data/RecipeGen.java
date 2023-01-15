package dev.xkmc.l2weaponry.init.data;

import dev.xkmc.l2complements.content.recipe.BurntRecipeBuilder;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2library.repack.registrate.util.DataIngredient;
import dev.xkmc.l2library.repack.registrate.util.entry.BlockEntry;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.BiFunction;

public class RecipeGen {

	private static String currentFolder = "";

	@SuppressWarnings("ConstantConditions")
	public static void genRecipe(RegistrateRecipeProvider pvd) {

	}

	private static ResourceLocation getID(Enchantment item) {
		return new ResourceLocation(L2Weaponry.MODID, currentFolder + ForgeRegistries.ENCHANTMENTS.getKey(item).getPath());
	}

	private static ResourceLocation getID(Item item) {
		return new ResourceLocation(L2Weaponry.MODID, currentFolder + ForgeRegistries.ITEMS.getKey(item).getPath());
	}

	private static void convert(RegistrateRecipeProvider pvd, Item in, Item out, int count) {
		unlock(pvd, new BurntRecipeBuilder(Ingredient.of(in), out.getDefaultInstance(), count)::unlockedBy, in).save(pvd, getID(in));
	}

	private static void storage(RegistrateRecipeProvider pvd, ItemEntry<?> nugget, ItemEntry<?> ingot, BlockEntry<?> block) {
		storage(pvd, nugget::get, ingot::get);
		storage(pvd, ingot::get, block::get);
	}

	public static void storage(RegistrateRecipeProvider pvd, NonNullSupplier<ItemLike> from, NonNullSupplier<ItemLike> to) {
		unlock(pvd, new ShapedRecipeBuilder(to.get(), 1)::unlockedBy, from.get().asItem())
				.pattern("XXX").pattern("XXX").pattern("XXX").define('X', from.get())
				.save(pvd, getID(to.get().asItem()) + "_storage");
		unlock(pvd, new ShapelessRecipeBuilder(from.get(), 9)::unlockedBy, to.get().asItem())
				.requires(to.get()).save(pvd, getID(to.get().asItem()) + "_unpack");
	}

	public static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, InventoryChangeTrigger.TriggerInstance, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCritereon(pvd));
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
