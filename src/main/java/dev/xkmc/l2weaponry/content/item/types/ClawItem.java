package dev.xkmc.l2weaponry.content.item.types;

import com.google.common.collect.ImmutableMultimap;
import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2library.init.events.attack.AttackCache;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2weaponry.content.item.base.GenericWeaponItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;

public class ClawItem extends GenericWeaponItem {

	public static final AttributeModifier RANGE = new AttributeModifier(MathHelper.getUUIDFromString("claw_reach"), "claw_reach", -1, AttributeModifier.Operation.ADDITION);

	public ClawItem(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config) {
		super(tier, damage, speed, prop, config, BlockTags.MINEABLE_WITH_HOE);
	}

	@Override
	protected void addModifiers(ImmutableMultimap.Builder<Attribute, AttributeModifier> builder) {
		super.addModifiers(builder);
		builder.put(ForgeMod.ATTACK_RANGE.get(), RANGE);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
		return toolAction == ToolActions.SWORD_SWEEP;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment == Enchantments.SWEEPING_EDGE || super.canApplyAtEnchantingTable(stack, enchantment);
	}

	@Override
	public @NotNull AABB getSweepHitBox(@NotNull ItemStack stack, @NotNull Player player, @NotNull Entity target) {
		if (player.getOffhandItem().getItem() == this) {
			double r = player.getAttributeValue(ForgeMod.ATTACK_RANGE.get());
			return player.getBoundingBox().inflate(r, r, r);
		} else {
			return player.getBoundingBox().inflate(1, 0.25, 1);
		}
	}

	public void accumulateDamage(ItemStack stack, long gameTime) {
		long last = stack.getOrCreateTag().getLong("last_hit_time");
		if (gameTime > last + LWConfig.COMMON.claw_timeout.get()) {
			stack.getOrCreateTag().putInt("hit_count", 1);
		} else {
			stack.getOrCreateTag().putInt("hit_count", stack.getOrCreateTag().getInt("hit_count") + 1);
		}
		stack.getOrCreateTag().putLong("last_hit_time", gameTime);
	}

	@Override
	public float getMultiplier(AttackCache event) {
		int count = event.getWeapon().getOrCreateTag().getInt("hit_count");
		if (count > 1) {
			int max = LWConfig.COMMON.claw_max.get();
			if (event.getAttacker().getOffhandItem().getItem() == this) {
				max *= 2;
			}
			return (float) (1 + LWConfig.COMMON.claw_bonus.get() * Mth.clamp(count - 1, 0, max));
		}
		return super.getMultiplier(event);
	}
}
