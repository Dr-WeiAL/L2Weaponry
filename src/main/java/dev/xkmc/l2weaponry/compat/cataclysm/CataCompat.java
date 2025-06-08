package dev.xkmc.l2weaponry.compat.cataclysm;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.l2library.serial.recipe.ConditionalRecipeWrapper;
import dev.xkmc.l2weaponry.compat.CompatDispatch;
import dev.xkmc.l2weaponry.init.data.RecipeGen;
import dev.xkmc.l2weaponry.init.materials.ILWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWGenItem;
import dev.xkmc.l2weaponry.init.materials.LWToolTypes;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;

public class CataCompat extends CompatDispatch {

	public static final ItemEntry<Item>[][] ITEMS = LWGenItem.generate(CataToolMats.values());

	public static final ItemEntry<SoulHarvester> SOUL_HARVESTER;

	static {
		SOUL_HARVESTER = LWItems.regLegendary("soul_harvester", SoulHarvester::new, LWToolTypes.SCYTHE, CataToolMats.CURSIUM, Rarity.EPIC, false);
	}

	@Override
	public ILWToolMats[] values() {
		return CataToolMats.values();
	}

	public void regExtraRecipes(RegistrateRecipeProvider pvd) {
		RecipeGen.unlock(pvd, SmithingTransformRecipeBuilder.smithing(
						Ingredient.of(ModItems.IGNITIUM_UPGARDE_SMITHING_TEMPLATE.get()),
						Ingredient.of(CataToolMats.CURSIUM.getTool(LWToolTypes.SCYTHE)),
						Ingredient.of(ModItems.IGNITIUM_INGOT.get()),
						RecipeCategory.COMBAT,
						SOUL_HARVESTER.get())::unlocks,
				ModItems.IGNITIUM_INGOT.get()).save(
				ConditionalRecipeWrapper.mod(pvd, Cataclysm.MODID),
				SOUL_HARVESTER.getId());

	}

}
