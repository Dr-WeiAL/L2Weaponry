package dev.xkmc.l2weaponry.events;

import dev.xkmc.l2library.init.events.attack.AttackCache;
import dev.xkmc.l2library.init.events.attack.AttackListener;
import dev.xkmc.l2weaponry.content.item.base.BaseClawItem;
import dev.xkmc.l2weaponry.content.item.base.DoubleWieldItem;
import dev.xkmc.l2weaponry.content.item.base.GenericWeaponItem;
import dev.xkmc.l2weaponry.content.item.base.WeaponItem;
import dev.xkmc.l2weaponry.content.item.types.ClawItem;
import net.minecraft.world.item.ItemStack;

public class LWAttackEventListener implements AttackListener {

	@Override
	public void onPlayerAttack(AttackCache cache) {
		var event = cache.getPlayerAttackEntityEvent();
		assert event != null;
		float prog = cache.getStrength();
		if (event.getEntity().getMainHandItem().getItem() instanceof WeaponItem && prog < 0.95f) {
			event.setCanceled(true);
		}
	}

	@Override
	public void onHurt(AttackCache cache, ItemStack weapon) {
		if (weapon.getItem() instanceof BaseClawItem claw) {
			claw.accumulateDamage(weapon, cache.getAttacker().getLevel().getGameTime());
		}
		if (!weapon.isEmpty() && weapon.getItem() instanceof GenericWeaponItem w) {
			cache.setDamageModified(cache.getDamageModified() * w.getMultiplier(cache));
		}
	}

}
