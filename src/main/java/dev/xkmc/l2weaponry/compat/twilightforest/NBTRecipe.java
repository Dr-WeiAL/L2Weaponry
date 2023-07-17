
package dev.xkmc.l2weaponry.compat.twilightforest;

import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;

public record NBTRecipe(FinishedRecipe recipe, ItemStack stack) implements FinishedRecipe {

	@Override
	public void serializeRecipeData(JsonObject json) {
		recipe.serializeRecipeData(json);
		json.getAsJsonObject("result").addProperty("nbt", stack.getOrCreateTag().toString());
	}

	@Override
	public ResourceLocation getId() {
		return recipe.getId();
	}

	@Override
	public RecipeSerializer<?> getType() {
		return recipe.getType();
	}

	@Nullable
	@Override
	public JsonObject serializeAdvancement() {
		return recipe.serializeAdvancement();
	}

	@Nullable
	@Override
	public ResourceLocation getAdvancementId() {
		return recipe.getAdvancementId();
	}

}