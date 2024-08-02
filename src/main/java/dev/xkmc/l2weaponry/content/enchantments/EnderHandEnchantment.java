package dev.xkmc.l2weaponry.content.enchantments;

import dev.xkmc.l2complements.content.enchantment.core.SourceModifierEnchantment;
import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import dev.xkmc.l2damagetracker.contents.attack.CreateSourceEvent;
import dev.xkmc.l2damagetracker.contents.attack.PlayerAttackCache;
import dev.xkmc.l2weaponry.init.data.LWNegateStates;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.ItemStack;

public class EnderHandEnchantment extends LegacyEnchantment implements SourceModifierEnchantment {

	@Override
	public void modify(CreateSourceEvent event, ItemStack stack, int value) {
		if (event.getOriginal().equals(DamageTypes.TRIDENT)) {
			event.enable(LWNegateStates.NO_PROJECTILE);
			event.setDirect(event.getAttacker());
			var cache = new PlayerAttackCache();
			cache.setupAttackerProfile(event.getAttacker(), stack);
			event.setPlayerAttackCache(cache);
		}
	}

}
