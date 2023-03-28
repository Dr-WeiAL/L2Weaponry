package dev.xkmc.l2weaponry.init.registrate;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2library.repack.registrate.util.entry.EntityEntry;
import dev.xkmc.l2weaponry.content.client.ThrownWeaponRenderer;
import dev.xkmc.l2weaponry.content.entity.JavelinEntity;
import dev.xkmc.l2weaponry.content.entity.ThrowingAxeEntity;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

public class LWEntities {

	public static final EntityEntry<ThrowingAxeEntity> ET_AXE;
	public static final EntityEntry<JavelinEntity> TE_JAVELIN;

	static {
		ET_AXE = L2Weaponry.REGISTRATE
				.<ThrowingAxeEntity>entity("throwing_axe", ThrowingAxeEntity::new, MobCategory.MISC)
				.properties(e -> e.sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(10))
				.renderer(() -> ThrownWeaponRenderer::new)
				.defaultLang().register();

		TE_JAVELIN = L2Weaponry.REGISTRATE
				.<JavelinEntity>entity("javelin", JavelinEntity::new, MobCategory.MISC)
				.properties(e -> e.sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(10))
				.renderer(() -> ThrownWeaponRenderer::new)
				.defaultLang().register();
	}

	public static void register() {
	}

	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
	}

}
