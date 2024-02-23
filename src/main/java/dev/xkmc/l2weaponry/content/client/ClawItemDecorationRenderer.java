package dev.xkmc.l2weaponry.content.client;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.l2weaponry.content.item.base.BaseClawItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemDecorator;

public class ClawItemDecorationRenderer implements IItemDecorator {

	@Override
	public boolean render(GuiGraphics g, Font font, ItemStack stack, int x, int y) {
		LocalPlayer player = Proxy.getClientPlayer();
		if (player == null) return false;
		ItemStack main = player.getMainHandItem();
		ItemStack off = player.getOffhandItem();
		if (main != stack && off != stack) return false;
		if (main != stack && off.getItem() != main.getItem()) return false;
		if (!(stack.getItem() instanceof BaseClawItem)) return false;
		if (!(main.getItem() instanceof BaseClawItem claw)) return false;
		long last = BaseClawItem.getLastTime(main);
		int timeout = LWConfig.COMMON.claw_timeout.get();
		float time = (player.level().getGameTime() - last) + Minecraft.getInstance().getPartialTick();
		if (time > timeout) return false;
		g.pose().pushPose();
		int height = LCConfig.CLIENT.enchOverlayZVal.get();
		g.pose().translate(0, 0, height);
		float defenseLost = Mth.clamp(time, 0, timeout) / timeout;
		float w = 13.0F * (1 - defenseLost);
		int col = 0xffffffff;
		if (time <= claw.getBlockTime(player)) {
			col = 0xff00ffff;
		}
		CommonDecoUtil.fillRect(g, x + 2, y + 14, w, 1, col);
		CommonDecoUtil.fillRect(g, x + 2 + w, y + 14, 13 - w, 1, 0xff000000);
		int stored_hit = BaseClawItem.getHitCount(main);
		int max_hit = claw.getMaxStack(main, Proxy.getClientPlayer());
		int hit = Math.min(max_hit, stored_hit);
		String s = "" + hit;
		col = hit < max_hit ? 0xff7fff : 0xff7f7f;
		g.drawString(font, s, x + 17 - font.width(s), y + 9, col);
		g.pose().popPose();
		return true;
	}


}
