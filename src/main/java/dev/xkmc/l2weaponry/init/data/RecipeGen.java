package dev.xkmc.l2weaponry.init.data;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import dev.xkmc.l2complements.content.enchantment.core.EnchantmentRecipeBuilder;
import dev.xkmc.l2complements.init.materials.LCMats;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2library.compat.jeed.JeedDataGenerator;
import dev.xkmc.l2library.serial.ingredients.EnchantmentIngredient;
import dev.xkmc.l2library.serial.recipe.AbstractSmithingRecipe;
import dev.xkmc.l2weaponry.compat.twilightforest.TFToolMats;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import dev.xkmc.l2weaponry.init.materials.ILWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWToolTypes;
import dev.xkmc.l2weaponry.init.registrate.LWEnchantments;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.TwilightForestMod;

import java.util.function.BiFunction;

public class RecipeGen {

	private static String currentFolder = "";

	public static void genRecipe(RegistrateRecipeProvider pvd) {
		currentFolder = "misc/";
		{
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, LWItems.HANDLE.get(), 2)::unlockedBy, Items.STICK)
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
				if (mat.getNugget() != Items.AIR) {
					currentFolder = "generated/recycle/";
					for (LWToolTypes type : LWToolTypes.values()) {
						smelting(pvd, mat.getTool(type), mat.getNugget(), 0.1f);
					}
				}
			}

			if (ModList.get().isLoaded(TwilightForestMod.ID)) {
				for (ILWToolMats mat : TFToolMats.values()) {
					tools(pvd, mat.getStick(), mat.getIngot(), mat);
				}
			}
		}
		currentFolder = "legendary/";
		{
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.COMBAT, LWItems.STORM_JAVELIN.get(), 1)::unlockedBy, LCItems.GUARDIAN_EYE.get())
					.pattern("cds").pattern("wgd").pattern("jwc")
					.define('d', LWToolMats.POSEIDITE.getTool(LWToolTypes.DAGGER))
					.define('j', LWToolMats.POSEIDITE.getTool(LWToolTypes.JAVELIN))
					.define('s', LWToolMats.POSEIDITE.getTool(LWToolTypes.SPEAR))
					.define('g', LCItems.GUARDIAN_EYE.get())
					.define('c', new EnchantmentIngredient(Enchantments.CHANNELING, 1))
					.define('w', LCItems.STORM_CORE.get())
					.save(pvd, getID(LWItems.STORM_JAVELIN.get()));

			smithing(pvd, LWToolMats.IRON.getTool(LWToolTypes.SPEAR), LCItems.HARD_ICE.get(), LWItems.FROZEN_SPEAR.get());
			smithing(pvd, LWToolMats.NETHERITE.getTool(LWToolTypes.BATTLE_AXE), LCItems.SOUL_FLAME.get(), LWItems.FLAME_AXE.get());
			smithing(pvd, LWToolMats.NETHERITE.getTool(LWToolTypes.HAMMER), LCItems.BLACKSTONE_CORE.get(), LWItems.BLACK_HAMMER.get());
			smithing(pvd, LWToolMats.NETHERITE.getTool(LWToolTypes.THROWING_AXE), LCItems.BLACKSTONE_CORE.get(), LWItems.BLACK_AXE.get());
			smithing(pvd, LWToolMats.SHULKERATE.getTool(LWToolTypes.SPEAR), LCItems.VOID_EYE.get(), LWItems.ENDER_SPEAR.get());
			smithing(pvd, LWToolMats.SHULKERATE.getTool(LWToolTypes.JAVELIN), LCItems.STORM_CORE.get(), LWItems.ENDER_JAVELIN.get());
			smithing(pvd, LWToolMats.SHULKERATE.getTool(LWToolTypes.DAGGER), LCItems.VOID_EYE.get(), LWItems.ENDER_DAGGER.get());
			smithing(pvd, LWToolMats.SHULKERATE.getTool(LWToolTypes.MACHETE), LCItems.STORM_CORE.get(), LWItems.ENDER_MACHETE.get());
			smithing(pvd, LWToolMats.SCULKIUM.getTool(LWToolTypes.DAGGER), LCItems.RESONANT_FEATHER.get(), LWItems.ABYSS_DAGGER.get());
			smithing(pvd, LWToolMats.SCULKIUM.getTool(LWToolTypes.MACHETE), LCItems.RESONANT_FEATHER.get(), LWItems.ABYSS_MACHETE.get());
			smithing(pvd, LWToolMats.SCULKIUM.getTool(LWToolTypes.HAMMER), LCItems.RESONANT_FEATHER.get(), LWItems.ABYSS_HAMMER.get());
			smithing(pvd, LWToolMats.SCULKIUM.getTool(LWToolTypes.BATTLE_AXE), LCItems.RESONANT_FEATHER.get(), LWItems.ABYSS_AXE.get());
			smithing(pvd, LWToolMats.TOTEMIC_GOLD.getTool(LWToolTypes.CLAW), LCItems.CURSED_DROPLET.get(), LWItems.BLOOD_CLAW.get());
			smithing(pvd, LWToolMats.TOTEMIC_GOLD.getTool(LWToolTypes.BATTLE_AXE), LCItems.LIFE_ESSENCE.get(), LWItems.HOLY_AXE.get());
			smithing(pvd, LWToolMats.TOTEMIC_GOLD.getTool(LWToolTypes.HAMMER), LCItems.SUN_MEMBRANE.get(), LWItems.HOLY_HAMMER.get());
			smithing(pvd, LWToolMats.ETERNIUM.getTool(LWToolTypes.CLAW), LCItems.SUN_MEMBRANE.get(), LWItems.CHEATER_CLAW.get());
			smithing(pvd, LWToolMats.ETERNIUM.getTool(LWToolTypes.MACHETE), LCItems.VOID_EYE.get(), LWItems.CHEATER_MACHETE.get());

		}

		currentFolder = "enchantments/";
		{
			unlock(pvd, new EnchantmentRecipeBuilder(LWEnchantments.HEAVY.get(), 1)::unlockedBy, Items.ANVIL)
					.pattern("AAA").pattern("CBC").pattern("LDL")
					.define('A', Items.ANVIL)
					.define('B', Items.BOOK)
					.define('C', Items.GOLD_INGOT)
					.define('D', LWItems.HANDLE.get())
					.define('L', Items.LAPIS_LAZULI)
					.save(pvd, getID(LWEnchantments.HEAVY.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LWEnchantments.NO_AGGRO.get(), 1)::unlockedBy, LCItems.VOID_EYE.get())
					.pattern("LAL").pattern("CBC").pattern("LCL")
					.define('A', LCItems.VOID_EYE)
					.define('B', Items.BOOK)
					.define('C', LCMats.SHULKERATE.getIngot())
					.define('L', Items.LAPIS_LAZULI)
					.save(pvd, getID(LWEnchantments.NO_AGGRO.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LWEnchantments.ENDER_HAND.get(), 1)::unlockedBy, LCMats.POSEIDITE.getIngot())
					.pattern("LAL").pattern("CBC").pattern("LDL")
					.define('A', LCMats.POSEIDITE.getIngot())
					.define('B', Items.BOOK)
					.define('C', Items.ENDER_EYE)
					.define('D', LWItems.HANDLE.get())
					.define('L', Items.LAPIS_LAZULI)
					.save(pvd, getID(LWEnchantments.ENDER_HAND.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LWEnchantments.HEAVY_SHIELD.get(), 1)::unlockedBy, Items.ANVIL)
					.pattern("DAD").pattern("CBC").pattern("LDL")
					.define('A', Items.ANVIL)
					.define('B', Items.BOOK)
					.define('D', Items.NETHERITE_INGOT)
					.define('C', LWItems.HANDLE.get())
					.define('L', Items.LAPIS_LAZULI)
					.save(pvd, getID(LWEnchantments.HEAVY_SHIELD.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LWEnchantments.HARD_SHIELD.get(), 1)::unlockedBy, LCMats.SHULKERATE.getIngot())
					.pattern("DLD").pattern("CBC").pattern("LDL")
					.define('B', Items.BOOK)
					.define('D', LCMats.SHULKERATE.getIngot())
					.define('C', LWItems.HANDLE.get())
					.define('L', Items.LAPIS_LAZULI)
					.save(pvd, getID(LWEnchantments.HARD_SHIELD.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LWEnchantments.ENERGIZED_WILL.get(), 1)::unlockedBy, LCItems.SOUL_FLAME.get())
					.pattern("CLC").pattern("DBD").pattern("CLC")
					.define('B', Items.BOOK)
					.define('C', Items.REDSTONE)
					.define('D', LCItems.SOUL_FLAME)
					.define('L', Items.LAPIS_LAZULI)
					.save(pvd, getID(LWEnchantments.ENERGIZED_WILL.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LWEnchantments.RAISED_SPIRIT.get(), 1)::unlockedBy, LCItems.SOUL_FLAME.get())
					.pattern("CLC").pattern("DBD").pattern("CLC")
					.define('B', Items.BOOK)
					.define('C', Items.GLOWSTONE_DUST)
					.define('D', LCItems.SOUL_FLAME)
					.define('L', Items.LAPIS_LAZULI)
					.save(pvd, getID(LWEnchantments.RAISED_SPIRIT.get()));
		}

		// jeed
		{
			JeedDataGenerator jeed = new JeedDataGenerator(L2Weaponry.MODID);
			jeed.add(LWItems.BLACK_HAMMER.get(), LCEffects.STONE_CAGE.get());
			jeed.add(LWItems.ENDER_JAVELIN.get(), MobEffects.SLOW_FALLING, MobEffects.LEVITATION);
			jeed.add(LWItems.ENDER_MACHETE.get(), MobEffects.SLOW_FALLING, MobEffects.LEVITATION);
			jeed.add(LWItems.FLAME_AXE.get(), LCEffects.FLAME.get());
			jeed.add(LWItems.FROZEN_SPEAR.get(), LCEffects.ICE.get());
			jeed.generate(pvd);

			if (ModList.get().isLoaded(TwilightForestMod.ID)) {
				jeed = new JeedDataGenerator(L2Weaponry.MODID, TwilightForestMod.ID);
				jeed.add(TFToolMats.IRONWOOD.getTool(LWToolTypes.ROUND_SHIELD), MobEffects.DAMAGE_RESISTANCE);
				jeed.add(TFToolMats.IRONWOOD.getTool(LWToolTypes.PLATE_SHIELD), MobEffects.DAMAGE_RESISTANCE);
				jeed.add(TFToolMats.FIERY.getTool(LWToolTypes.ROUND_SHIELD), MobEffects.FIRE_RESISTANCE);
				jeed.add(TFToolMats.FIERY.getTool(LWToolTypes.PLATE_SHIELD), MobEffects.FIRE_RESISTANCE);
				for (var e : LWToolTypes.values()) {
					jeed.add(TFToolMats.STEELEAF.getTool(e), LCEffects.BLEED.get());
				}
				jeed.generate(pvd);
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

	@SuppressWarnings("ConstantConditions")
	private static ResourceLocation getID(Item item, String suffix) {
		return new ResourceLocation(L2Weaponry.MODID, currentFolder + ForgeRegistries.ITEMS.getKey(item).getPath() + suffix);
	}

	public static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, InventoryChangeTrigger.TriggerInstance, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCritereon(pvd));
	}

	private static void buildTool(RegistrateRecipeProvider pvd, Item handle, Item ingot, ILWToolMats mat, LWToolTypes type, String... strs) {
		var b = unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.TOOLS, mat.getTool(type), 1)::unlockedBy, mat.getIngot());
		boolean leather = false;
		for (String str : strs) {
			b = b.pattern(str);
			leather |= str.indexOf('L') >= 0;
		}
		b.define('I', ingot).define('H', handle);
		if (leather) b = b.define('L', Items.LEATHER);
		mat.saveRecipe(b, pvd, type, getID(mat.getTool(type)));
	}

	public static void upgrade(RegistrateRecipeProvider pvd, LWToolMats base, LWToolMats mat) {
		currentFolder = "generated/upgrade/";
		for (LWToolTypes t : LWToolTypes.values()) {
			smithing(pvd, base.getTool(t), mat.getIngot(), mat.getTool(t));
		}
	}

	public static void tools(RegistrateRecipeProvider pvd, Item handle, Item ingot, ILWToolMats mat) {
		currentFolder = "generated/craft/";
		buildTool(pvd, handle, ingot, mat, LWToolTypes.CLAW, "III", "HLH", "H H");
		buildTool(pvd, handle, ingot, mat, LWToolTypes.DAGGER, " I", "H ");
		buildTool(pvd, handle, ingot, mat, LWToolTypes.HAMMER, "III", "IHI", " H ");
		buildTool(pvd, handle, ingot, mat, LWToolTypes.BATTLE_AXE, "III", "IH ", "H  ");
		buildTool(pvd, handle, ingot, mat, LWToolTypes.SPEAR, " II", " HI", "H  ");
		buildTool(pvd, handle, ingot, mat, LWToolTypes.MACHETE, "  I", " I ", " H ");
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
		unlock(pvd, SmithingTransformRecipeBuilder.smithing(AbstractSmithingRecipe.TEMPLATE_PLACEHOLDER, Ingredient.of(in), Ingredient.of(mat),
				RecipeCategory.COMBAT, out)::unlocks, mat).save(pvd, getID(out));
	}

	public static void smithing(RegistrateRecipeProvider pvd, Item in, Item mat, Item out) {
		Ingredient ing = mat == Items.NETHERITE_INGOT ? Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE) :
				AbstractSmithingRecipe.TEMPLATE_PLACEHOLDER;
		unlock(pvd, SmithingTransformRecipeBuilder.smithing(ing, Ingredient.of(in), Ingredient.of(mat),
				RecipeCategory.COMBAT, out)::unlocks, mat).save(pvd, getID(out));
	}

	public static void smelting(RegistrateRecipeProvider pvd, Item source, Item result, float experience) {
		unlock(pvd, SimpleCookingRecipeBuilder.smelting(Ingredient.of(source), RecipeCategory.MISC, result, experience, 200)::unlockedBy, source)
				.save(pvd, getID(source));
	}

	public static void blasting(RegistrateRecipeProvider pvd, Item source, Item result, float experience) {
		unlock(pvd, SimpleCookingRecipeBuilder.smelting(Ingredient.of(source), RecipeCategory.MISC, result, experience, 200)::unlockedBy, source)
				.save(pvd, getID(source));
	}

}
