package dev.xkmc.l2weaponry.init.materials;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2damagetracker.contents.materials.api.IMatToolType;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.loaders.ItemLayerModelBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface ILWToolMats {

	String name();

	IMatToolType type();

	boolean fireRes();

	Item getTool(LWToolTypes type);

	Item getIngot();

	Item getBlock();

	Item getStick();

	default Item getChain() {
		return Items.CHAIN;
	}

	default void saveRecipe(ShapedRecipeBuilder b, RegistrateRecipeProvider pvd, LWToolTypes type, ResourceLocation id) {
		b.save(getProvider(pvd), id);
	}

	default String englishName() {
		return name();
	}

	default ItemModelBuilder model(LWToolTypes type, ItemModelBuilder b) {
		if (emissive() && type != LWToolTypes.ROUND_SHIELD && type != LWToolTypes.PLATE_SHIELD) {
			return b.customLoader(ItemLayerModelBuilder::begin).emissive(15, 15, 0).end();
		} else {
			return b;
		}
	}

	default boolean emissive() {
		return false;
	}

	default Consumer<FinishedRecipe> getProvider(RegistrateRecipeProvider pvd) {
		return pvd;
	}

	default boolean hasTool(LWToolTypes type) {
		if (type == LWToolTypes.NUNCHAKU) {
			return this == LWToolMats.IRON || this == LWToolMats.GOLD || this == LWToolMats.DIAMOND;//TODO
		}
		return true;
	}

	default String prefix() {
		return "";
	}

	default boolean isOptional() {
		return false;
	}

	@Nullable
	default ILWToolMats getBaseUpgrade() {
		return null;
	}

}
