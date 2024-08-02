package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2library.init.FlagMarker;
import dev.xkmc.l2weaponry.content.entity.BaseThrownWeaponEntity;
import dev.xkmc.l2weaponry.content.item.types.JavelinItem;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class StormJavelin extends JavelinItem implements LegendaryWeapon {

	public StormJavelin(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config);
	}

	@Override
	public boolean causeThunder(BaseThrownWeaponEntity<?> entity) {
		return true;
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity user) {
		super.hurtEnemy(stack, target, user);
		if (user.level().isClientSide) return true;
		BlockPos blockpos = target.blockPosition();
		LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(user.level());
		assert bolt != null;
		bolt.addTag(FlagMarker.LIGHTNING);
		bolt.moveTo(Vec3.atBottomCenterOf(blockpos));
		bolt.setCause(user instanceof ServerPlayer serverPlayer ? serverPlayer : null);
		user.level().addFreshEntity(bolt);
		user.playSound(SoundEvents.TRIDENT_THUNDER.value(), 5, 1);
		return true;
	}

	@Override
	public boolean isImmuneTo(DamageSource source) {
		return source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypeTags.IS_LIGHTNING);
	}

	@Override
	protected boolean canSweep() {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack pStack, TooltipContext pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.STORM_JAVELIN.get());
	}

}
