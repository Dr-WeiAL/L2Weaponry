package dev.xkmc.l2weaponry.compat.dragons;

import com.google.common.collect.Lists;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2damagetracker.contents.materials.api.IMatToolType;
import dev.xkmc.l2library.serial.recipe.ConditionalRecipeWrapper;
import dev.xkmc.l2weaponry.compat.ModMats;
import dev.xkmc.l2weaponry.init.materials.ILWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWToolTypes;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public enum DragonToolMats implements ILWToolMats {
	ICE_DRAGONSTEEL(new ModMats(() -> IAFProxy.get().tierIce(), new IceDragonBoneTool()), true, IAFProxy.get().ingotIceSteel(), IAFProxy.get().blockIceSteel()),
	FIRE_DRAGONSTEEL(new ModMats(() -> IAFProxy.get().tierFire(), new FireDragonBoneTool()), true, IAFProxy.get().ingotFireSteel(), IAFProxy.get().blockFireSteel()),
	LIGHTNING_DRAGONSTEEL(new ModMats(() -> IAFProxy.get().tierLightning(), new LightningDragonBoneTool()), true, IAFProxy.get().ingotLightningSteel(), IAFProxy.get().blockLightningSteel()),
	;

	private final IMatToolType type;
	private final boolean fireRes;
	private final Supplier<Item> ingot;
	private final Supplier<Block> block;

	DragonToolMats(IMatToolType type, boolean fireRes, Supplier<Item> ingot, Supplier<Block> block) {
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
		return DragonCompat.ITEMS[ordinal()][type.ordinal()].get();
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
		return IAFProxy.get().witherBone();
	}

	@Override
	public Consumer<FinishedRecipe> getProvider(RegistrateRecipeProvider pvd, ICondition... cond) {
		List<ICondition> list = Lists.asList(new ModLoadedCondition(IAFProxy.get().modid()), cond);
		return ConditionalRecipeWrapper.of(pvd, list.toArray(ICondition[]::new));
	}

	@Override
	public boolean isOptional() {
		return true;
	}

}
