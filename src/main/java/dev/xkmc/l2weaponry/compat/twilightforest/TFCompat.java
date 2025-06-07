package dev.xkmc.l2weaponry.compat.twilightforest;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.l2weaponry.compat.CompatDispatch;
import dev.xkmc.l2weaponry.init.materials.ILWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWGenItem;
import net.minecraft.world.item.Item;

public class TFCompat extends CompatDispatch {

	public static final ItemEntry<Item>[][] ITEMS = LWGenItem.generate(TFToolMats.values());

	@Override
	public ILWToolMats[] values() {
		return TFToolMats.values();
	}

}
