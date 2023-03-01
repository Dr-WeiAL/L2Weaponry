package dev.xkmc.l2weaponry.init;

import dev.xkmc.l2weaponry.content.client.ShieldItemDecorationRenderer;
import dev.xkmc.l2weaponry.events.ClientRenderEvents;
import dev.xkmc.l2weaponry.init.materials.LWToolMats;
import dev.xkmc.l2weaponry.init.materials.LWToolTypes;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class L2WeaponryClient {

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
			ResourceLocation blocking = new ResourceLocation(L2Weaponry.MODID, "blocking");
			ResourceLocation throwing = new ResourceLocation(L2Weaponry.MODID, "throwing");
			for (LWToolMats mat : LWToolMats.values()) {
				var set = LWItems.GEN_ITEM[mat.ordinal()];
				ItemProperties.register(set[LWToolTypes.ROUND_SHIELD.ordinal()].get(), blocking, func);
				ItemProperties.register(set[LWToolTypes.PLATE_SHIELD.ordinal()].get(), blocking, func);
				ItemProperties.register(set[LWToolTypes.THROWING_AXE.ordinal()].get(), throwing, func);
				ItemProperties.register(set[LWToolTypes.JAVELIN.ordinal()].get(), throwing, func);
			}
			ItemProperties.register(LWItems.ENDER_JAVELIN.get(), throwing, func);
			ItemProperties.register(LWItems.STORM_JAVELIN.get(), throwing, func);
			ItemProperties.register(LWItems.BLACK_AXE.get(), throwing, func);
		});
	}

	@SubscribeEvent
	public static void registerItemDecoration(RegisterItemDecorationsEvent event) {
		for (LWToolMats mat : LWToolMats.values()) {
			event.register(LWItems.GEN_ITEM[mat.ordinal()][LWToolTypes.ROUND_SHIELD.ordinal()].get(),
					ShieldItemDecorationRenderer::renderShieldBar);
			event.register(LWItems.GEN_ITEM[mat.ordinal()][LWToolTypes.PLATE_SHIELD.ordinal()].get(),
					ShieldItemDecorationRenderer::renderShieldBar);
		}
	}

	@SubscribeEvent
	public static void onResourceReload(RegisterClientReloadListenersEvent event) {

	}

}
