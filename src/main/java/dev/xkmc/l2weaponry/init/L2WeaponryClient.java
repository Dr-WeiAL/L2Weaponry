package dev.xkmc.l2weaponry.init;

import dev.xkmc.l2weaponry.content.client.ClawItemDecorationRenderer;
import dev.xkmc.l2weaponry.content.client.ShieldItemDecorationRenderer;
import dev.xkmc.l2weaponry.content.item.types.NunchakuItem;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = L2Weaponry.MODID, bus = EventBusSubscriber.Bus.MOD)
public class L2WeaponryClient {

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ClampedItemPropertyFunction func = (stack, level, entity, layer) ->
					entity != null && entity.isBlocking() && entity.getUseItem() == stack ? 1.0F : 0.0F;
			for (Item i : LWItems.BLOCK_DECO) {
				ItemProperties.register(i, L2Weaponry.loc("blocking"), func);
			}
			func = (stack, level, entity, layer) ->
					entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
			for (Item i : LWItems.THROW_DECO) {
				ItemProperties.register(i, L2Weaponry.loc("throwing"), func);
			}
			func = (stack, level, entity, layer) -> NunchakuItem.check(entity, stack) ? 1.0F : 0.0F;
			for (Item i : LWItems.NUNCHAKU_DECO) {
				ItemProperties.register(i, L2Weaponry.loc("spinning"), func);
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
	public static void onModelLoad(ModelEvent.RegisterAdditional event) {
		for (var item : LWItems.NUNCHAKU_DECO) {
			event.register(ModelResourceLocation.standalone(BuiltInRegistries.ITEM.getKey(item).withPath(e -> "item/" + e + "_roll")));
			event.register(ModelResourceLocation.standalone(BuiltInRegistries.ITEM.getKey(item).withPath(e -> "item/" + e + "_unroll")));
		}
	}

}
