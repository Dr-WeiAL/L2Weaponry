package dev.xkmc.l2weaponry.content.item.base;

import net.minecraft.world.entity.player.Player;

public interface DoubleHandItem {

	default boolean disableOffHand(Player player) {
		return true;
	}

}
