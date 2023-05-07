package dev.xkmc.l2weaponry.network;

import dev.xkmc.l2library.serial.config.PacketHandler;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.minecraft.resources.ResourceLocation;

public class NetworkManager {

	public static final PacketHandler HANDLER = new PacketHandler(
			new ResourceLocation(L2Weaponry.MODID, "main"), 1
	);

	public static void register() {
	}

}
