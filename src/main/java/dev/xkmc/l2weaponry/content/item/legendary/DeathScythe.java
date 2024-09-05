package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.types.ScytheItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DeathScythe extends ScytheItem implements LegendaryWeapon {

	public DeathScythe(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config) {
		super(tier, damage, speed, prop, config);
	}

	@Override
	public void onHurt(AttackCache event, LivingEntity le, ItemStack stack) {
		if (event.getCriticalHitEvent() != null && event.getStrength() < 0.9) return;
		float health = event.getAttackTarget().getHealth();
		float max = event.getAttackTarget().getMaxHealth();
		float factor = (float) (double) LWConfig.COMMON.deathScytheMax.get();
		event.addHurtModifier(DamageModifier.multTotal(1 + factor * (1 - health / max)));
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		int max = (int) Math.round(LWConfig.COMMON.deathScytheMax.get() * 100);
		list.add(LangData.DEATH_SCYTHE.get(max));
	}

}
