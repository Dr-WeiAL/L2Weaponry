package dev.xkmc.l2weaponry.content.capability;

import dev.xkmc.l2library.capability.player.PlayerCapabilityHolder;
import dev.xkmc.l2library.capability.player.PlayerCapabilityNetworkHandler;
import dev.xkmc.l2library.capability.player.PlayerCapabilityTemplate;
import dev.xkmc.l2serial.serialization.SerialClass;
import dev.xkmc.l2weaponry.content.item.base.BaseShieldItem;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
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
	@SerialClass.SerialField
	private int reflectTimer = 0;

	private double shieldRetain = 0;

	public double getShieldDefense() {
		return shieldDefense;
	}

	public void setShieldDefense(double shieldDefense) {
		this.shieldDefense = shieldDefense;
		if (player instanceof ServerPlayer sp)
			HOLDER.network.toClientSyncAll(sp);
	}

	@Override
	public int getReflectTimer() {
		return reflectTimer;
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
		if (reflectTimer > 0) {
			reflectTimer--;
			if (reflectTimer == 0) {
				shieldRetain = 0;
			}
		}
	}

	public boolean canReflect() {
		return !player.isShiftKeyDown() && player.onGround() && player.getAttributeValue(LWItems.REFLECT_TIME.get()) > 0;
	}

	public void startReflectTimer() {
		if (!canReflect() || shieldDefense > 0) {
			return;
		}
		shieldDefense += LWConfig.COMMON.reflectCost.get();
		shieldRetain = shieldDefense * player.getAttributeValue(LWItems.SHIELD_DEFENSE.get());
		reflectTimer = (int) player.getAttributeValue(LWItems.REFLECT_TIME.get());
	}

	public void clearReflectTimer() {
		reflectTimer = 0;
		shieldRetain = 0;
	}

	public double popRetain() {
		double ans = shieldRetain;
		shieldRetain = 0;
		reflectTimer = 0;
		return ans;
	}

}
