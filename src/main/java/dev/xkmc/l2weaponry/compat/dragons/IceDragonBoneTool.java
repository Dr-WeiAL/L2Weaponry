package dev.xkmc.l2weaponry.compat.dragons;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import com.github.alexthe666.iceandfire.entity.props.FrozenProperties;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class IceDragonBoneTool extends ExtraToolConfig {

	@Override
	public void onHit(ItemStack stack, LivingEntity target, LivingEntity user) {
		super.onHit(stack, target, user);
		if (IafConfig.dragonWeaponIceAbility) {
			if (target instanceof EntityFireDragon) {
				target.hurt(user.level().damageSources().drown(), 13.5F);
			}
			FrozenProperties.setFrozenFor(target, 200);
			target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
			target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 2));
			target.knockback(1.0D, user.getX() - target.getX(), user.getZ() - target.getZ());
		}
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(Component.translatable("dragon_sword_ice.hurt1").withStyle(ChatFormatting.GREEN));
		if (IafConfig.dragonWeaponIceAbility) {
			list.add(Component.translatable("dragon_sword_ice.hurt2").withStyle(ChatFormatting.AQUA));
		}
	}
}
