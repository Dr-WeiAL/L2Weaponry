package dev.xkmc.l2weaponry.content.item.types;

import com.google.common.collect.ImmutableMultimap;
import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2library.init.events.attack.AttackCache;
import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2weaponry.content.item.base.DoubleHandItem;
import dev.xkmc.l2weaponry.content.item.base.GenericShieldItem;
import dev.xkmc.l2weaponry.init.data.LangData;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlateShieldItem extends GenericShieldItem implements DoubleHandItem {

	private static final String NAME_ATTR = "shield_defense";
	private static final String NAME_KB = "shield_knockback";

	public PlateShieldItem(Tier tier, int maxDefense, float recover, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config, maxDefense, recover, false);
	}

	@Override
	protected void buildAttributes(ImmutableMultimap.Builder<Attribute, AttributeModifier> builder) {
		super.buildAttributes(builder);
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", Math.round(maxDefense * 0.035), AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -3, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(MathHelper.getUUIDFromString(NAME_KB), NAME_KB, 4, AttributeModifier.Operation.ADDITION));
		builder.put(LWItems.REFLECT_TIME.get(), new AttributeModifier(MathHelper.getUUIDFromString(NAME_ATTR), NAME_ATTR, 20, AttributeModifier.Operation.ADDITION));
	}

	@Override
	public float getMultiplier(AttackCache cache) {
		LivingHurtEvent event = cache.getLivingHurtEvent();
		assert event != null;
		event.getSource().bypassArmor();
		return 1;
	}

	@Override
	public boolean disableOffHand(Player player, ItemStack stack) {
		return !lightWeight(stack);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		if (!lightWeight(newStack)) {
			ItemStack off = Proxy.getClientPlayer().getOffhandItem().copy();
			Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().offHandItem = off;
			off.getOrCreateTag().putBoolean("reequip", true);
		}
		return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.TOOL_PLATE_SHIELD.get());
		list.add(LangData.TOOL_PLATE_SHIELD_EXTRA.get());
		super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
	}

}
