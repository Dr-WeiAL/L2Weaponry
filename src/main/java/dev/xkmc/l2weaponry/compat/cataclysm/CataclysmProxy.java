package dev.xkmc.l2weaponry.compat.cataclysm;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.init.ModEffect;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class CataclysmProxy {

	public static void stackBlazingBrand(LivingEntity user, LivingEntity target, float factor) {
		try {
			var eff = ModEffect.EFFECTBLAZING_BRAND.get();
			var old = target.getEffect(eff);
			int i = old == null ? 0 : Math.min(4, old.getAmplifier() + 1);
			MobEffectInstance ins = new MobEffectInstance(eff, 240, i, false, true, true);
			target.addEffect(ins);
			user.heal(factor * (float) CMConfig.IgnisHealingMultiplier * (float) (i + 1));
		} catch (Throwable e) {
			L2Weaponry.LOGGER.error(e);
		}
	}

}
