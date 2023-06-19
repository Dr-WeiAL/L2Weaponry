package dev.xkmc.l2weaponry.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.xkmc.l2library.util.raytrace.FastItem;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public class ClientLocalPlayerMixin {

	@Shadow
	private boolean startedUsingItem;

	@WrapOperation(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z"))
	private boolean l2weaponry_canSprint(LocalPlayer player, Operation<Boolean> op) {
		if (startedUsingItem) {
			ItemStack stack = player.getUseItem();
			if (stack.getItem() instanceof FastItem fast) {
				if (fast.isFast(stack)) {
					return false;
				}
			}
		}
		return op.call(player);
	}

}
