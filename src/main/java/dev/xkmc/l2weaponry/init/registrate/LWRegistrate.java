package dev.xkmc.l2weaponry.init.registrate;

import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2serial.util.Wrappers;
import net.minecraft.world.item.Item;

public class LWRegistrate extends L2Registrate {

	public LWRegistrate(String modid) {
		super(modid);
	}

	public <T extends Item> LWItemBuilder<T, L2Registrate> item(String name, NonNullFunction<Item.Properties, T> factory) {
		return item(self(), name, factory);
	}

	@Override
	public <T extends Item, P> LWItemBuilder<T, P> item(P parent, String name, NonNullFunction<Item.Properties, T> factory) {
		return Wrappers.cast(entry(name, callback -> new LWItemBuilder<>(this, parent, name, callback, factory).transform(builder ->
				builder.tab(LWItems.TAB.getKey()))));
	}

}
