package dev.xkmc.l2weaponry.compat;

import com.doo.xenchantment.enchantment.IncDamage;
import dev.xkmc.l2weaponry.content.item.base.WeaponItem;

public class XEnchCompat {

	public static void onInit(){
		IncDamage.DAMAGE_GETTER.add(stack -> stack.getItem() instanceof WeaponItem weapon ? weapon.attackDamage : null);
	}

}
