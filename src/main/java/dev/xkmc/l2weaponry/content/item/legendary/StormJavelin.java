package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.entity.BaseThrownWeaponEntity;
import dev.xkmc.l2weaponry.content.entity.JavelinEntity;
import dev.xkmc.l2weaponry.content.item.types.JavelinItem;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StormJavelin extends JavelinItem implements LegendaryWeapon {

	public StormJavelin(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config) {
		super(tier, damage, speed, prop, config);
	}

	@Override
	public void onHitBlock(BaseThrownWeaponEntity<?> entity, ItemStack item) {
		if (entity.level.isClientSide) return;
		if (entity.remainingHit <= 0) return;
		BlockPos blockpos = entity.blockPosition();
		LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(entity.level);
		assert bolt != null;
		bolt.moveTo(Vec3.atBottomCenterOf(blockpos));
		bolt.setCause(entity.getOwner() instanceof ServerPlayer ? (ServerPlayer) entity.getOwner() : null);
		entity.level.addFreshEntity(bolt);
		entity.playSound(SoundEvents.TRIDENT_THUNDER, 5, 1);
	}

	@Override
	public void onHitEntity(BaseThrownWeaponEntity<?> entity, ItemStack item, LivingEntity le) {
		if (entity.level.isClientSide) return;
		if (entity.remainingHit <= 0) return;
		BlockPos blockpos = entity.blockPosition();
		LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(entity.level);
		assert bolt != null;
		bolt.moveTo(Vec3.atBottomCenterOf(blockpos));
		bolt.setCause(entity.getOwner() instanceof ServerPlayer ? (ServerPlayer) entity.getOwner() : null);
		entity.level.addFreshEntity(bolt);
		entity.playSound(SoundEvents.TRIDENT_THUNDER, 5, 1);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity user) {
		super.hurtEnemy(stack, target, user);
		if (user.level.isClientSide) return true;
		BlockPos blockpos = target.blockPosition();
		LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(user.level);
		assert bolt != null;
		bolt.moveTo(Vec3.atBottomCenterOf(blockpos));
		bolt.setCause(user instanceof ServerPlayer serverPlayer ? serverPlayer : null);
		user.level.addFreshEntity(bolt);
		user.playSound(SoundEvents.TRIDENT_THUNDER, 5, 1);
		return true;
	}

	@Override
	public boolean isImmuneTo(DamageSource source) {
		return source.isFire() || source == DamageSource.LIGHTNING_BOLT;
	}

	@Override
	protected boolean canSweep() {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.STORM_JAVELIN.get());
	}

	@Override
	public JavelinEntity getProjectile(Level level, LivingEntity player, ItemStack stack, int slot) {
		JavelinEntity ans = super.getProjectile(level, player, stack, slot);
		ans.waterInertia = 0.99f;
		return ans;
	}
}
