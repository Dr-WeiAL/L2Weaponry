package dev.xkmc.l2weaponry.compat.atm;

import com.mojang.datafixers.util.Pair;
import com.thevortex.allthemodium.material.ATMTier;
import com.thevortex.allthemodium.registry.ModRegistry;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2core.util.MathHelper;
import dev.xkmc.l2damagetracker.contents.materials.api.IMatToolType;
import dev.xkmc.l2weaponry.compat.ModMats;
import dev.xkmc.l2weaponry.init.materials.ILWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWExtraConfig;
import dev.xkmc.l2weaponry.init.materials.LWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWToolTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public enum ATMToolMats implements ILWToolMats {
	ALLTHEMODIUM(new ModMats(() -> ATMTier.ALLTHEMODIUM, new ATMTool(1)), true, ModRegistry.ALLTHEMODIUM_INGOT, ModRegistry.ALLTHEMODIUM_BLOCK),
	VIBRANIUM(new ModMats(() -> ATMTier.VIBRANIUM, new ATMTool(2)), true, ModRegistry.VIBRANIUM_INGOT, ModRegistry.VIBRANIUM_BLOCK),
	UNOBTAINIUM(new ModMats(() -> ATMTier.UNOBTAINIUM, new ATMTool(3)), true, ModRegistry.UNOBTAINIUM_INGOT, ModRegistry.UNOBTAINIUM_BLOCK),
	;

	private final IMatToolType type;
	private final boolean fireRes;
	private final Supplier<Item> ingot;
	private final Supplier<Block> block;

	ATMToolMats(IMatToolType type, boolean fireRes, Supplier<Item> ingot, Supplier<Block> block) {
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
		return ATMCompat.ITEMS[ordinal()][type.ordinal()].get();
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
		return Items.STICK;
	}

	@Override
	public boolean hasTool(LWToolTypes type) {
		if (type == LWToolTypes.ROUND_SHIELD || type == LWToolTypes.PLATE_SHIELD)
			return false;
		return true;
	}

	@Override
	public void addEnchants(HolderLookup.Provider pvd, List<LWToolTypes.DefaultEnch> list, LWToolTypes type) {
		if (this.type.getExtraToolConfig() instanceof LWExtraConfig lw) {
			lw.addEnchants(list, type, getTool(type));
		}
		List<LWToolTypes.DefaultEnch> copy = new ArrayList<>(list);
		list.clear();
		for (var e : copy) {
			int max = pvd.holderOrThrow(e.key()).value().getMaxLevel();
			list.add(new LWToolTypes.DefaultEnch(e.key(), Math.min(e.lv() + ordinal() + 1, max)));
		}
	}

	@Override
	public String englishName() {
		return name();
	}

	@Override
	public RecipeOutput getProvider(RegistrateRecipeProvider pvd, ICondition... cond) {
		return ILWToolMats.super.getProvider(pvd, MathHelper.merge(cond, new ModLoadedCondition("allthemodium")));
	}

	@Override
	public boolean isOptional() {
		return true;
	}

	@Override
	public Pair<ILWToolMats, Item> getBaseUpgrade() {
		return switch (this) {
			case ALLTHEMODIUM -> Pair.of(LWToolMats.NETHERITE, ModRegistry.ATM_SMITHING.get());
			case VIBRANIUM -> Pair.of(ALLTHEMODIUM, ModRegistry.VIB_SMITHING.get());
			case UNOBTAINIUM -> Pair.of(UNOBTAINIUM, ModRegistry.UNO_SMITHING.get());
		};
	}

}
