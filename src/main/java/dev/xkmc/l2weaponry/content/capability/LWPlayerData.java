package dev.xkmc.l2weaponry.content.capability;

import dev.xkmc.l2library.capability.player.PlayerCapabilityHolder;
import dev.xkmc.l2library.capability.player.PlayerCapabilityNetworkHandler;
import dev.xkmc.l2library.capability.player.PlayerCapabilityTemplate;
import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2weaponry.content.item.base.BaseShieldItem;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

@SerialClass
public class LWPlayerData extends PlayerCapabilityTemplate<LWPlayerData> implements IShieldData {

	public static final Capability<LWPlayerData> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
	});

	public static final PlayerCapabilityHolder<LWPlayerData> HOLDER = new PlayerCapabilityHolder<>(
			new ResourceLocation(L2Weaponry.MODID, "main"),
			CAPABILITY, LWPlayerData.class, LWPlayerData::new,
			PlayerCapabilityNetworkHandler::new);

	public static void register() {
	}

	@SerialClass.SerialField
	private double shieldDefense = 0;

	public double getShieldDefense() {
		return shieldDefense;
	}

	public void setShieldDefense(double shieldDefense) {
		this.shieldDefense = shieldDefense;
		if (player instanceof ServerPlayer sp)
			HOLDER.network.toClientSyncAll(sp);
	}

	public double getRecoverRate() {
		ItemStack stack = player.getUseItem();
		double recover = 0.01;
		if (stack.getItem() instanceof BaseShieldItem shield) {
			recover *= shield.getDefenseRecover(stack);
		}
		return recover;
	}

	@Override
	public void tick() {
		double recover = getRecoverRate();
		if (shieldDefense > 0) {
			shieldDefense -= recover;
			if (shieldDefense < 0) {
				shieldDefense = 0;
			}
		}
	}
}
