package dev.xkmc.l2weaponry.mixin;

import dev.xkmc.l2weaponry.content.item.base.BaseThrowableWeaponItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Drowned.class)
public abstract class DrownedMixin extends Zombie {

	public DrownedMixin(Level level) {
		super(level);
	}

	@Inject(at = @At("HEAD"), method = "performRangedAttack", cancellable = true)
	public void l2weaponry$performRangedAttack$throwOtherWeapon(LivingEntity target, float f, CallbackInfo ci) {
		ItemStack stack = getMainHandItem();
		if (stack.getItem() instanceof BaseThrowableWeaponItem item) {
			var ans = item.getProjectile(level(), this, stack, 0);
			ans.setBaseDamage(getAttributeValue(Attributes.ATTACK_DAMAGE));
			ans.getPersistentData().putInt("DespawnFactor", 20);
			double d0 = target.getX() - this.getX();
			double d1 = target.getY(0.3333333333333333D) - ans.getY();
			double d2 = target.getZ() - this.getZ();
			double d3 = Math.sqrt(d0 * d0 + d2 * d2);
			ans.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, (float) (14 - this.level().getDifficulty().getId() * 4));
			this.playSound(SoundEvents.DROWNED_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
			this.level().addFreshEntity(ans);
			ci.cancel();
		}
	}

}
