package dev.xkmc.l2weaponry.init;

import dev.xkmc.l2complements.content.item.misc.LCBEWLR;
import dev.xkmc.l2complements.init.registrate.LCParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class L2WeaponryClient {

	public static void onCtorClient(IEventBus bus, IEventBus eventBus) {
		bus.addListener(L2WeaponryClient::clientSetup);
		bus.addListener(L2WeaponryClient::onResourceReload);
		bus.addListener(L2WeaponryClient::onParticleRegistryEvent);
	}


	public static void onParticleRegistryEvent(RegisterParticleProvidersEvent event) {

	}

	public static void clientSetup(FMLClientSetupEvent event) {
		L2WeaponryClient.registerItemProperties();
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerItemProperties() {
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onResourceReload(RegisterClientReloadListenersEvent event) {

	}

}
