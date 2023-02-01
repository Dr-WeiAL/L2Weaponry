package dev.xkmc.l2weaponry.content.item.base;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
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
	private static final String KEY_DEFENSE_LOST = "defense_lost";

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
		if (lightWeight(stack)) {
			damageShield(entity, stack, 1);
		}
		return super.damageItem(stack, amount, entity, onBroken);
	}

	public int damageShield(LivingEntity player, ItemStack stack, double v) {
		var c = stack.getOrCreateTag();
		int damage = c.getInt(KEY_LAST_DAMAGE);
		c.putInt(KEY_LAST_DAMAGE, 0);
		double defense = c.getDouble(KEY_DEFENSE_LOST);
		defense += lightWeight(stack) ? v : damage * v / getMaxDefense(player);
		if (defense >= 1) {
			c.putDouble(KEY_DEFENSE_LOST, 1);
			return Math.min(100, (int) Math.round(20 / getDefenseRecover(stack)));
		}
		c.putDouble(KEY_DEFENSE_LOST, defense);
		return 0;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level pLevel, Entity user, int pSlotId, boolean pIsSelected) {
		if (user instanceof LivingEntity le) {
			if (le.getMainHandItem() == stack || le.getOffhandItem() == stack) {
				if (user.tickCount % 20 == 0) {
					var c = stack.getOrCreateTag();
					double defense = c.getDouble(KEY_DEFENSE_LOST);
					defense -= getDefenseRecover(stack);
					if (defense < 0) defense = 0;
					c.putDouble(KEY_DEFENSE_LOST, defense);
				}
			}
		}
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
		return slot == EquipmentSlot.MAINHAND || lightWeight(stack) && slot == EquipmentSlot.OFFHAND ? defaultModifiers : ImmutableMultimap.of();
	}

	public double getDefenseLost(ItemStack stack) {
		return stack.getOrCreateTag().getDouble(KEY_DEFENSE_LOST);
	}

}
