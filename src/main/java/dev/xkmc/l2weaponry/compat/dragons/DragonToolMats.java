package dev.xkmc.l2weaponry.compat.dragons;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2damagetracker.contents.materials.api.IMatToolType;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2library.serial.recipe.ConditionalRecipeWrapper;
import dev.xkmc.l2weaponry.compat.ModMats;
import dev.xkmc.l2weaponry.init.materials.ILWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWToolTypes;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public enum DragonToolMats implements ILWToolMats {
	DRAGON_BONE(new ModMats(IafItemRegistry.DRAGONBONE_TOOL_MATERIAL, new ExtraToolConfig()), true, IafItemRegistry.DRAGON_BONE, IafBlockRegistry.DRAGON_BONE_BLOCK),
	ICE_DRAGON_BONE(new ModMats(IafItemRegistry.ICE_DRAGONBONE_TOOL_MATERIAL, new IceDragonBoneTool()), true, IafItemRegistry.ICE_DRAGON_BLOOD, () -> Blocks.AIR),
	FIRE_DRAGON_BONE(new ModMats(IafItemRegistry.FIRE_DRAGONBONE_TOOL_MATERIAL, new FireDragonBoneTool()), true, IafItemRegistry.FIRE_DRAGON_BLOOD, () -> Blocks.AIR),
	LIGHTNING_DRAGON_BONE(new ModMats(IafItemRegistry.LIGHTNING_DRAGONBONE_TOOL_MATERIAL, new LightningDragonBoneTool()), true, IafItemRegistry.LIGHTNING_DRAGON_BLOOD, () -> Blocks.AIR),
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
		return IafItemRegistry.WITHERBONE.get();
	}

	@Override
	public Consumer<FinishedRecipe> getProvider(RegistrateRecipeProvider pvd) {
		return ConditionalRecipeWrapper.mod(pvd, IceAndFire.MODID);
	}

	@Override
	public boolean hasTool(LWToolTypes type) {
		return type == LWToolTypes.NUNCHAKU || type == LWToolTypes.BATTLE_AXE ||
				type == LWToolTypes.HAMMER || type == LWToolTypes.SPEAR ||
				type == LWToolTypes.JAVELIN || type == LWToolTypes.THROWING_AXE ||
				type == LWToolTypes.DAGGER;
	}

	@Override
	public boolean isOptional() {
		return true;
	}

	@Override
	public @Nullable ILWToolMats getBaseUpgrade() {
		return this == DRAGON_BONE ? null : DRAGON_BONE;
	}


}
