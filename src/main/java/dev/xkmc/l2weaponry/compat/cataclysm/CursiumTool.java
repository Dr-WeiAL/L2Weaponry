package dev.xkmc.l2weaponry.compat.cataclysm;

import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.base.IStackableWeapon;
import dev.xkmc.l2weaponry.init.data.LangData;
import dev.xkmc.l2weaponry.init.materials.LWExtraConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CursiumTool extends ExtraToolConfig implements LWExtraConfig {

	@Override
	public void onDamage(AttackCache cache, ItemStack stack) {
		int count = IStackableWeapon.getHitCount(stack);
		var attacker = cache.getAttacker();
		if (count > 0 && attacker != null && stack.getItem() instanceof IStackableWeapon item) {
			int max = item.getMaxStack(stack, attacker);
			float bonus = 0.2f * Mth.clamp(count, 0, max);
			cache.addHurtModifier(DamageModifier.multTotal(1 + bonus));
		}
	}

	@Override
	public int getExtraStacking(ItemStack stack, @Nullable LivingEntity user) {
		return 5;
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(LangData.MATS_CURSIUM.get());
	}

}
