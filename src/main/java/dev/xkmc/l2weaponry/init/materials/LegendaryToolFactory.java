package dev.xkmc.l2weaponry.init.materials;

import dev.xkmc.l2library.init.materials.generic.ExtraToolConfig;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

public interface LegendaryToolFactory<T extends Item> {

	T get(Tier tier, int damage, float speed, Item.Properties prop, ExtraToolConfig config);

}
