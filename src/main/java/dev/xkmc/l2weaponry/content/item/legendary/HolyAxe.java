package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2library.init.events.attack.AttackCache;
import dev.xkmc.l2weaponry.content.item.types.HammerItem;
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

public class HolyAxe extends HammerItem implements LegendaryWeapon {

	public HolyAxe(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config) {
		super(tier, damage, speed, prop, config);
	}

	@Override
	public void onHurt(AttackCache event, LivingEntity le) {
		if (event.getCriticalHitEvent() != null && event.getStrength() < 0.9) return;
		float ans = le.getAbsorptionAmount();
		float health = event.getAttackTarget().getHealth();
		double max = LWConfig.COMMON.dogmaticStandoffMax.get();
		double inc = LWConfig.COMMON.dogmaticStandoffGain.get();
		ans = (float) Math.max(ans, Math.min(health * max, health * inc + ans));
		le.setAbsorptionAmount(ans);
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		int max = (int) Math.round(LWConfig.COMMON.dogmaticStandoffMax.get() * 100);
		int inc = (int) Math.round(LWConfig.COMMON.dogmaticStandoffGain.get() * 100);
		list.add(LangData.HOLY_AXE.get(inc, max));
	}

}
