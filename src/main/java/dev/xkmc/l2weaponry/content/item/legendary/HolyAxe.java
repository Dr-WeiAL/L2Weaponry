package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.types.BattleAxeItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class HolyAxe extends BattleAxeItem implements LegendaryWeapon {

	public HolyAxe(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config);
	}

	@Override
	public void onHurt(DamageData.Offence event, LivingEntity le, ItemStack stack) {
		if (event.getStrength() < 0.9) return;
		float ans = le.getAbsorptionAmount();
		float health = event.getTarget().getHealth();
		if (health > 0) {
			var ins = le.getAttribute(Attributes.MAX_ABSORPTION);
			if (ins == null) return;
			double max = LWConfig.SERVER.dogmaticStandoffMax.get();
			double inc = LWConfig.SERVER.dogmaticStandoffGain.get();
			ins.addOrReplacePermanentModifier(new AttributeModifier(id(), health * max,
					AttributeModifier.Operation.ADD_VALUE));
			le.setAbsorptionAmount((float) (ans + health * inc));
		}
	}

	@Override
	public void appendHoverText(ItemStack pStack, TooltipContext pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		int max = (int) Math.round(LWConfig.SERVER.dogmaticStandoffMax.get() * 100);
		int inc = (int) Math.round(LWConfig.SERVER.dogmaticStandoffGain.get() * 100);
		list.add(LangData.HOLY_AXE.get(inc, max));
	}

}
