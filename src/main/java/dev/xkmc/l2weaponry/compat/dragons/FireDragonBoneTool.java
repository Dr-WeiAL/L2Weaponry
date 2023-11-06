package dev.xkmc.l2weaponry.compat.dragons;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class FireDragonBoneTool extends ExtraToolConfig {

	@Override
	public void onHit(ItemStack stack, LivingEntity target, LivingEntity user) {
		super.onHit(stack, target, user);
		if (IafConfig.dragonWeaponFireAbility) {
			if (target instanceof EntityIceDragon) {
				target.hurt(user.level().damageSources().inFire(), 13.5F);
			}

			target.setSecondsOnFire(5);
			target.knockback(1.0D, user.getX() - target.getX(), user.getZ() - target.getZ());
		}
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(Component.translatable("dragon_sword_fire.hurt1").withStyle(ChatFormatting.GREEN));
		if (IafConfig.dragonWeaponFireAbility) {
			list.add(Component.translatable("dragon_sword_fire.hurt2").withStyle(ChatFormatting.DARK_RED));
		}
	}
}
