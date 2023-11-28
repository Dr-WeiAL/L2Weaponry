package dev.xkmc.l2weaponry.compat.dragons;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.item.DragonSteelTier;
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
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public enum DragonToolMats implements ILWToolMats {
	DRAGON_BONE(new ModMats(IafItemRegistry.DRAGONBONE_TOOL_MATERIAL, new ExtraToolConfig()), true, IafItemRegistry.DRAGON_BONE),
	ICE_DRAGON_BONE(new ModMats(DragonSteelTier.DRAGONSTEEL_TIER_ICE, new IceDragonBoneTool()), true, IafItemRegistry.ICE_DRAGON_BLOOD),
	FIRE_DRAGON_BONE(new ModMats(DragonSteelTier.DRAGONSTEEL_TIER_FIRE, new FireDragonBoneTool()), true, IafItemRegistry.FIRE_DRAGON_BLOOD),
	LIGHTNING_DRAGON_BONE(new ModMats(DragonSteelTier.DRAGONSTEEL_TIER_LIGHTNING, new LightningDragonBoneTool()), true, IafItemRegistry.LIGHTNING_DRAGON_BLOOD),
	;

	private final IMatToolType type;
	private final boolean fireRes;
	private final Supplier<Item> ingot;

	DragonToolMats(IMatToolType type, boolean fireRes, Supplier<Item> ingot) {
		this.type = type;
		this.fireRes = fireRes;
		this.ingot = ingot;
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
		return Items.AIR;
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
