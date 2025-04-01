package dev.xkmc.l2weaponry.compat;

import dev.xkmc.l2damagetracker.contents.materials.api.IMatToolType;
import dev.xkmc.l2damagetracker.contents.materials.api.ITool;
import dev.xkmc.l2damagetracker.contents.materials.api.IToolStats;
import dev.xkmc.l2damagetracker.contents.materials.api.ToolConfig;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2damagetracker.contents.materials.vanilla.GenItemVanillaType;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;

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
		return this.tier.get().getUses();
	}

	public int speed() {
		return Math.round(this.tier.get().getSpeed());
	}

	public int enchant() {
		return this.tier.get().getEnchantmentValue();
	}

	@Override
	public void configure(ITool tool, ItemAttributeModifiers.Builder builder) {
		int dmg = tool.getDamage(Math.round(tier.get().getAttackDamageBonus()) + 4);
		float atkSpeed = tool.getAtkSpeed(1);
		tool.configure(builder, dmg, atkSpeed);
	}

}
