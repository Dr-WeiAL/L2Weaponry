package dev.xkmc.l2weaponry.content.item.base;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2weaponry.content.capability.IShieldData;
import dev.xkmc.l2weaponry.content.capability.LWPlayerData;
import dev.xkmc.l2weaponry.events.LWGeneralEvents;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class BaseShieldItem extends ShieldItem {

	public static final String KEY_LAST_DAMAGE = "last_damage";

	private static final String NAME_ATTR_MAIN = "shield_defense_mainhand";
	private static final String NAME_ATTR_OFF = "shield_defense_offhand";

	protected final int maxDefense;
	protected final double recover;
	protected final boolean lightWeight;

	private final Multimap<Attribute, AttributeModifier> mainhandModifiers;
	private final Multimap<Attribute, AttributeModifier> offhandModifiers;

	public BaseShieldItem(Properties pProperties, int maxDefense, double recover, boolean lightWeight) {
		super(pProperties);
		this.maxDefense = maxDefense;
		this.recover = recover;
		this.lightWeight = lightWeight;
		{
			ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			buildAttributes(EquipmentSlot.MAINHAND, builder);
			this.mainhandModifiers = builder.build();
		}
		{
			ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			buildAttributes(EquipmentSlot.OFFHAND, builder);
			this.offhandModifiers = builder.build();
		}
		LWItems.BLOCK_DECO.add(this);
	}

	protected void buildAttributes(EquipmentSlot slot, ImmutableMultimap.Builder<Attribute, AttributeModifier> builder) {
		if (slot == EquipmentSlot.MAINHAND)
			builder.put(LWItems.SHIELD_DEFENSE.get(), new AttributeModifier(MathHelper.getUUIDFromString(NAME_ATTR_MAIN), NAME_ATTR_MAIN, maxDefense, AttributeModifier.Operation.ADDITION));
		if (slot == EquipmentSlot.OFFHAND)
			builder.put(LWItems.SHIELD_DEFENSE.get(), new AttributeModifier(MathHelper.getUUIDFromString(NAME_ATTR_OFF), NAME_ATTR_OFF, maxDefense, AttributeModifier.Operation.ADDITION));
	}

	public void takeDamage(ItemStack stack, Player player, int amount) {
		stack.getOrCreateTag().putInt(BaseShieldItem.KEY_LAST_DAMAGE, amount);
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
		var cap = LWPlayerData.HOLDER.get(player);
		cap.startReflectTimer();
	}

	@Override
	public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity user, int pTimeCharged) {
		if (user instanceof Player player) {
			var cap = LWPlayerData.HOLDER.get(player);
			cap.clearReflectTimer();
		}
	}

	public boolean lightWeight(ItemStack stack) {
		return lightWeight;
	}

	public double getDefenseRecover(ItemStack stack) {
		return recover;
	}

	public double getMaxDefense(LivingEntity player) {
		var attr = player.getAttribute(LWItems.SHIELD_DEFENSE.get());
		if (attr == null) {
			return maxDefense;
		}
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
		return damageShieldImpl(player, LWPlayerData.HOLDER.get(player), stack, v);
	}

	public int damageShieldImpl(LivingEntity player, IShieldData cap, ItemStack stack, double v) {
		var c = stack.getOrCreateTag();
		int damage = c.getInt(KEY_LAST_DAMAGE);
		c.putInt(KEY_LAST_DAMAGE, 0);
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

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
		return slot == EquipmentSlot.MAINHAND ? mainhandModifiers : lightWeight(stack) && slot == EquipmentSlot.OFFHAND ? offhandModifiers : ImmutableMultimap.of();
	}

	public double reflect(ItemStack stack, Player player, LivingEntity target) {
		return reflectImpl(stack, player.level().damageSources().playerAttack(player),
				player.getAttributeValue(Attributes.ATTACK_DAMAGE),
				LWPlayerData.HOLDER.get(player), target);
	}

	protected void onBlock(ItemStack stack, LivingEntity user, LivingEntity target) {

	}

	protected double onReflect(ItemStack stack, LivingEntity user, LivingEntity target, double original, double reflect) {
		return reflect;
	}

	public double reflectImpl(ItemStack stack, DamageSource source, double additional, IShieldData data, LivingEntity target) {
		LivingEntity user = (LivingEntity) source.getEntity();
		assert user != null;
		onBlock(stack, user, target);
		var c = stack.getOrCreateTag();
		double damage = c.getInt(KEY_LAST_DAMAGE);
		if (data.canReflect() && data.getReflectTimer() > 0) {
			double finalDamage = onReflect(stack, user, target, damage, damage + additional);
			LWGeneralEvents.schedule(() -> target.hurt(source, (float) finalDamage));
			return 2;
		}
		double extra = onReflect(stack, user, target, damage, 0);
		if (extra > 0) {
			LWGeneralEvents.schedule(() -> target.hurt(source, (float) extra));
		}
		return 0.5;
	}

}
