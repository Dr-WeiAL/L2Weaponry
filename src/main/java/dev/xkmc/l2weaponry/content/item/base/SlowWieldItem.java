package dev.xkmc.l2weaponry.content.item.base;

import com.google.common.collect.ImmutableMultimap;
import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SlowWieldItem extends GenericWeaponItem implements DoubleHandItem {

	public SlowWieldItem(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config, TagKey<Block> blocks) {
		super(tier, damage, speed, prop, config, blocks);
	}

	@Override
	protected void addModifiers(ImmutableMultimap.Builder<Attribute, AttributeModifier> builder) {
		float base_speed = 4 + this.attackSpeed;
		float raw_speed = base_speed + this.attackDamage;
		float reduce = 1f - Math.round(base_speed / raw_speed * 100) * 0.01f;
		this.attackSpeed += this.attackDamage;
		super.addModifiers(builder);
		AttributeModifier slow_2 = new AttributeModifier(MathHelper.getUUIDFromString("slow_wield"), "slow_wield", -reduce, AttributeModifier.Operation.MULTIPLY_TOTAL);
		builder.put(Attributes.ATTACK_SPEED, slow_2);
	}

	@Override
	public InteractionResult useOn(UseOnContext pContext) {
		return InteractionResult.CONSUME;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
		return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		ItemStack off = Proxy.getClientPlayer().getOffhandItem().copy();
		Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().offHandItem = off;
		off.getOrCreateTag().putBoolean("reequip",true);
		return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
	}

}
