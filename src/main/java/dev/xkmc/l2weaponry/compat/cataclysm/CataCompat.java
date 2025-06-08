package dev.xkmc.l2weaponry.compat.cataclysm;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.l2core.serial.recipe.ConditionalRecipeWrapper;
import dev.xkmc.l2weaponry.compat.CompatDispatch;
import dev.xkmc.l2weaponry.init.data.LWRecipeGen;
import dev.xkmc.l2weaponry.init.materials.ILWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWGenItem;
import dev.xkmc.l2weaponry.init.materials.LWToolTypes;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

public class CataCompat extends CompatDispatch {

	public static final ItemEntry<Item>[][] ITEMS = LWGenItem.generate(CataToolMats.values());

	public static final ItemEntry<SoulHarvester> SOUL_HARVESTER;
	public static final ItemEntry<AncientTraveller> ANCIENT_TRAVELLER;

	static {
		SOUL_HARVESTER = LWItems.regLegendary("soul_harvester", SoulHarvester::new, LWToolTypes.SCYTHE, CataToolMats.CURSIUM, Rarity.EPIC, false);
		ANCIENT_TRAVELLER = LWItems.regLegendary("ancient_traveller", AncientTraveller::new, LWToolTypes.MACHETE, CataToolMats.WITHERITE, Rarity.EPIC, false);
	}

	@Override
	public ILWToolMats[] values() {
		return CataToolMats.values();
	}

	public void regExtraRecipes(RegistrateRecipeProvider pvd) {
		LWRecipeGen.unlock(pvd, SmithingTransformRecipeBuilder.smithing(
						Ingredient.of(ModItems.IGNITIUM_UPGARDE_SMITHING_TEMPLATE),
						Ingredient.of(CataToolMats.CURSIUM.getTool(LWToolTypes.SCYTHE)),
						Ingredient.of(ModItems.IGNITIUM_INGOT),
						RecipeCategory.COMBAT,
						SOUL_HARVESTER.get())::unlocks,
				ModItems.IGNITIUM_INGOT.get()).save(
				new ConditionalRecipeWrapper(pvd, new ModLoadedCondition(Cataclysm.MODID)),
				SOUL_HARVESTER.getId());

		LWRecipeGen.unlock(pvd, SmithingTransformRecipeBuilder.smithing(
						Ingredient.of(ModItems.SANDSTORM_IN_A_BOTTLE),
						Ingredient.of(CataToolMats.WITHERITE.getTool(LWToolTypes.MACHETE)),
						Ingredient.of(ModItems.ANCIENT_METAL_INGOT),
						RecipeCategory.COMBAT,
						ANCIENT_TRAVELLER.get())::unlocks,
				ModItems.SANDSTORM_IN_A_BOTTLE.get()).save(
				new ConditionalRecipeWrapper(pvd, new ModLoadedCondition(Cataclysm.MODID)),
				ANCIENT_TRAVELLER.getId());

	}

}
