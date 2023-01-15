package dev.xkmc.l2weaponry.content.item.types;

import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2library.init.events.attack.AttackCache;
import dev.xkmc.l2weaponry.content.item.base.SlowWieldItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class HammerItem extends SlowWieldItem {

	public HammerItem(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config) {
		super(tier, damage, speed, prop, config, BlockTags.MINEABLE_WITH_AXE);
	}

	@Override
	protected boolean isSharp() {
		return false;
	}

	@Override
	public float getMultiplier(AttackCache cache) {
		LivingHurtEvent event = cache.getLivingHurtEvent();
		assert event != null;
		event.getSource().bypassArmor();
		return 1;
	}

}
