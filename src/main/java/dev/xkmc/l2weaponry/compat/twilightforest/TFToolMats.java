package dev.xkmc.l2weaponry.compat.twilightforest;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2damagetracker.contents.materials.api.IMatToolType;
import dev.xkmc.l2library.serial.recipe.ConditionalRecipeWrapper;
import dev.xkmc.l2library.serial.recipe.NBTRecipe;
import dev.xkmc.l2weaponry.compat.ModMats;
import dev.xkmc.l2weaponry.init.materials.ILWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWExtraConfig;
import dev.xkmc.l2weaponry.init.materials.LWToolTypes;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import twilightforest.util.TwilightItemTier;

import java.util.function.Consumer;
import java.util.function.Supplier;

public enum TFToolMats implements ILWToolMats {
	IRONWOOD(new ModMats(TwilightItemTier.IRONWOOD, new IronwoodTool()), false, TFItems.IRONWOOD_INGOT, TFBlocks.IRONWOOD_BLOCK),
	STEELEAF(new ModMats(TwilightItemTier.STEELEAF, new SteeleafTool()), false, TFItems.STEELEAF_INGOT, TFBlocks.STEELEAF_BLOCK),
	KNIGHTMETAL(new ModMats(TwilightItemTier.KNIGHTMETAL, new KnightmetalTool()), false, TFItems.KNIGHTMETAL_INGOT, TFBlocks.KNIGHTMETAL_BLOCK),
	FIERY(new ModMats(TwilightItemTier.FIERY, new FieryTool()), true, TFItems.FIERY_INGOT, TFBlocks.FIERY_BLOCK);

	private final IMatToolType type;
	private final boolean fireRes;
	private final Supplier<Item> ingot;
	private final Supplier<Block> block;

	TFToolMats(IMatToolType type, boolean fireRes, Supplier<Item> ingot, Supplier<Block> block) {
		this.type = type;
		this.fireRes = fireRes;
		this.ingot = ingot;
		this.block = block;
	}

	@Override
	public IMatToolType type() {
		return type;
	}

	@Override
	public boolean fireRes() {
		return fireRes;
	}

	@Override
	public Item getTool(LWToolTypes type) {
		return TFCompat.ITEMS[ordinal()][type.ordinal()].get();
	}

	@Override
	public Item getIngot() {
		return ingot.get();
	}

	@Override
	public Item getBlock() {
		return block.get().asItem();
	}

	@Override
	public Item getStick() {
		return this == FIERY ? Items.BLAZE_ROD : LWItems.HANDLE.get();
	}

	@Override
	public void saveRecipe(ShapedRecipeBuilder b, RegistrateRecipeProvider pvd, LWToolTypes type, ResourceLocation id) {
		var cond = getProvider(pvd);
		if (this.type.getExtraToolConfig() instanceof LWExtraConfig lw) {
			ItemStack stack = lw.getDefaultStack(getTool(type));
			if (stack.hasTag()) {
				b.save(e -> cond.accept(new NBTRecipe(e, stack)), id);
			}
		} else {
			b.save(cond, id);
		}
	}


	@Override
	public String englishName() {
		return this == KNIGHTMETAL ? "knightly" : name();
	}

	@Override
	public boolean emissive() {
		return this == FIERY;
	}

	@Override
	public Consumer<FinishedRecipe> getProvider(RegistrateRecipeProvider pvd) {
		return ConditionalRecipeWrapper.mod(pvd, TwilightForestMod.ID);
	}

	@Override
	public boolean isOptional() {
		return true;
	}


}
