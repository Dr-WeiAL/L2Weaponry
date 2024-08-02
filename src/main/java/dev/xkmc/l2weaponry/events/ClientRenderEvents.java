package dev.xkmc.l2weaponry.events;

import dev.xkmc.l2core.util.Proxy;
import dev.xkmc.l2weaponry.content.item.base.DoubleWieldItem;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderHandEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = L2Weaponry.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ClientRenderEvents {

	@SubscribeEvent
	public static void renderHandEvent(RenderHandEvent event) {
		LocalPlayer player = Proxy.getClientPlayer();
		Item main = player.getMainHandItem().getItem();
		Item off = player.getOffhandItem().getItem();
		if (main instanceof DoubleWieldItem && main == off) {
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

	public static void onNunchakuUse(Player player, ItemStack stack) {
		if (player != Proxy.getClientPlayer()) return;
		var mode = Minecraft.getInstance().gameMode;
		if (mode == null) return;
		var cd = player.getAttackStrengthScale(1);
		if (cd < 1) return;
		var hit = Minecraft.getInstance().hitResult;
		if (hit instanceof EntityHitResult entityResult) {
			var entity = entityResult.getEntity();
			if (!entity.isAlive()) return;
			if (!(entity instanceof ItemEntity) && !(entity instanceof ExperienceOrb) && !(entity instanceof AbstractArrow)) {
				mode.attack(player, entity);
			}
		}
	}

}
