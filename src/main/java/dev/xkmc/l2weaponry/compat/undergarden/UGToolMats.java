package dev.xkmc.l2weaponry.compat.undergarden;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2core.util.MathHelper;
import dev.xkmc.l2damagetracker.contents.materials.api.IMatToolType;
import dev.xkmc.l2weaponry.compat.ModMats;
import dev.xkmc.l2weaponry.init.materials.ILWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWExtraConfig;
import dev.xkmc.l2weaponry.init.materials.LWToolTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import quek.undergarden.Undergarden;
import quek.undergarden.registry.UGBlocks;
import quek.undergarden.registry.UGItemTiers;
import quek.undergarden.registry.UGItems;

import java.util.List;
import java.util.function.Supplier;

public enum UGToolMats implements ILWToolMats {
	CLOGGRUM(new ModMats(UGItemTiers.CLOGGRUM, new CloggrumTool()), false, UGItems.CLOGGRUM_INGOT, UGBlocks.CLOGGRUM_BLOCK),
	FROSTSTEEL(new ModMats(UGItemTiers.FROSTSTEEL, new FroststeelTool()), false, UGItems.FROSTSTEEL_INGOT, UGBlocks.FROSTSTEEL_BLOCK),
	UTHERIUM(new ModMats(UGItemTiers.UTHERIUM, new UteriumTool()), false, UGItems.UTHERIUM_CRYSTAL, UGBlocks.UTHERIUM_BLOCK),
	FORGOTTEN(new ModMats(UGItemTiers.FORGOTTEN, new ForgottenTool()), false, UGItems.FORGOTTEN_INGOT, UGBlocks.FORGOTTEN_BLOCK),
	;

	private final IMatToolType type;
	private final boolean fireRes;
	private final Supplier<Item> ingot;
	private final Supplier<Block> block;

	UGToolMats(IMatToolType type, boolean fireRes, Supplier<Item> ingot, Supplier<Block> block) {
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
		return UGCompat.ITEMS[ordinal()][type.ordinal()].get();
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
		return UGItems.UNDERBEAN_STICK.get();
	}

	@Override
	public boolean hasTool(LWToolTypes type) {
		return true;
	}

	@Override
	public void addEnchants(HolderLookup.Provider pvd, List<LWToolTypes.DefaultEnch> list, LWToolTypes type) {
		if (this.type.getExtraToolConfig() instanceof LWExtraConfig lw) {
			lw.addEnchants(list, type, getTool(type));
		}
	}

	@Override
	public String englishName() {
		return name();
	}

	@Override
	public RecipeOutput getProvider(RegistrateRecipeProvider pvd, ICondition... cond) {
		return ILWToolMats.super.getProvider(pvd, MathHelper.merge(cond, new ModLoadedCondition(Undergarden.MODID)));
	}

	@Override
	public boolean isOptional() {
		return true;
	}

}
