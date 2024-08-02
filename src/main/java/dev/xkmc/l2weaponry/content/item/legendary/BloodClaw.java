package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.types.ClawItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.data.LangData;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class BloodClaw extends ClawItem implements LegendaryWeapon {

	private static final String KEY_KILL = "killCount";

	private static int getBonus(int kill) {
		if (kill <= 0) return 0;
		return (int) Math.round(Math.log(kill) / Math.log(2));
	}

	public BloodClaw(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config);
	}

	@Override
	public void onDamageFinal(DamageData.DefenceMax cache, LivingEntity le) {
		le.heal(cache.getDamageFinal());
	}

	@Override
	public int getMaxStack(ItemStack stack, LivingEntity user) {
		int max = LWConfig.SERVER.claw_max.get() + getBonus(LWItems.KILL_COUNT.getOrDefault(stack, 0));
		if (user.getOffhandItem().getItem() == this) {
			max *= 2;
		}
		return max;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		int kill = LWItems.KILL_COUNT.getOrDefault(stack, 0);
		list.add(LangData.BLOOD_CLAW.get());
		list.add(LangData.STAT_KILL.get(Component.literal("" + kill)));
		list.add(LangData.STAT_BONUS_CLAW.get(Component.literal("+" + getBonus(kill))));
	}

	@Override
	public void onKill(ItemStack stack, LivingEntity target, LivingEntity user) {
		if (target instanceof Enemy) {
			int kill = LWItems.KILL_COUNT.getOrDefault(stack, 0);
			LWItems.KILL_COUNT.set(stack, kill + 1);
		}
	}

}
