package dev.xkmc.l2weaponry.content.item.base;

import dev.xkmc.l2complements.init.materials.LCMats;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.entity.BaseThrownWeaponEntity;
import dev.xkmc.l2weaponry.init.registrate.LWEnchantments;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public abstract class BaseThrowableWeaponItem extends GenericWeaponItem implements IThrowableCallback {

	public BaseThrowableWeaponItem(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config, TagKey<Block> blocks) {
		super(tier, damage, speed, prop, config, blocks);
		LWItems.THROW_DECO.add(this);
	}

	public UseAnim getUseAnimation(ItemStack pStack) {
		return UseAnim.SPEAR;
	}

	public int getUseDuration(ItemStack pStack) {
		return 72000;
	}

	public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int timeLeft) {
		if (user instanceof Player player) {
			int time = this.getUseDuration(stack) - timeLeft;
			if (time >= 10) {
				if (!level.isClientSide) {
					int slot = user.getUsedItemHand() == InteractionHand.OFF_HAND ? 40 : player.getInventory().selected;
					boolean projection = stack.getEnchantmentLevel(LWEnchantments.PROJECTION.get()) > 0;
					boolean no_pickup = projection || player.getAbilities().instabuild;
					stack.hurtAndBreak(1, player, pl -> pl.broadcastBreakEvent(user.getUsedItemHand()));
					AbstractArrow proj = getProjectile(level, player, stack, slot);
					proj.setBaseDamage(player.getAttributeValue(Attributes.ATTACK_DAMAGE));
					proj.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
					if (no_pickup) {
						proj.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
					}
					if (projection) {
						proj.getPersistentData().putInt("DespawnFactor", 20);
					}
					level.addFreshEntity(proj);
					level.playSound(null, proj, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
					if (!no_pickup) {
						player.getInventory().removeItem(stack);
					}
					player.awardStat(Stats.ITEM_USED.get(this));
				}
			}
		}
	}

	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
			return InteractionResultHolder.fail(itemstack);
		} else {
			pPlayer.startUsingItem(pHand);
			return InteractionResultHolder.consume(itemstack);
		}
	}

	public abstract BaseThrownWeaponEntity<?> getProjectile(Level level, LivingEntity player, ItemStack stack, int slot);

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		if (getTier() == LCMats.POSEIDITE.getTier()) {
			if (enchantment == Enchantments.CHANNELING) {
				return true;
			}
		}
		return enchantment == Enchantments.LOYALTY || super.canApplyAtEnchantingTable(stack, enchantment);
	}
}
