package dev.xkmc.l2weaponry.content.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;

public class CommonDecoUtil {

	/**
	 * Draw with the WorldRenderer
	 */
	public static void fillRect(PoseStack pose, BufferBuilder buffer, float x, float y, float w, float h, float z, int color, int a) {
		int r = (color >> 16) & 0xff;
		int g = (color >> 8) & 0xff;
		int b = color & 0xff;
		RenderSystem.disableDepthTest();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		buffer.vertex(pose.last().pose(), x, y, z).color(r, g, b, a).endVertex();
		buffer.vertex(pose.last().pose(), x, y + h, z).color(r, g, b, a).endVertex();
		buffer.vertex(pose.last().pose(), x + w, y + h, z).color(r, g, b, a).endVertex();
		buffer.vertex(pose.last().pose(), x + w, y, z).color(r, g, b, a).endVertex();
		BufferUploader.drawWithShader(buffer.end());
	}

	public static void drawText(PoseStack poseStack, int x, int y, Font font, int col, String s) {
		MultiBufferSource.BufferSource buffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
		font.drawInBatch(s, x, y, col, true, poseStack.last().pose(), buffer,
				Font.DisplayMode.NORMAL, 0, 0xF000F0);
		buffer.endBatch();
	}


}
