package dev.xkmc.l2weaponry.compat;

import dev.xkmc.l2damagetracker.contents.materials.api.IMatToolType;
import dev.xkmc.l2damagetracker.contents.materials.api.ITool;
import dev.xkmc.l2damagetracker.contents.materials.api.IToolStats;
import dev.xkmc.l2damagetracker.contents.materials.api.ToolConfig;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Unbreakable;

import java.util.function.Supplier;

public record ModMats(
		Supplier<Tier> tier, ExtraToolConfig config, boolean unbreakable
) implements IMatToolType, IToolStats {

	public ModMats(Tier tier, ExtraToolConfig config) {
		this(() -> tier, config, false);
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
		return new ToolConfig(this::genGenericTool);
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

	private Item genGenericTool(IMatToolType mat, ITool tool, Item.Properties prop) {
		if (unbreakable) prop.component(DataComponents.UNBREAKABLE, new Unbreakable(true));
		var builder = ItemAttributeModifiers.builder();
		mat.getToolStats().configure(tool, builder);
		mat.getExtraToolConfig().configureAttributes(builder);
		prop.attributes(builder.build());
		return tool.create(mat.getTier(), prop, mat.getExtraToolConfig());
	}

}
