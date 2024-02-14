package dev.xkmc.l2weaponry.mixin;

import dev.xkmc.l2weaponry.events.LWGeneralEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningBolt.class)//TODO move to l2library
public abstract class LightningBoltMixin extends Entity {

	public LightningBoltMixin(EntityType<?> p_19870_, Level p_19871_) {
		super(p_19870_, p_19871_);
	}

	@Inject(at = @At("HEAD"), method = "spawnFire", cancellable = true)
	public void l2weaponry$spawnFire$cancelFire(int i, CallbackInfo ci) {
		if (getTags().contains(LWGeneralEvents.LIGHTNING)) {
			ci.cancel();
		}
	}

}
