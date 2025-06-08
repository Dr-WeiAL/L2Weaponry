package dev.xkmc.l2weaponry.compat.cataclysm;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Halberd_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModParticle;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

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


	public static int spawnHalberd(Vec3 pos, LivingEntity player, int delay) {
		try {
			Level var2 = player.level();
			if (var2 instanceof ServerLevel sl) {
				strikeWindmillHalberd(sl, pos, player, 7, 5, (double) 1.0F, (double) 1.0F, 0.2, delay);
			}
			return CMConfig.SoulRenderCooldown;
		} catch (Throwable e) {
			L2Weaponry.LOGGER.throwing(e);
			return 20;
		}
	}

	private static void strikeWindmillHalberd(ServerLevel level, Vec3 pos, LivingEntity user, int numberOfBranches, int particlesPerBranch, double initialRadius, double radiusIncrement, double curveFactor, int delay) {
		float angleIncrement = (float) ((Math.PI * 2D) / (double) numberOfBranches);

		for (int branch = 0; branch < numberOfBranches; ++branch) {
			float baseAngle = angleIncrement * (float) branch;

			for (int i = 0; i < particlesPerBranch; ++i) {
				double currentRadius = initialRadius + (double) i * radiusIncrement;
				float currentAngle = (float) ((double) baseAngle + (double) ((float) i * angleIncrement) / initialRadius + (double) ((float) ((double) i * curveFactor)));
				double xOffset = currentRadius * Math.cos((double) currentAngle);
				double zOffset = currentRadius * Math.sin((double) currentAngle);
				double spawnX = pos.x() + xOffset;
				double spawnY = pos.y() + 0.3;
				double spawnZ = pos.z() + zOffset;
				int d3 = delay + i + 1;
				level.sendParticles((SimpleParticleType) ModParticle.PHANTOM_WING_FLAME.get(), spawnX, spawnY, spawnZ, 1, (double) 0.0F, (double) 0.0F, (double) 0.0F, 0.007);
				spawnHalberd(spawnX, spawnZ, pos.y() - (double) 5.0F, pos.y() + (double) 3.0F, currentAngle, d3, level, user);
			}
		}

	}

	private static void spawnHalberd(double x, double z, double minY, double maxY, float rotation, int delay, Level world, LivingEntity player) {
		BlockPos blockpos = BlockPos.containing(x, maxY, z);
		boolean flag = false;
		double d0 = (double) 0.0F;

		do {
			BlockPos blockpos1 = blockpos.below();
			BlockState blockstate = world.getBlockState(blockpos1);
			if (blockstate.isFaceSturdy(world, blockpos1, Direction.UP)) {
				if (!world.isEmptyBlock(blockpos)) {
					BlockState blockstate1 = world.getBlockState(blockpos);
					VoxelShape voxelshape = blockstate1.getCollisionShape(world, blockpos);
					if (!voxelshape.isEmpty()) {
						d0 = voxelshape.max(Direction.Axis.Y);
					}
				}

				flag = true;
				break;
			}

			blockpos = blockpos.below();
		} while (blockpos.getY() >= Mth.floor(minY) - 1);

		if (flag) {
			world.addFreshEntity(new Phantom_Halberd_Entity(world, x, (double) blockpos.getY() + d0, z, rotation, delay, player, (float) CMConfig.PhantomHalberddamage));
		}

	}

}
