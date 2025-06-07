package dev.xkmc.l2weaponry.compat.aerial;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.l2weaponry.compat.CompatDispatch;
import dev.xkmc.l2weaponry.compat.twilightforest.TFToolMats;
import dev.xkmc.l2weaponry.init.materials.ILWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWGenItem;
import net.minecraft.world.item.Item;

public class AHCompat extends CompatDispatch {

	public static final ItemEntry<Item>[][] ITEMS = LWGenItem.generate(AHToolMats.values());

	@Override
	public ILWToolMats[] values() {
		return AHToolMats.values();
	}

}
