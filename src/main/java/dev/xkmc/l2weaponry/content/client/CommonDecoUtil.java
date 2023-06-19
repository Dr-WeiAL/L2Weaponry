package dev.xkmc.l2weaponry.content.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.FastColor;
import org.joml.Matrix4f;

public class CommonDecoUtil {

	/**
	 * Draw with the WorldRenderer
	 */
	public static void fillRect(GuiGraphics gui, BufferBuilder buffer, float x, float y, float w, float h, float z, int color, int a) {
		int r = (color >> 16) & 0xff;
		int g = (color >> 8) & 0xff;
		int b = color & 0xff;
		gui.fill(0,0,0,0,0);
		RenderSystem.disableDepthTest();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		buffer.vertex(pose.last().pose(), x, y, z).color(r, g, b, a).endVertex();
		buffer.vertex(pose.last().pose(), x, y + h, z).color(r, g, b, a).endVertex();
		buffer.vertex(pose.last().pose(), x + w, y + h, z).color(r, g, b, a).endVertex();
		buffer.vertex(pose.last().pose(), x + w, y, z).color(r, g, b, a).endVertex();
		BufferUploader.drawWithShader(buffer.end());
	}

	public void fill(GuiGraphics g, RenderType type, int p_286234_, int p_286444_, int p_286244_, int p_286411_, int p_286671_, int p_286599_) {
		Matrix4f matrix4f = g.pose().last().pose();
		if (p_286234_ < p_286244_) {
			int i = p_286234_;
			p_286234_ = p_286244_;
			p_286244_ = i;
		}

		if (p_286444_ < p_286411_) {
			int j = p_286444_;
			p_286444_ = p_286411_;
			p_286411_ = j;
		}

		float f3 = (float) FastColor.ARGB32.alpha(p_286599_) / 255.0F;
		float f = (float)FastColor.ARGB32.red(p_286599_) / 255.0F;
		float f1 = (float)FastColor.ARGB32.green(p_286599_) / 255.0F;
		float f2 = (float)FastColor.ARGB32.blue(p_286599_) / 255.0F;
		VertexConsumer vertexconsumer = g.bufferSource().getBuffer(type);
		vertexconsumer.vertex(matrix4f, (float)p_286234_, (float)p_286444_, (float)p_286671_).color(f, f1, f2, f3).endVertex();
		vertexconsumer.vertex(matrix4f, (float)p_286234_, (float)p_286411_, (float)p_286671_).color(f, f1, f2, f3).endVertex();
		vertexconsumer.vertex(matrix4f, (float)p_286244_, (float)p_286411_, (float)p_286671_).color(f, f1, f2, f3).endVertex();
		vertexconsumer.vertex(matrix4f, (float)p_286244_, (float)p_286444_, (float)p_286671_).color(f, f1, f2, f3).endVertex();
		g.flush();
	}

	public static void drawText(PoseStack poseStack, int x, int y, Font font, int col, String s) {
		MultiBufferSource.BufferSource buffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
		font.drawInBatch(s, x, y, col, true, poseStack.last().pose(), buffer,
				Font.DisplayMode.NORMAL, 0, 0xF000F0);
		buffer.endBatch();
	}


}
