package dev.xkmc.l2weaponry.content.item.types;

import com.google.common.collect.ImmutableMultimap;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2weaponry.content.item.base.SlowWieldItem;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ScytheItem extends SlowWieldItem {

	public static final AttributeModifier RANGE = new AttributeModifier(MathHelper.getUUIDFromString("scythe_range"), "scythe_range", 1, AttributeModifier.Operation.ADDITION);
	public static final AttributeModifier REACH = new AttributeModifier(MathHelper.getUUIDFromString("scythe_reach"), "scythe_reach", 1, AttributeModifier.Operation.ADDITION);

	public ScytheItem(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config) {
		super(tier, damage, speed, prop, config, BlockTags.MINEABLE_WITH_HOE);
	}

	@Override
	protected void addModifiers(ImmutableMultimap.Builder<Attribute, AttributeModifier> builder) {
		super.addModifiers(builder);
		builder.put(ForgeMod.ENTITY_REACH.get(), RANGE);
		builder.put(ForgeMod.BLOCK_REACH.get(), REACH);
	}

	@Override
	public boolean canSweep() {
		return true;
	}

	@Override
	public AABB getSweepHitBoxImpl(ItemStack stack, LivingEntity player, Entity target) {
		double r = player.getAttributeValue(ForgeMod.ENTITY_REACH.get());
		return player.getBoundingBox().inflate(r + 1, r + 0.25, r + 1);
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.TOOL_SCYTHE.get());
		super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
	}

}
