package dev.xkmc.l2weaponry.content.client;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.l2weaponry.content.capability.LWPlayerData;
import dev.xkmc.l2weaponry.content.item.base.BaseShieldItem;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemDecorator;

public class ShieldItemDecorationRenderer implements IItemDecorator {

	private static final int COLOR_REFLECT = 0xff00ffff;
	private static final int COLOR_USING = 0xffff7f7f;

	@Override
	public boolean render(GuiGraphics g, Font font, ItemStack stack, int x, int y) {
		ItemStack main = Proxy.getClientPlayer().getMainHandItem();
		if (main != stack) {
			if (Proxy.getClientPlayer().getOffhandItem() != stack) return false;
			if (main.getItem() instanceof BaseShieldItem) return false;
		}
		if (!(stack.getItem() instanceof BaseShieldItem)) return false;
		var cap = LWPlayerData.HOLDER.get(Proxy.getClientPlayer());
		float defenseLost = (float) cap.getShieldDefense();
		float w = 13.0f * (1 - defenseLost);
		boolean using = cap.getRecoverRate() < 0.01;
		int color = cap.canReflect() && defenseLost == 0 ? COLOR_REFLECT : using ? COLOR_USING : 0xffffffff;
		CommonDecoUtil.fillRect(g, x + 2, y + 14, w, 1, color);
		CommonDecoUtil.fillRect(g, x + 2 + w, y + 14, 13 - w, 1, 0xff000000);
		int reflectTimer = cap.getReflectTimer();
		if (cap.canReflect() && reflectTimer > 0) {
			int max = (int) cap.player.getAttributeValue(LWItems.REFLECT_TIME.get());
			w = 13.0f * reflectTimer / max;
			CommonDecoUtil.fillRect(g, x + 2, y + 14, w, 1, COLOR_REFLECT);
		}
		return true;
	}
}
