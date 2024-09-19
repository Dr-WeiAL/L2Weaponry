package dev.xkmc.l2weaponry.compat;

import dev.xkmc.l2damagetracker.contents.materials.api.*;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2damagetracker.contents.materials.vanilla.GenItemVanillaType;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public record ModMats(Tier tier, ExtraToolConfig config) implements IMatToolType, IToolStats {

	@Override
	public Tier getTier() {
		return tier;
	}

	@Override
	public IToolStats getToolStats() {
		return this;
	}

	@Override
	public ToolConfig getToolConfig() {
		return GenItemVanillaType.TOOL_GEN;
	}

	@Override
	public ExtraToolConfig getExtraToolConfig() {
		return config;
	}


	public int durability() {
		return this.tier.getUses();
	}

	public int speed() {
		return Math.round(this.tier.getSpeed());
	}

	public int enchant() {
		return this.tier.getEnchantmentValue();
	}

	@Override
	public void configure(ITool tool, ItemAttributeModifiers.Builder builder) {
		int dmg = tool.getDamage(Math.round(tier.getAttackDamageBonus()) + 4);
		float atkSpeed = tool.getAtkSpeed(1);
		tool.configure(builder, dmg, atkSpeed);
	}

}
