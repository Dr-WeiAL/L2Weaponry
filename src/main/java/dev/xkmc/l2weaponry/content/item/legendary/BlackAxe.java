package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2damagetracker.contents.attack.CreateSourceEvent;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2damagetracker.contents.damage.DefaultDamageState;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.types.ThrowingAxeItem;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlackAxe extends ThrowingAxeItem implements LegendaryWeapon {

	public BlackAxe(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config);
	}

	@Override
	public void modifySource(LivingEntity attacker, CreateSourceEvent event, ItemStack item, @Nullable Entity target) {
		event.enable(DefaultDamageState.BYPASS_ARMOR);
	}

	@Override
	public void onHurt(DamageData.Offence event, LivingEntity le, ItemStack stack) {
		if (event.getStrength() < 0.9f) return;
		event.addHurtModifier(DamageModifier.addExtra((float) event.getTarget().getAttributeValue(Attributes.ARMOR), id()));
	}

	@Override
	public void appendHoverText(ItemStack pStack, TooltipContext pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.BLACK_AXE.get());
	}

}
