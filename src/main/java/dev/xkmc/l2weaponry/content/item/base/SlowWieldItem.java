package dev.xkmc.l2weaponry.content.item.base;

import com.google.common.collect.ImmutableMultimap;
import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;

public class SlowWieldItem extends GenericWeaponItem {

	public SlowWieldItem(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config, TagKey<Block> blocks) {
		super(tier, damage, speed, prop, config, blocks);
	}

	@Override
	protected void addModifiers(ImmutableMultimap.Builder<Attribute, AttributeModifier> builder) {
		float base_speed = this.attackSpeed;
		float raw_speed = this.attackSpeed + this.attackDamage;
		float reduce = 1f - Math.round(base_speed / raw_speed * 10) * 0.1f;
		this.attackSpeed += this.attackDamage;
		super.addModifiers(builder);
		AttributeModifier slow_2 = new AttributeModifier(MathHelper.getUUIDFromString("slow_wield"), "slow_wield", -reduce, AttributeModifier.Operation.MULTIPLY_TOTAL);
		builder.put(Attributes.ATTACK_SPEED, slow_2);
	}

}
