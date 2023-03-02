package dev.xkmc.l2weaponry.content.item.base;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2weaponry.content.capability.LWPlayerData;
import dev.xkmc.l2weaponry.init.L2WeaponryClient;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class BaseShieldItem extends ShieldItem {

	private static final String KEY_LAST_DAMAGE = "last_damage";

	private static final String NAME_ATTR = "shield_defense";

	protected final int maxDefense;
	protected final double recover;
	protected final boolean lightWeight;

	private final Multimap<Attribute, AttributeModifier> defaultModifiers;

	public BaseShieldItem(Properties pProperties, int maxDefense, double recover, boolean lightWeight) {
		super(pProperties);
		this.maxDefense = maxDefense;
		this.recover = recover;
		this.lightWeight = lightWeight;
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(LWItems.SHIELD_DEFENSE.get(), new AttributeModifier(MathHelper.getUUIDFromString(NAME_ATTR), NAME_ATTR, maxDefense, AttributeModifier.Operation.ADDITION));
		this.defaultModifiers = builder.build();
		L2WeaponryClient.BLOCK_DECO.add(this);
	}

	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		if (!lightWeight(itemstack) && pHand == InteractionHand.OFF_HAND)
			return InteractionResultHolder.pass(itemstack);
		pPlayer.startUsingItem(pHand);
		return InteractionResultHolder.consume(itemstack);
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
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
		stack.getOrCreateTag().putInt(KEY_LAST_DAMAGE, amount);
		if (entity instanceof Player player && lightWeight(stack)) {
			int cd = damageShield(player, stack, -1);
			if (cd > 0 && player instanceof ServerPlayer) {
				player.getCooldowns().addCooldown(this, cd);
				player.level.broadcastEntityEvent(player, (byte) 30);
			}
		}
		return super.damageItem(stack, amount, entity, onBroken);
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
		var c = stack.getOrCreateTag();
		int damage = c.getInt(KEY_LAST_DAMAGE);
		c.putInt(KEY_LAST_DAMAGE, 0);
		var cap = LWPlayerData.HOLDER.get(player);
		double defense = cap.getShieldDefense();
		defense += v < 0 ? damage / getMaxDefense(player) : lightWeight(stack) ? v : damage * v / getMaxDefense(player);
		if (defense >= 1) {
			cap.setShieldDefense(1);
			return 100;
		}
		cap.setShieldDefense(defense);
		return 0;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
		return slot == EquipmentSlot.MAINHAND || lightWeight(stack) && slot == EquipmentSlot.OFFHAND ? defaultModifiers : ImmutableMultimap.of();
	}

}
