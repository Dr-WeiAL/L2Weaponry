package dev.xkmc.l2weaponry.mixin;

import dev.xkmc.l2weaponry.content.item.legendary.LegendaryWeapon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Shadow
	public abstract ItemStack getMainHandItem();

	@Inject(at = @At("HEAD"), method = "canFreeze", cancellable = true)
	public void l2weaponry_canFreeze_cancelFreeze(CallbackInfoReturnable<Boolean> cir) {
		if (getMainHandItem().getItem() instanceof LegendaryWeapon weapon && weapon.cancelFreeze()) {
			cir.setReturnValue(false);
		}
	}

}
