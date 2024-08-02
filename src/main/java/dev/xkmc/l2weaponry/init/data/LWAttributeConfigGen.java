package dev.xkmc.l2weaponry.init.data;

import com.tterrag.registrate.providers.RegistrateDataMapProvider;
import dev.xkmc.l2tabs.init.L2Tabs;
import dev.xkmc.l2tabs.init.data.AttrDispEntry;
import dev.xkmc.l2weaponry.init.registrate.LWItems;

public class LWAttributeConfigGen {

	public static void onDataMapGen(RegistrateDataMapProvider pvd) {
		pvd.builder(L2Tabs.ATTRIBUTE_ENTRY.reg())
				.add(LWItems.REFLECT_TIME.key(), new AttrDispEntry(false, 14000, 0), false)
				.add(LWItems.SHIELD_DEFENSE.key(), new AttrDispEntry(false, 15000, 0), false);
	}

}
