package dev.xkmc.l2weaponry.init.materials;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2damagetracker.contents.materials.api.IMatToolType;
import dev.xkmc.l2library.serial.recipe.NBTRecipe;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.loaders.ItemLayerModelBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
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

	default void addEnchants(List<EnchantmentInstance> list, LWToolTypes type) {
	}

	default void saveRecipe(ShapedRecipeBuilder b, RegistrateRecipeProvider pvd, LWToolTypes type, ResourceLocation id) {
		var cond = getProvider(pvd);
		ItemStack stack = getToolEnchanted(type);
		if (stack.isEnchanted()) {
			if (stack.getTag() != null) {
				stack.getTag().remove("Damage");
				b.save(e -> cond.accept(new NBTRecipe(e, stack)), id);
				return;
			}
		}
		b.save(cond, id);
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
