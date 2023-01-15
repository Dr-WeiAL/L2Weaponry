package dev.xkmc.l2weaponry.events;

import dev.xkmc.l2library.init.events.attack.AttackCache;
import dev.xkmc.l2library.init.events.attack.AttackListener;
import dev.xkmc.l2weaponry.content.item.base.GenericWeaponItem;
import net.minecraft.world.item.ItemStack;

public class LWAttackEventListener implements AttackListener {

	@Override
	public void onHurt(AttackCache cache, ItemStack weapon) {
		if (!weapon.isEmpty() && weapon.getItem() instanceof GenericWeaponItem w) {
			cache.setDamageModified(cache.getDamageModified() * w.getMultiplier(cache));
		}
	}

}
