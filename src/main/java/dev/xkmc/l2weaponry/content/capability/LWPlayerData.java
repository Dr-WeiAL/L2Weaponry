package dev.xkmc.l2weaponry.content.capability;

import dev.xkmc.l2core.capability.player.PlayerCapabilityTemplate;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import dev.xkmc.l2weaponry.content.item.base.BaseShieldItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.registrate.LWEntities;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@SerialClass
public class LWPlayerData extends PlayerCapabilityTemplate<LWPlayerData> {

	public static LWPlayerData get(Player player) {
		return LWEntities.PLAYER.type().getOrCreate(player);
	}

	public static IShieldData asData(Player player) {
		return new Shield(player, get(player));
	}

	@SerialField
	private double shieldDefense = 0;
	@SerialField
	private int reflectTimer = 0;

	private double shieldRetain = 0;

	public void setShieldDefense(Player player, double shieldDefense) {
		this.shieldDefense = shieldDefense;
		if (player instanceof ServerPlayer sp)
			LWEntities.PLAYER.type().network.toClient(sp);
	}

	public double getRecoverRate(Player player) {
		ItemStack stack = player.getUseItem();
		double recover = 0.01;
		if (stack.getItem() instanceof BaseShieldItem shield) {
			recover *= shield.getDefenseRecover(stack);
		}
		return recover;
	}

	@Override
	public void tick(Player player) {
		double recover = getRecoverRate(player);
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

	public boolean canReflect(Player player) {
		return !player.isShiftKeyDown() && player.getAttributeValue(LWItems.REFLECT_TIME.holder()) > 0;
	}

	public void startReflectTimer(Player player) {
		if (!canReflect(player) || shieldDefense > 0) {
			return;
		}
		shieldDefense += LWConfig.SERVER.reflectCost.get();
		shieldRetain = shieldDefense * player.getAttributeValue(LWItems.SHIELD_DEFENSE.holder());
		reflectTimer = (int) player.getAttributeValue(LWItems.REFLECT_TIME.holder());
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

	private record Shield(Player player, LWPlayerData data) implements IShieldData {
		@Override
		public double getShieldDefense() {
			return data.shieldDefense;
		}

		@Override
		public void setShieldDefense(double i) {
			data.setShieldDefense(player, i);
		}

		@Override
		public int getReflectTimer() {
			return data.reflectTimer;
		}

		@Override
		public boolean canReflect() {
			return data.canReflect(player);
		}

		@Override
		public double popRetain() {
			return data.popRetain();
		}
	}

}
