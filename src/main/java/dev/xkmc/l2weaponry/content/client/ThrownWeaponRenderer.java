package dev.xkmc.l2weaponry.content.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.xkmc.l2weaponry.content.entity.BaseThrownWeaponEntity;
import dev.xkmc.l2weaponry.init.data.TagGen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ThrownWeaponRenderer<T extends BaseThrownWeaponEntity<T>> extends EntityRenderer<T> {

	public static final ResourceLocation TRIDENT_LOCATION = new ResourceLocation("textures/entity/trident.png");

	public ThrownWeaponRenderer(EntityRendererProvider.Context pContext) {
		super(pContext);
	}

	public void render(T entity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
		pMatrixStack.pushPose();
		if (!entity.getItem().is(TagGen.JAVELIN))
			pMatrixStack.translate(0, 0.25, 0);
		pMatrixStack.scale(1.47f, 1.47f, 1.47f);
		pMatrixStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
		pMatrixStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTicks, entity.xRotO, entity.getXRot()) - 90.0F));
		if (entity.getItem().is(TagGen.JAVELIN)) {
			pMatrixStack.translate(0, -0.4, 0);
		} else {
			pMatrixStack.mulPose(Axis.YP.rotationDegrees(180));
		}
		pMatrixStack.mulPose(Axis.ZP.rotationDegrees(45));

		var ir = Minecraft.getInstance().getItemRenderer();
		var model = ir.getModel(entity.getItem(), entity.level(), null, entity.getId());

		ir.render(entity.getItem(), ItemDisplayContext.GROUND, false,
				pMatrixStack, pBuffer, pPackedLight, OverlayTexture.NO_OVERLAY, model);
		pMatrixStack.popPose();
		super.render(entity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	public ResourceLocation getTextureLocation(T pEntity) {
		return TRIDENT_LOCATION;
	}

}