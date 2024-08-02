package dev.xkmc.l2weaponry.init.materials;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2core.serial.configval.BooleanValueCondition;
import dev.xkmc.l2core.serial.recipe.ConditionalRecipeWrapper;
import dev.xkmc.l2core.serial.recipe.DataRecipeWrapper;
import dev.xkmc.l2damagetracker.contents.materials.api.IMatToolType;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.loaders.ItemLayerModelBuilder;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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

	default void addEnchants(List<EnchantmentInstance> list, LWToolTypes type) {
	}

	default void saveRecipe(Supplier<ShapedRecipeBuilder> b, RegistrateRecipeProvider pvd, LWToolTypes type, ResourceLocation id) {
		ItemStack stack = getToolEnchanted(type);
		if (!stack.isComponentsPatchEmpty()) {
			b.get().save(DataRecipeWrapper.of(getProvider(pvd, BooleanValueCondition.of(LWConfig.RECIPE,
					x -> x.defaultEnchantmentOnWeapons, true)), stack), id.withSuffix("_enchanted"));
			b.get().save(getProvider(pvd, BooleanValueCondition.of(LWConfig.RECIPE,
					x -> x.defaultEnchantmentOnWeapons, false)), id);
		} else {
			b.get().save(getProvider(pvd), id);
		}
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

	default RecipeOutput getProvider(RegistrateRecipeProvider pvd, ICondition... cond) {
		return cond.length == 0 ? pvd : ConditionalRecipeWrapper.of(pvd, cond);
	}

	default boolean hasTool(LWToolTypes type) {
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

	default boolean is3D(LWToolTypes type) {
		return false;
	}

	default ItemStack getToolEnchanted(LWToolTypes type) {
		List<EnchantmentInstance> enchs = new ArrayList<>(type.getEnchs());
		addEnchants(enchs, type);
		ItemStack stack = getTool(type).getDefaultInstance();
		if (!enchs.isEmpty()) {
			for (var e : enchs) {
				stack.enchant(e.enchantment, e.level);
			}
		}
		return stack;
	}

}
