package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2library.init.events.attack.AttackCache;
import dev.xkmc.l2library.init.events.attack.DamageModifier;
import dev.xkmc.l2library.init.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.types.ClawItem;
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
	public void onDamageFinal(AttackCache cache, LivingEntity le) {
		if (cache.getStrength() < 0.95f) return;
		if (cache.getAttackTarget().hurtTime > 0) return;
		float diff = cache.getPreDamage() - cache.getDamageDealt();
		cache.getWeapon().getOrCreateTag().putUUID(KEY_TARGET, cache.getAttackTarget().getUUID());
		cache.getWeapon().getOrCreateTag().putFloat(KEY_DAMAGE, diff * 2);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.CHEATER_CLAW.get());
	}

	@Override
	public void onHurt(AttackCache event, LivingEntity le) {
		if (event.getWeapon().getOrCreateTag().hasUUID(KEY_TARGET) &&
				event.getAttackTarget().getUUID().equals(
						event.getWeapon().getOrCreateTag().getUUID(KEY_TARGET))) {
			event.addHurtModifier(DamageModifier.addPost(event.getWeapon().getOrCreateTag().getFloat(KEY_DAMAGE)));
		}
	}
}
