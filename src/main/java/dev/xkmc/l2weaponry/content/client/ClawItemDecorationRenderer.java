package dev.xkmc.l2weaponry.content.client;

import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.l2weaponry.content.item.base.BaseClawItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemDecorator;

public class ClawItemDecorationRenderer implements IItemDecorator {

	@Override
	public boolean render(GuiGraphics g, Font font, ItemStack stack, int x, int y) {
		ItemStack main = Proxy.getClientPlayer().getMainHandItem();
		ItemStack off = Proxy.getClientPlayer().getOffhandItem();
		if (main != stack && off != stack) return false;
		if (main != stack && off.getItem() != main.getItem()) return false;
		if (!(stack.getItem() instanceof BaseClawItem)) return false;
		if (!(main.getItem() instanceof BaseClawItem claw)) return false;
		long last = BaseClawItem.getLastTime(main);
		long time = Proxy.getClientWorld().getGameTime();
		int timeout = LWConfig.COMMON.claw_timeout.get();
		if (time - last > timeout) return false;
		double defenseLost = Mth.clamp(time - last, 0, timeout) * 1d / timeout;
		int w = Mth.ceil(13.0F * (1 - defenseLost));
		CommonDecoUtil.fillRect(g, x + 2, y + 14, w, 1, 0xffffffff);
		CommonDecoUtil.fillRect(g, x + 2 + w, y + 14, 13 - w, 1, 0xff000000);
		int stored_hit = BaseClawItem.getHitCount(main);
		int max_hit = claw.getMaxStack(main, Proxy.getClientPlayer());
		int hit = Math.min(max_hit, stored_hit);
		String s = "" + hit;
		int col = hit < max_hit ? 0xff7fff : 0xff7f7f;
		if (time - last <= LWConfig.COMMON.claw_block_time.get()) {
			col = 0x00ffff;
		}
		g.drawString(font, s, x + 17 - font.width(s), y + 9, col);
		return true;
	}


}
