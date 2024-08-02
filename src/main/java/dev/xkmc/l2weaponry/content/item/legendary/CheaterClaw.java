package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.types.ClawItem;
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

public class CheaterClaw extends ClawItem implements LegendaryWeapon {

	private static final String KEY_TARGET = "target", KEY_DAMAGE = "damage_bonus";

	public CheaterClaw(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config) {
		super(tier, damage, speed, prop, config);
	}

	@Override
	public void onDamageFinal(DamageData.DefenceMax cache, LivingEntity le) {
		if (cache.getStrength() < 0.95f) return;
		if (cache.getTarget().hurtTime > 0) return;
		float diff = cache.getDamageIncoming() - cache.getDamageFinal();
		cache.getWeapon().getOrCreateTag().putUUID(KEY_TARGET, cache.getAttackTarget().getUUID());
		double rate = LWConfig.SERVER.determinationRate.get();
		cache.getWeapon().getOrCreateTag().putFloat(KEY_DAMAGE, diff * (float) rate);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.CHEATER_CLAW.get());
	}

	@Override
	public void onHurt(DamageData.Offence event, LivingEntity le, ItemStack stack) {
		if (stack.getOrCreateTag().hasUUID(KEY_TARGET) &&
				event.getAttackTarget().getUUID().equals(
						stack.getOrCreateTag().getUUID(KEY_TARGET))) {
			event.addHurtModifier(DamageModifier.addExtra(stack.getOrCreateTag().getFloat(KEY_DAMAGE)));
		}
	}
}
