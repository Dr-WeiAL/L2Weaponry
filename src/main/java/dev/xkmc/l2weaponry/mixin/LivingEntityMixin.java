package dev.xkmc.l2weaponry.mixin;

import dev.xkmc.l2weaponry.content.item.base.BaseClawItem;
import dev.xkmc.l2weaponry.content.item.legendary.LegendaryWeapon;
import dev.xkmc.l2weaponry.content.item.types.ClawItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	public LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	@Shadow
	public abstract ItemStack getMainHandItem();

	@Shadow
	public abstract ItemStack getOffhandItem();

	@Inject(at = @At("HEAD"), method = "canFreeze", cancellable = true)
	public void l2weaponry_canFreeze_cancelFreeze(CallbackInfoReturnable<Boolean> cir) {
		if (getMainHandItem().getItem() instanceof LegendaryWeapon weapon && weapon.cancelFreeze()) {
			cir.setReturnValue(false);
		}
	}

	@Inject(at = @At("HEAD"), method = "isDamageSourceBlocked", cancellable = true)
	public void l2weaponry_isDamageSourceBlocked_clawBlock(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
		ItemStack stack = getMainHandItem();
		if (stack.getItem() instanceof ClawItem) {
			long gameTime = level.getGameTime();
			if (gameTime > BaseClawItem.getLastTime(stack) + LWConfig.COMMON.claw_block_time.get()) {
				return;
			}
			Entity entity = source.getDirectEntity();
			boolean flag = false;
			if (entity instanceof AbstractArrow abstractarrow) {
				if (abstractarrow.getPierceLevel() > 0) {
					flag = true;
				}
			}
			if (!source.is(DamageTypeTags.BYPASSES_ARMOR) && !flag) {
				if (getOffhandItem().getItem() == stack.getItem()) {
					cir.setReturnValue(true);
					return;
				}
				Vec3 vec32 = source.getSourcePosition();
				if (vec32 != null) {
					Vec3 vec3 = getViewVector(1.0F);
					Vec3 vec31 = vec32.vectorTo(position()).normalize();
					vec31 = new Vec3(vec31.x, 0.0D, vec31.z);
					if (vec31.dot(vec3) < 0.0D) {
						cir.setReturnValue(true);
					}
				}
			}
		}
	}

}
