package dev.xkmc.l2weaponry.compat.aerial;

import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.base.BaseShieldItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.data.LangData;
import dev.xkmc.l2weaponry.init.materials.LWExtraConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.LevelEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArsonistTool extends ExtraToolConfig implements LWExtraConfig {

	@Nullable
	@Override
	public DamageSource getReflectSource(Player player) {
		return player.level().damageSources().thorns(player);
	}

	@Override
	public double onShieldReflect(ItemStack stack, LivingEntity user, LivingEntity entity, double original, double reflect) {
		if (reflect > 0 && entity.level() instanceof ServerLevel sl) {
			entity.setDeltaMovement(entity.getDeltaMovement().add(0, 1, 0));
			sl.sendParticles(ParticleTypes.EXPLOSION,
					entity.getX(), entity.getY(), entity.getZ(),
					1, 0, 0, 0, 1);
			sl.playSound(null, entity.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS);
		}
		if (!entity.fireImmune()) {
			reflect += 5;
			entity.level().levelEvent(LevelEvent.SOUND_EXTINGUISH_FIRE, entity.blockPosition(), 0);
		}
		return reflect;
	}

	@Override
	public void onDamage(AttackCache cache, ItemStack stack) {
		if (!cache.getAttackTarget().fireImmune() && !(stack.getItem() instanceof BaseShieldItem)) {
			cache.getAttackTarget().setSecondsOnFire(LWConfig.SERVER.fieryDuration.get());
		}
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		if (stack.getItem() instanceof BaseShieldItem) {
			list.add(LangData.MATS_AH_ARSON_SHIELD.get(5));
		} else {
			list.add(LangData.MATS_AH_ARSON.get(LWConfig.SERVER.fieryDuration.get()));
		}
	}

}
