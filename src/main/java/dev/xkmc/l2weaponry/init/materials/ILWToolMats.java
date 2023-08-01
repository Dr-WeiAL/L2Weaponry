package dev.xkmc.l2weaponry.init.materials;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2damagetracker.contents.materials.api.IMatToolType;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;

public interface ILWToolMats {

	String name();

	IMatToolType type();

	boolean fireRes();

	Item getTool(LWToolTypes type);

	Item getIngot();

	Item getBlock();

	Item getStick();

	default void saveRecipe(ShapedRecipeBuilder b, RegistrateRecipeProvider pvd, LWToolTypes type, ResourceLocation id) {
		b.save(pvd, id);
	}

	default String englishName() {
		return name();
	}

	default ItemModelBuilder model(ItemModelBuilder b) {
		return b;
	}
}
