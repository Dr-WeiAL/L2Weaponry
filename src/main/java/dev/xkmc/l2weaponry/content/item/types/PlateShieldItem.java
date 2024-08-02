package dev.xkmc.l2weaponry.content.item.types;

import dev.xkmc.l2core.util.Proxy;
import dev.xkmc.l2damagetracker.contents.attack.CreateSourceEvent;
import dev.xkmc.l2damagetracker.contents.damage.DefaultDamageState;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.base.DoubleHandItem;
import dev.xkmc.l2weaponry.content.item.base.GenericShieldItem;
import dev.xkmc.l2weaponry.init.data.LangData;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlateShieldItem extends GenericShieldItem implements DoubleHandItem {

	private static final String NAME_ATTR = "shield_defense";
	private static final String NAME_KB = "shield_knockback";
	private static final String NAME_CRIT = "shield_crit";

	public PlateShieldItem(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config, false);
	}

	@Override
	public void modifySource(LivingEntity attacker, CreateSourceEvent event, ItemStack item, @Nullable Entity target) {
		event.enable(DefaultDamageState.BYPASS_ARMOR);
	}

	@Override
	public boolean disableOffHand(Player player, ItemStack stack) {
		return !lightWeight(stack);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		if (!lightWeight(newStack)) {
			ItemStack off = Proxy.getClientPlayer().getOffhandItem().copy();
			Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().offHandItem = off;
			LWItems.REEQUIP.set(off, Unit.INSTANCE);
		}
		return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
	}

	@Override
	public void appendHoverText(ItemStack pStack, TooltipContext pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.TOOL_PLATE_SHIELD.get());
		list.add(LangData.TOOL_PLATE_SHIELD_EXTRA.get());
		super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
	}

	@Override
	public double getDefenseRecover(ItemStack stack) {
		return 1 / 8f;
	}

}
