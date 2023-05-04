package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2library.init.events.attack.AttackCache;
import dev.xkmc.l2library.init.events.attack.DamageModifier;
import dev.xkmc.l2library.init.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.types.MacheteItem;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CheaterMachete extends MacheteItem implements LegendaryWeapon {

	private static final String KEY_TARGET = "target", KEY_DAMAGE = "accumulated_damage";

	public CheaterMachete(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config) {
		super(tier, damage, speed, prop, config);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.CHEATER_MACHETE.get());
	}

	@Override
	public void onHurt(AttackCache cache, LivingEntity le) {
		var ctag = cache.getWeapon().getOrCreateTag();
		if (ctag.hasUUID(KEY_TARGET) && cache.getAttackTarget().getUUID().equals(ctag.getUUID(KEY_TARGET))) {
			float lost = cache.getAttackTarget().getMaxHealth() - cache.getAttackTarget().getHealth();
			float acc = ctag.getFloat(KEY_DAMAGE);
			if (lost < acc) {
				cache.addHurtModifier(DamageModifier.addPost(acc - lost));
			}
		}
	}

	@Override
	public void onHurtMaximized(AttackCache cache, LivingEntity le) {
		var ctag = cache.getWeapon().getOrCreateTag();
		if (ctag.hasUUID(KEY_TARGET) && cache.getAttackTarget().getUUID().equals(ctag.getUUID(KEY_TARGET))) {
			float damage = ctag.getFloat(KEY_DAMAGE);
			ctag.putFloat(KEY_DAMAGE, damage + cache.getPreDamage());
		} else {
			ctag.putFloat(KEY_DAMAGE, cache.getPreDamage());
		}
		ctag.putUUID(KEY_TARGET, cache.getAttackTarget().getUUID());

	}
}
