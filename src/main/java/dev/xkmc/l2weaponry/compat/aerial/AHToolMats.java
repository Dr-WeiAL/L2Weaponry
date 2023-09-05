package dev.xkmc.l2weaponry.compat.aerial;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2damagetracker.contents.materials.api.IMatToolType;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2library.serial.recipe.ConditionalRecipeWrapper;
import dev.xkmc.l2weaponry.compat.twilightforest.TFMats;
import dev.xkmc.l2weaponry.init.materials.ILWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWToolTypes;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import fr.factionbedrock.aerialhell.AerialHell;
import fr.factionbedrock.aerialhell.Item.Material.ToolMaterials;
import fr.factionbedrock.aerialhell.Registry.AerialHellBlocksAndItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;
import java.util.function.Supplier;

public enum AHToolMats implements ILWToolMats {
	ARSONIST(new TFMats(ToolMaterials.arsonist, new ExtraToolConfig()), false, AerialHellBlocksAndItems.ARSONIST_INGOT, AerialHellBlocksAndItems.ARSONIST_BLOCK),
	LUNAR(new TFMats(ToolMaterials.lunatic, new ExtraToolConfig()), false, AerialHellBlocksAndItems.LUNATIC_CRYSTAL, AerialHellBlocksAndItems.LUNATIC_CRYSTAL_BLOCK),
	RUBY(new TFMats(ToolMaterials.ruby, new ExtraToolConfig()), false, AerialHellBlocksAndItems.RUBY, AerialHellBlocksAndItems.RUBY_BLOCK),
	VOLUCITE(new TFMats(ToolMaterials.volucite, new ExtraToolConfig()), true, AerialHellBlocksAndItems.VOLUCITE_VIBRANT, AerialHellBlocksAndItems.VOLUCITE_BLOCK);

	private final IMatToolType type;
	private final boolean fireRes;
	private final Supplier<Item> ingot;
	private final Supplier<Block> block;

	AHToolMats(IMatToolType type, boolean fireRes, Supplier<Item> ingot, Supplier<Block> block) {
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
		return AHCompat.ITEMS[ordinal()][type.ordinal()].get();
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
		return LWItems.HANDLE.get();
	}

	@Override
	public Consumer<FinishedRecipe> getProvider(RegistrateRecipeProvider pvd) {
		return ConditionalRecipeWrapper.mod(pvd, AerialHell.MODID);
	}

	@Override
	public boolean hasTool(LWToolTypes type) {
		if (type == LWToolTypes.PLATE_SHIELD || type == LWToolTypes.ROUND_SHIELD) {
			return this != RUBY && this != LUNAR;
		}
		return true;
	}


}
