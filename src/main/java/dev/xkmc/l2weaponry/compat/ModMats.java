package dev.xkmc.l2weaponry.compat;

import dev.xkmc.l2damagetracker.contents.materials.api.IMatToolType;
import dev.xkmc.l2damagetracker.contents.materials.api.ITool;
import dev.xkmc.l2damagetracker.contents.materials.api.IToolStats;
import dev.xkmc.l2damagetracker.contents.materials.api.ToolConfig;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2damagetracker.contents.materials.vanilla.GenItemVanillaType;
import net.minecraft.world.item.Tier;

import java.util.function.Supplier;

public record ModMats(Supplier<Tier> tier, ExtraToolConfig config) implements IMatToolType, IToolStats {

	public ModMats(Tier tier, ExtraToolConfig config) {
		this(() -> tier, config);
	}

	@Override
	public Tier getTier() {
		return tier.get();
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
		return this.getTier().getUses();
	}

	public int speed() {
		return Math.round(this.getTier().getSpeed());
	}

	public int enchant() {
		return this.getTier().getEnchantmentValue();
	}

	public int getDamage(ITool tool) {
		return tool.getDamage(Math.round(this.getTier().getAttackDamageBonus()) + 4);
	}

	public float getSpeed(ITool tool) {
		return tool.getSpeed(1.0F);
	}

}
