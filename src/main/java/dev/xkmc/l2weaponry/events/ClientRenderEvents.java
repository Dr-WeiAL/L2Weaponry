package dev.xkmc.l2weaponry.events;

import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.l2weaponry.content.item.types.ClawItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientRenderEvents {

	@SubscribeEvent
	public static void renderHandEvent(RenderHandEvent event) {
		LocalPlayer player = Proxy.getClientPlayer();
		Item main = player.getMainHandItem().getItem();
		Item off = player.getOffhandItem().getItem();
		if (main instanceof ClawItem && main == off) {
			if (event.getHand() == InteractionHand.MAIN_HAND) {
				Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer()
						.renderArmWithItem(player,
								event.getPartialTick(),
								event.getInterpolatedPitch(),
								InteractionHand.OFF_HAND,
								event.getSwingProgress(),
								player.getOffhandItem(),
								event.getEquipProgress(),
								event.getPoseStack(),
								event.getMultiBufferSource(),
								event.getPackedLight()
						);
			} else {
				event.setCanceled(true);
			}
		}
	}

}
