package dev.xkmc.l2weaponry.content.client;

import dev.xkmc.l2core.init.L2CoreConfig;
import dev.xkmc.l2core.util.Proxy;
import dev.xkmc.l2weaponry.content.capability.LWPlayerData;
import dev.xkmc.l2weaponry.content.item.base.BaseShieldItem;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.IItemDecorator;

public class ShieldItemDecorationRenderer implements IItemDecorator {

	private static final int COLOR_REFLECT = 0xff00ffff;
	private static final int COLOR_USING = 0xffff7f7f;

	@Override
	public boolean render(GuiGraphics g, Font font, ItemStack stack, int x, int y) {
		var player = Proxy.getClientPlayer();
		if (player == null) return false;
		ItemStack main = player.getMainHandItem();
		if (main != stack) {
			if (player.getOffhandItem() != stack) return false;
			if (main.getItem() instanceof BaseShieldItem) return false;
		}
		if (!(stack.getItem() instanceof BaseShieldItem)) return false;
		g.pose().pushPose();
		int height = L2CoreConfig.CLIENT.overlayZVal.get();
		g.pose().translate(0, 0, height);
		var cap = LWPlayerData.get(player);
		var data = LWPlayerData.asData(player);
		float defenseLost = (float) data.getShieldDefense();
		float w = 13.0f * (1 - defenseLost);
		boolean using = cap.getRecoverRate(player) < 0.01;
		int color = data.canReflect() && defenseLost == 0 ? COLOR_REFLECT : using ? COLOR_USING : 0xffffffff;
		CommonDecoUtil.fillRect(g, x + 2, y + 14, w, 1, color);
		CommonDecoUtil.fillRect(g, x + 2 + w, y + 14, 13 - w, 1, 0xff000000);
		int reflectTimer = data.getReflectTimer();
		if (data.canReflect() && reflectTimer > 0) {
			int max = (int) player.getAttributeValue(LWItems.REFLECT_TIME.holder());
			w = 13.0f * reflectTimer / max;
			CommonDecoUtil.fillRect(g, x + 2, y + 14, w, 1, COLOR_REFLECT);
		}
		g.pose().popPose();
		return true;
	}
}
