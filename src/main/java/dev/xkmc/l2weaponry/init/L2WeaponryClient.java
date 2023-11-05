package dev.xkmc.l2weaponry.init;

import dev.xkmc.l2weaponry.content.client.ClawItemDecorationRenderer;
import dev.xkmc.l2weaponry.content.client.ShieldItemDecorationRenderer;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = L2Weaponry.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class L2WeaponryClient {

	@SubscribeEvent
	public static void onParticleRegistryEvent(RegisterParticleProvidersEvent event) {

	}

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ClampedItemPropertyFunction func = (stack, level, entity, layer) ->
					entity != null && entity.isBlocking() && entity.getUseItem() == stack ? 1.0F : 0.0F;
			for (Item i : LWItems.BLOCK_DECO) {
				ItemProperties.register(i, new ResourceLocation(L2Weaponry.MODID, "blocking"), func);
			}
			func = (stack, level, entity, layer) ->
					entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
			for (Item i : LWItems.THROW_DECO) {
				ItemProperties.register(i, new ResourceLocation(L2Weaponry.MODID, "throwing"), func);
			}
			func = (stack, level, entity, layer) ->
					entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
			for (Item i : LWItems.NUNCHAKU_DECO) {
				ItemProperties.register(i, new ResourceLocation(L2Weaponry.MODID, "spinning"), func);
			}
		});
	}

	@SubscribeEvent
	public static void registerItemDecoration(RegisterItemDecorationsEvent event) {
		var shield = new ShieldItemDecorationRenderer();
		var claw = new ClawItemDecorationRenderer();
		for (Item i : LWItems.BLOCK_DECO) {
			event.register(i, shield);
		}
		for (Item i : LWItems.CLAW_DECO) {
			event.register(i, claw);
		}
	}

	@SubscribeEvent
	public static void onResourceReload(RegisterClientReloadListenersEvent event) {

	}

}
