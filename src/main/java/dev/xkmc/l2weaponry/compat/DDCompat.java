package dev.xkmc.l2weaponry.compat;

import com.kyanite.deeperdarker.DeeperDarker;
import com.kyanite.deeperdarker.content.DDItems;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2weaponry.init.data.RecipeGen;
import dev.xkmc.l2weaponry.init.materials.LWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWToolTypes;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

public class DDCompat {

	public static void onRecipeGen(RegistrateRecipeProvider pvd) {
		var mat = LWToolMats.SCULKIUM;
		var base = LWToolMats.NETHERITE;
		var cond = mat.getProvider(pvd, new ModLoadedCondition(DeeperDarker.MOD_ID));
		for (LWToolTypes t : LWToolTypes.values()) {
			if (!mat.hasTool(t) || !base.hasTool(t)) continue;
			RecipeGen.unlock(pvd, SmithingTransformRecipeBuilder.smithing(
							Ingredient.of(DDItems.WARDEN_UPGRADE_SMITHING_TEMPLATE.get()),
							Ingredient.of(base.getTool(t)),
							Ingredient.of(DDItems.REINFORCED_ECHO_SHARD.get()),
							RecipeCategory.COMBAT, mat.getTool(t))::unlocks, mat.getBlock())
					.save(cond, RecipeGen.getID(mat.getTool(t)));
		}
	}

}
