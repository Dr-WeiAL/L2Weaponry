package dev.xkmc.l2weaponry.mixin;

import dev.xkmc.l2weaponry.content.item.base.BaseShieldItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

	@Inject(at = @At("HEAD"), method = "disableShield")
	public void disableShield(boolean axe, CallbackInfo ci) {
		Player player = (Player) (Object) this;
		ItemStack stack = player.getUseItem();
		if (stack.getItem() instanceof BaseShieldItem shield) {
			int cd = shield.damageShield(player, stack, axe ? 1 : 0.25);
			if (cd > 0) {
				player.getCooldowns().addCooldown(player.getUseItem().getItem(), cd);
				player.stopUsingItem();
				player.level.broadcastEntityEvent(player, (byte) 30);
			}
		}
	}

}
