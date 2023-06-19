package dev.xkmc.l2weaponry.init.data;

import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import dev.xkmc.l2tabs.init.L2Tabs;
import dev.xkmc.l2tabs.init.data.AttributeDisplayConfig;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

public class LWAttributeConfigGen extends ConfigDataProvider {
    public LWAttributeConfigGen(DataGenerator generator) {
        super(generator, "L2Weaponry Attribute Config Gen");
    }

    public void add(Collector collector) {
        collector.add(L2Tabs.ATTRIBUTE_ENTRY, new ResourceLocation(L2Weaponry.MODID, L2Weaponry.MODID), new AttributeDisplayConfig()
                        .add(LWItems.REFLECT_TIME.get(), true, 14000)
                .add(LWItems.SHIELD_DEFENSE.get(), true, 15000));
    }
}
