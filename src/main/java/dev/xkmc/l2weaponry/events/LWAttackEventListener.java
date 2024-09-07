package dev.xkmc.l2weaponry.events;

import dev.xkmc.l2complements.content.enchantment.core.SourceModifierEnchantment;
import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import dev.xkmc.l2damagetracker.contents.attack.*;
import dev.xkmc.l2damagetracker.contents.materials.generic.GenericTieredItem;
import dev.xkmc.l2weaponry.content.entity.BaseThrownWeaponEntity;
import dev.xkmc.l2weaponry.content.item.base.DoubleWieldItem;
import dev.xkmc.l2weaponry.content.item.base.LWTieredItem;
import dev.xkmc.l2weaponry.content.item.legendary.LegendaryWeapon;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import dev.xkmc.l2weaponry.init.materials.LWExtraConfig;
import dev.xkmc.l2weaponry.init.registrate.LWEnchantments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

import java.util.Optional;
import java.util.function.BiConsumer;

public class LWAttackEventListener implements AttackListener {

	public static final ResourceLocation STRENGTH_CHECK = L2Weaponry.loc("attack_strength_check");
	public static final ResourceLocation WEAPON_BONUS = L2Weaponry.loc("weapon_type_bonus");

	@Override
	public void onCreateSource(CreateSourceEvent event) {
		if (event.getOriginal().equals(DamageTypes.MOB_ATTACK) || event.getOriginal().equals(DamageTypes.PLAYER_ATTACK)) {
			ItemStack stack = event.getAttacker().getMainHandItem();
			if (stack.getItem() instanceof LWTieredItem item) {
				Entity target = Optional.of(event)
						.map(CreateSourceEvent::getPlayerAttackCache)
						.map(PlayerAttackCache::getPlayerAttackEntityEvent)
						.map(AttackEntityEvent::getTarget).orElse(null);
				item.modifySource(event.getAttacker(), event, event.getAttacker().getMainHandItem(), target);
			}
		} else if (event.getDirect() instanceof BaseThrownWeaponEntity<?> thrown) {
			ItemStack stack = thrown.getItem();
			if (stack.getItem() instanceof LWTieredItem weapon) {
				if (thrown.getOwner() instanceof LivingEntity le) {
					weapon.modifySource(le, event, stack, thrown.targetCache);
					LegacyEnchantment.findAll(stack, SourceModifierEnchantment.class, true)
							.forEach(x -> x.val().modify(event, stack, x.lv()));
				}
			}
		}
	}

	@Override
	public void setupProfile(DamageData data, BiConsumer<LivingEntity, ItemStack> setup) {
		if (data.getSource().getDirectEntity() instanceof BaseThrownWeaponEntity<?> thrown) {
			if (thrown.getOwner() instanceof LivingEntity le) {
				setup.accept(le, thrown.getItem());
			}
		}
	}

	@Override
	public boolean onAttack(DamageData.Attack data) {
		if (data.getTarget().getMainHandItem().getItem() instanceof LegendaryWeapon weapon) {
			return weapon.isImmuneTo(data.getSource());
		}
		return false;
	}

	@Override
	public void onHurt(DamageData.Offence data) {
		LivingEntity le = data.getAttacker();
		if (le == null) return;
		ItemStack stack = data.getWeapon();
		if (!stack.isEmpty()) {
			if (stack.getItem() instanceof GenericTieredItem) {
				if (data.getStrength() < 0.7f) {
					data.addHurtModifier(DamageModifier.nonlinearFinal(10000, f -> 0.1f, STRENGTH_CHECK));
					return;
				}
			}
			if (stack.getItem() instanceof DoubleWieldItem claw) {
				claw.accumulateDamage(stack, le);
			}
		}
		if (!stack.isEmpty() && stack.getItem() instanceof LWTieredItem w) {
			data.addHurtModifier(DamageModifier.multAttr(w.getMultiplier(data), WEAPON_BONUS));
			if (w.getExtraConfig() instanceof LWExtraConfig config) {
				config.onHurt(data, le, stack);
			}
		}
		if (stack.getItem() instanceof LegendaryWeapon weapon) {
			weapon.onHurt(data, le, stack);
		}
	}

	@Override
	public void onHurtMaximized(DamageData.OffenceMax data) {
		LivingEntity le = data.getAttacker();
		ItemStack stack = data.getWeapon();
		if (le != null && stack.getItem() instanceof LegendaryWeapon weapon) {
			weapon.onHurtMaximized(data, le);
		}
	}

	@Override
	public void onDamageFinalized(DamageData.DefenceMax data) {
		LivingEntity le = data.getAttacker();
		ItemStack stack = data.getWeapon();
		if (le == null || stack.isEmpty()) return;
		if (stack.getItem() instanceof LegendaryWeapon weapon) {
			weapon.onDamageFinal(data, le);
		}
		if (stack.getItem() instanceof DoubleWieldItem item) {
			if (LWEnchantments.GHOST_SLASH.getLv(stack) > 0 && data.getStrength() >= 0.9) {
				item.accumulateDamage(stack, le);
				if (!(le instanceof Player player) || !player.getAbilities().instabuild) {
					stack.hurtAndBreak(1, le, EquipmentSlot.MAINHAND);
				}
			}
		}
	}

	@Override
	public boolean onCriticalHit(PlayerAttackCache cache, CriticalHitEvent event) {
		if (!event.isVanillaCritical() && !event.isCriticalHit()) return false;
		if (event.getEntity().level().isClientSide()) return false;
		if (cache.getWeapon().getItem() instanceof LegendaryWeapon weapon) {
			weapon.onCrit(event.getEntity(), event.getTarget());
		}
		return false;
	}

}
