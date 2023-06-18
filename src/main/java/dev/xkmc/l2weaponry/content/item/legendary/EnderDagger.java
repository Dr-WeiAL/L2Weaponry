package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2library.init.events.attack.CreateSourceEvent;
import dev.xkmc.l2library.init.events.damage.DefaultDamageState;
import dev.xkmc.l2library.init.materials.generic.ExtraToolConfig;
import dev.xkmc.l2library.util.raytrace.IGlowingTarget;
import dev.xkmc.l2library.util.raytrace.RayTraceUtil;
import dev.xkmc.l2weaponry.content.item.types.DaggerItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.data.LangData;
import dev.xkmc.l2weaponry.mixin.TargetGoalAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnderDagger extends DaggerItem implements LegendaryWeapon, IGlowingTarget {

	public EnderDagger(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config) {
		super(tier, damage, speed, prop, config);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		var target = RayTraceUtil.serverGetTarget(player);
		if (target != null && (level.isClientSide() || TeleportUtil.teleport(player, target, true))) {
			clearTarget(target, player);
			return InteractionResultHolder.success(stack);
		}
		return InteractionResultHolder.fail(stack);
	}

	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
		if (player.getCooldowns().getCooldownPercent(this, 0) > 0)
			return InteractionResult.PASS;
		if (player.level.isClientSide) return InteractionResult.SUCCESS;
		if (TeleportUtil.teleport(player, target, true)) {
			clearTarget(target, player);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.FAIL;
	}

	@Override
	public void modifySource(LivingEntity attacker, CreateSourceEvent event, ItemStack item, @Nullable Entity target) {
		if (target instanceof Mob mob && mob.getTarget() != attacker)
			event.enable(DefaultDamageState.BYPASS_ARMOR);
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.ENDER_DAGGER.get(getDistance(pStack)));
	}

	@Override
	public int getDistance(ItemStack itemStack) {
		return LWConfig.COMMON.shadowHunterDistance.get();
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
		if (selected && level.isClientSide() && entity instanceof Player player) {
			RayTraceUtil.clientUpdateTarget(player, getDistance(stack));
		}
	}

	private void clearTarget(LivingEntity target, Player player) {
		if (target instanceof Mob mob && mob.getTarget() == player) {
			mob.setTarget(null);
			mob.setLastHurtByMob(null);
			mob.setLastHurtByPlayer(null);
			for (var e : mob.targetSelector.getAvailableGoals()) {
				if (e.getGoal() instanceof TargetGoal t) {
					((TargetGoalAccessor)t).setTargetMob(null);
				}
			}
		}
		player.getCooldowns().addCooldown(this, 60);
	}

}
