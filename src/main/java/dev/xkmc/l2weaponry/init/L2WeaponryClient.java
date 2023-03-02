package dev.xkmc.l2weaponry.init;

import dev.xkmc.l2weaponry.content.client.ClawItemDecorationRenderer;
import dev.xkmc.l2weaponry.content.client.ShieldItemDecorationRenderer;
import dev.xkmc.l2weaponry.events.ClientRenderEvents;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;
import java.util.List;

public class L2WeaponryClient {

	public static final List<Item> BLOCK_DECO = new ArrayList<>();
	public static final List<Item> THROW_DECO = new ArrayList<>();
	public static final List<Item> CLAW_DECO = new ArrayList<>();

	public static void onCtorClient(IEventBus bus, IEventBus eventBus) {
		bus.register(L2WeaponryClient.class);
		eventBus.register(ClientRenderEvents.class);
	}

	@SubscribeEvent
	public static void onParticleRegistryEvent(RegisterParticleProvidersEvent event) {

	}

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ClampedItemPropertyFunction func = (stack, level, entity, layer) ->
					entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
			for (Item i : BLOCK_DECO) {
				ItemProperties.register(i, new ResourceLocation(L2Weaponry.MODID, "blocking"), func);
			}
			for (Item i : THROW_DECO) {
				ItemProperties.register(i, new ResourceLocation(L2Weaponry.MODID, "throwing"), func);
			}
		});
	}

	@SubscribeEvent
	public static void registerItemDecoration(RegisterItemDecorationsEvent event) {
		for (Item i : BLOCK_DECO) {
			event.register(i, ShieldItemDecorationRenderer::renderShieldBar);
		}
		for (Item i : CLAW_DECO) {
			event.register(i, ClawItemDecorationRenderer::renderHitCount);
		}
	}

	@SubscribeEvent
	public static void onResourceReload(RegisterClientReloadListenersEvent event) {

	}

}
