package dev.xkmc.l2weaponry.events;

import dev.xkmc.l2library.init.events.attack.AttackCache;
import dev.xkmc.l2library.init.events.attack.AttackListener;
import dev.xkmc.l2weaponry.content.item.base.BaseClawItem;
import dev.xkmc.l2weaponry.content.item.base.GenericWeaponItem;
import dev.xkmc.l2weaponry.content.item.base.WeaponItem;
import net.minecraft.world.item.ItemStack;

public class LWAttackEventListener implements AttackListener {

	@Override
	public void onHurt(AttackCache cache, ItemStack weapon) {
		if (weapon.getItem() instanceof BaseClawItem claw) {
			claw.accumulateDamage(weapon, cache.getAttacker().getLevel().getGameTime());
		}
		if (!weapon.isEmpty() && weapon.getItem() instanceof GenericWeaponItem w) {
			cache.setDamageModified(cache.getStrength() < 0.7f ? 0.1f : cache.getDamageModified() * w.getMultiplier(cache));
		}
	}

}
