package dev.xkmc.l2weaponry.mixin;

import dev.xkmc.l2weaponry.content.item.base.BaseShieldItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

	@Deprecated
	protected PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	@Inject(at = @At("HEAD"), method = "hurtCurrentlyUsedShield")
	public void l2weaponry_hurtCurrentlyUsedShield_customShield(float pDamage, CallbackInfo ci) {
		if (useItem.getItem() instanceof BaseShieldItem shield) {
			Player player = (Player) (Object) this;
			int amount = (int) Math.floor(pDamage);
			shield.takeDamage(useItem, player, amount);
		}
	}

	@Inject(at = @At("HEAD"), method = "blockUsingShield", cancellable = true)
	public void l2weaponry_blockUsingShield_customShield(LivingEntity pEntity, CallbackInfo ci) {
		Player player = (Player) (Object) this;
		ItemStack stack = player.getUseItem();
		if (stack.getItem() instanceof BaseShieldItem shield) {
			double strength = shield.reflect(stack, player, pEntity);
			pEntity.knockback(strength, player.getX() - pEntity.getX(), player.getZ() - pEntity.getZ());
			if (pEntity.canDisableShield()) {
				player.disableShield(true);
			}
			ci.cancel();
		}
	}

	@Inject(at = @At("HEAD"), method = "disableShield", cancellable = true)
	public void l2weaponry_disableShield_customShield(boolean axe, CallbackInfo ci) {
		Player player = (Player) (Object) this;
		ItemStack stack = player.getUseItem();
		if (stack.getItem() instanceof BaseShieldItem shield) {
			int cd = shield.damageShield(player, stack, axe ? 1 : 0.25);
			if (cd > 0) {
				player.getCooldowns().addCooldown(player.getUseItem().getItem(), cd);
				player.stopUsingItem();
				player.level().broadcastEntityEvent(player, (byte) 30);
			}
			ci.cancel();
		}
	}

}
