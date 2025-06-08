package dev.xkmc.l2weaponry.compat.cataclysm;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.items.Tooltier;
import com.google.common.collect.Lists;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2damagetracker.contents.materials.api.IMatToolType;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2library.serial.recipe.ConditionalRecipeWrapper;
import dev.xkmc.l2weaponry.compat.ModMats;
import dev.xkmc.l2weaponry.init.materials.ILWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWExtraConfig;
import dev.xkmc.l2weaponry.init.materials.LWToolTypes;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public enum CataToolMats implements ILWToolMats {
	IGNITIUM(new ModMats(CataTiers.IGNITIUM, new IgnitiumTool()), ModItems.IGNITIUM_INGOT, ModBlocks.IGNITIUM_BLOCK),
	WITHERITE(new ModMats(CataTiers.WITHERITE, new WitheriteTool()), ModItems.WITHERITE_INGOT, ModBlocks.WITHERITE_BLOCK),
	CURSIUM(new ModMats(CataTiers.CURSIUM, new CursiumTool()), ModItems.CURSIUM_INGOT, ModBlocks.CURSIUM_BLOCK),
	ANCIENT_METAL(new ModMats(CataTiers.ANCIENT_METAL, new AncientMetalTool()), ModItems.ANCIENT_METAL_INGOT, ModBlocks.ANCIENT_METAL_BLOCK),
	BLACK_STEEL(new ModMats(Tooltier.BLACK_STEEL, new ExtraToolConfig()), ModItems.BLACK_STEEL_INGOT, ModBlocks.BLACK_STEEL_BLOCK),
	;

	private final IMatToolType type;
	private final Supplier<Item> ingot;
	private final Supplier<Block> block;

	CataToolMats(IMatToolType type, Supplier<Item> ingot, Supplier<Block> block) {
		this.type = type;
		this.ingot = ingot;
		this.block = block;
	}

	@Override
	public IMatToolType type() {
		return type;
	}

	@Override
	public boolean fireRes() {
		return true;
	}

	@Override
	public Item getTool(LWToolTypes type) {
		return CataCompat.ITEMS[ordinal()][type.ordinal()].get();
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
		return switch (this) {
			case IGNITIUM -> Items.BLAZE_ROD;
			case WITHERITE -> Items.NETHERITE_INGOT;
			case CURSIUM -> ModItems.BLACK_STEEL_INGOT.get();
			default -> LWItems.HANDLE.get();
		};
	}

	@Override
	public void addEnchants(List<EnchantmentInstance> list, LWToolTypes type) {
		if (this.type.getExtraToolConfig() instanceof LWExtraConfig lw) {
			lw.addEnchants(list, type, getTool(type));
		}
	}

	@Override
	public Consumer<FinishedRecipe> getProvider(RegistrateRecipeProvider pvd, ICondition... cond) {
		List<ICondition> list = Lists.asList(new ModLoadedCondition(Cataclysm.MODID), cond);
		return ConditionalRecipeWrapper.of(pvd, list.toArray(ICondition[]::new));
	}

	@Override
	public boolean hasTool(LWToolTypes type) {
		return switch (type) {
			case CLAW, DAGGER, NUNCHAKU, SPEAR, JAVELIN, THROWING_AXE, SCYTHE, BATTLE_AXE, MACHETE -> true;
			default -> false;
		};
	}

	@Override
	public boolean is3D(LWToolTypes type) {
		return type == LWToolTypes.JAVELIN;
	}

	@Override
	public boolean isOptional() {
		return true;
	}

}
