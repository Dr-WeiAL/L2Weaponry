package dev.xkmc.l2weaponry.init.materials;

import dev.xkmc.l2complements.init.materials.LCMats;
import dev.xkmc.l2complements.init.materials.api.IMatToolType;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;

import java.util.function.Supplier;

public enum LWToolMats {
	IRON(new VanillaMats(Tiers.IRON), Items.IRON_INGOT, Items.IRON_BLOCK),
	GOLD(new VanillaMats(Tiers.GOLD), Items.GOLD_INGOT, Items.GOLD_BLOCK),
	DIAMOND(new VanillaMats(Tiers.DIAMOND), Items.DIAMOND, Items.DIAMOND_BLOCK),
	NETHERITE(new VanillaMats(Tiers.NETHERITE), Items.NETHERITE_INGOT, Items.NETHERITE_BLOCK),
	TOTEMIC_GOLD(LCMats.TOTEMIC_GOLD),
	POSEIDITE(LCMats.POSEIDITE),
	SHULKERATE(LCMats.SHULKERATE),
	SCULKIUM(LCMats.SCULKIUM),
	ETERNIUM(LCMats.ETERNIUM);

	public final IMatToolType type;
	private final Supplier<Item> ingot, block;

	LWToolMats(IMatToolType type, Item ingot, Item block) {
		this.type = type;
		this.ingot = () -> ingot;
		this.block = () -> block;
	}

	LWToolMats(LCMats type) {
		this.type = type;
		ingot = type::getIngot;
		block = () -> type.getBlock().asItem();
	}

	public Item getIngot() {
		return ingot.get();
	}

	public Item getBlock() {
		return block.get();
	}

	public Item getTool(LWToolTypes type) {
		return LWItems.GEN_ITEM[ordinal()][type.ordinal()].get();
	}

}
