package dev.xkmc.l2weaponry.events;

import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.l2weaponry.content.item.base.DoubleHandItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientRenderEvents {

	@SubscribeEvent
	public static void handRenderEvent(RenderHandEvent event) {
		if (event.getHand() == InteractionHand.OFF_HAND) {
			if (event.getItemStack().getItem() instanceof DoubleHandItem item) {
				if (item.disableOffHand(Proxy.getClientPlayer())) {
					//event.setCanceled(true);
				}
			} else if (Proxy.getClientPlayer().getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof DoubleHandItem item) {
				if (item.disableOffHand(Proxy.getClientPlayer())) {
					//event.setCanceled(true);
				}
			}
		}
	}

}
