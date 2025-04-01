package dev.xkmc.l2weaponry.compat.atm;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.l2weaponry.compat.undergarden.UGToolMats;
import dev.xkmc.l2weaponry.init.materials.LWGenItem;
import net.minecraft.world.item.Item;

public class ATMCompat {

	public static final ItemEntry<Item>[][] ITEMS = LWGenItem.generate(ATMToolMats.values());

	public static void register() {
	}

}
