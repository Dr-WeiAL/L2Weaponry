package dev.xkmc.l2weaponry.content.item.types;

import com.google.common.collect.ImmutableMultimap;
import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2weaponry.content.item.base.GenericWeaponItem;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpearItem extends GenericWeaponItem {

	public static final AttributeModifier RANGE = new AttributeModifier(MathHelper.getUUIDFromString("spear_reach"), "spear_reach", 2, AttributeModifier.Operation.ADDITION);

	public SpearItem(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config) {
		super(tier, damage, speed, prop, config, BlockTags.MINEABLE_WITH_SHOVEL);
	}

	@Override
	protected void addModifiers(ImmutableMultimap.Builder<Attribute, AttributeModifier> builder) {
		super.addModifiers(builder);
		builder.put(ForgeMod.ATTACK_RANGE.get(), RANGE);
	}

	@Override
	protected boolean canSweep() {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.TOOL_SPEAR.get());
		super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
	}

	@Override
	public @NotNull AABB getSweepHitBox(@NotNull ItemStack stack, @NotNull Player player, @NotNull Entity target) {
		return target.getBoundingBox().inflate(3.0D, 0.5D, 3.0D);
	}

}
