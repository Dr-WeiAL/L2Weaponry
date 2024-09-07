package dev.xkmc.l2weaponry.compat.twilightforest;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2core.util.MathHelper;
import dev.xkmc.l2damagetracker.contents.materials.api.IMatToolType;
import dev.xkmc.l2weaponry.compat.ModMats;
import dev.xkmc.l2weaponry.init.materials.ILWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWExtraConfig;
import dev.xkmc.l2weaponry.init.materials.LWToolTypes;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import twilightforest.util.TFToolMaterials;

import java.util.List;
import java.util.function.Supplier;

public enum TFToolMats implements ILWToolMats {
	IRONWOOD(new ModMats(TFToolMaterials.IRONWOOD, new IronwoodTool()), false, TFItems.IRONWOOD_INGOT, TFBlocks.IRONWOOD_BLOCK),
	STEELEAF(new ModMats(TFToolMaterials.STEELEAF, new SteeleafTool()), false, TFItems.STEELEAF_INGOT, TFBlocks.STEELEAF_BLOCK),
	KNIGHTMETAL(new ModMats(TFToolMaterials.KNIGHTMETAL, new KnightmetalTool()), false, TFItems.KNIGHTMETAL_INGOT, TFBlocks.KNIGHTMETAL_BLOCK),
	FIERY(new ModMats(TFToolMaterials.FIERY, new FieryTool()), true, TFItems.FIERY_INGOT, TFBlocks.FIERY_BLOCK);

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
	public void addEnchants(List<LWToolTypes.DefaultEnch> list, LWToolTypes type) {
		if (this.type.getExtraToolConfig() instanceof LWExtraConfig lw) {
			lw.addEnchants(list, type, getTool(type));
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
	public RecipeOutput getProvider(RegistrateRecipeProvider pvd, ICondition... cond) {
		return ILWToolMats.super.getProvider(pvd, MathHelper.merge(cond, new ModLoadedCondition(TwilightForestMod.ID)));
	}

	@Override
	public boolean isOptional() {
		return true;
	}


}
