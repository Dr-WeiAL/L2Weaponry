package dev.xkmc.l2weaponry.compat.undergarden;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.l2weaponry.compat.CompatDispatch;
import dev.xkmc.l2weaponry.init.materials.ILWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWGenItem;
import net.minecraft.world.item.Item;

public class UGCompat extends CompatDispatch {

	public static final ItemEntry<Item>[][] ITEMS = LWGenItem.generate(UGToolMats.values());

	@Override
	public ILWToolMats[] values() {
		return UGToolMats.values();
	}

}
