package dev.xkmc.l2weaponry.content.item.types;

import com.google.common.collect.ImmutableMultimap;
import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2weaponry.content.item.base.GenericWeaponItem;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.ForgeMod;

public class DaggerItem extends GenericWeaponItem {

	public static final AttributeModifier RANGE = new AttributeModifier(MathHelper.getUUIDFromString("dagger_reach"), "dagger_reach", -1, AttributeModifier.Operation.ADDITION);

	public DaggerItem(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config) {
		super(tier, damage, speed, prop, config);
	}

	@Override
	protected void addModifiers(ImmutableMultimap.Builder<Attribute, AttributeModifier> builder) {
		super.addModifiers(builder);
		builder.put(ForgeMod.ATTACK_RANGE.get(), RANGE);
	}

}
