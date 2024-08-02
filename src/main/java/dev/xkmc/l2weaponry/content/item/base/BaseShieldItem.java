package dev.xkmc.l2weaponry.content.item.base;

import dev.xkmc.l2core.events.SchedulerHandler;
import dev.xkmc.l2weaponry.content.capability.IShieldData;
import dev.xkmc.l2weaponry.content.capability.LWPlayerData;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public abstract class BaseShieldItem extends ShieldItem {

	public static final String KEY_LAST_DAMAGE = "last_damage";

	protected final boolean lightWeight;

	public BaseShieldItem(Properties pProperties, boolean lightWeight) {
		super(pProperties);
		this.lightWeight = lightWeight;
		LWItems.BLOCK_DECO.add(this);
	}

	public void takeDamage(ItemStack stack, Player player, int amount) {
		LWItems.BLOCKED_DAMAGE.set(stack, amount);
		if (lightWeight(stack)) {
			int cd = damageShield(player, stack, -1);
			if (cd > 0 && player instanceof ServerPlayer) {
				player.getCooldowns().addCooldown(this, cd);
				player.level().broadcastEntityEvent(player, (byte) 30);
			}
		}
	}

	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		if (!lightWeight(itemstack) && pHand == InteractionHand.OFF_HAND)
			return InteractionResultHolder.pass(itemstack);
		pPlayer.startUsingItem(pHand);
		startUse(pPlayer);
		return InteractionResultHolder.consume(itemstack);
	}

	@Override
	public InteractionResult useOn(UseOnContext pContext) {
		ItemStack stack = pContext.getItemInHand();
		InteractionHand hand = pContext.getHand();
		Player player = pContext.getPlayer();
		if (!lightWeight(stack) && hand == InteractionHand.OFF_HAND)
			return InteractionResult.PASS;
		if (player != null) {
			player.startUsingItem(hand);
			startUse(player);
			return InteractionResult.CONSUME;
		}
		return super.useOn(pContext);
	}

	protected void startUse(Player player) {
		var cap = LWPlayerData.get(player);
		cap.startReflectTimer(player);
	}

	@Override
	public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity user, int pTimeCharged) {
		if (user instanceof Player player) {
			var cap = LWPlayerData.get(player);
			cap.clearReflectTimer();
		}
	}

	public boolean lightWeight(ItemStack stack) {
		return lightWeight;
	}

	public abstract double getDefenseRecover(ItemStack stack);

	public double getMaxDefense(LivingEntity player) {
		var attr = player.getAttribute(LWItems.SHIELD_DEFENSE.holder());
		if (attr == null) return 20;
		return attr.getValue();
	}

	@Override
	public void onUseTick(Level pLevel, LivingEntity entity, ItemStack pStack, int pRemainingUseDuration) {
		if (entity instanceof ServerPlayer player) {
			if (player.getCooldowns().isOnCooldown(this)) {
				player.stopUsingItem();
			}
		}
	}

	public int damageShield(Player player, ItemStack stack, double v) {
		return damageShieldImpl(player, LWPlayerData.asData(player), stack, v);
	}

	public int damageShieldImpl(LivingEntity player, IShieldData cap, ItemStack stack, double v) {
		int damage = LWItems.BLOCKED_DAMAGE.getOrDefault(stack, 0);
		stack.remove(LWItems.BLOCKED_DAMAGE);
		double defense = cap.getShieldDefense();
		double max = getMaxDefense(player);
		double retain = cap.popRetain();
		double light = Math.max(0, damage - retain);
		double heavy = Math.max(0, damage * v - retain);
		defense += v < 0 ? light / max : lightWeight(stack) ? v : heavy / getMaxDefense(player);
		if (defense >= 1) {
			cap.setShieldDefense(1);
			return 100;
		}
		cap.setShieldDefense(defense);
		return 0;
	}

	public double reflect(ItemStack stack, Player player, LivingEntity target) {
		return reflectImpl(stack, getReflectSource(player),
				player.getAttributeValue(Attributes.ATTACK_DAMAGE),
				LWPlayerData.asData(player), target);
	}

	protected void onBlock(ItemStack stack, LivingEntity user, LivingEntity target) {

	}

	protected double onReflect(ItemStack stack, LivingEntity user, LivingEntity target, double original, double reflect) {
		return reflect;
	}

	protected DamageSource getReflectSource(Player player) {
		return player.level().damageSources().playerAttack(player);
	}

	public double reflectImpl(ItemStack stack, DamageSource source, double additional, IShieldData data, LivingEntity target) {
		LivingEntity user = (LivingEntity) source.getEntity();
		assert user != null;
		onBlock(stack, user, target);
		int damage = LWItems.BLOCKED_DAMAGE.getOrDefault(stack, 0);
		if (data.canReflect() && data.getReflectTimer() > 0) {
			double finalDamage = onReflect(stack, user, target, damage, damage + additional);
			SchedulerHandler.schedule(() -> target.hurt(source, (float) finalDamage));
			return 2;
		}
		double extra = onReflect(stack, user, target, damage, 0);
		if (extra > 0) {
			SchedulerHandler.schedule(() -> target.hurt(source, (float) extra));
		}
		return 0.5;
	}

}
