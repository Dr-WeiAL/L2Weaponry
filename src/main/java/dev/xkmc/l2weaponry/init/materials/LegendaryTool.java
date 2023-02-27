package dev.xkmc.l2weaponry.init.materials;

import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2complements.init.materials.api.ITool;
import dev.xkmc.l2library.util.code.Wrappers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

public record LegendaryTool<T extends Item>(LWToolTypes type, LegendaryToolFactory<T> tool) implements ITool {

	public T parse(LWToolMats mat, Item.Properties p) {
		return Wrappers.cast(mat.type.getToolConfig().sup().get(mat.type, this, p));
	}

	@Override
	public int getDamage(int i) {
		return type.getDamage(i);
	}

	@Override
	public float getSpeed(float v) {
		return type.getSpeed(v);
	}

	@Override
	public Item create(Tier tier, int i, float v, Item.Properties properties, ExtraToolConfig extraToolConfig) {
		return tool.get(tier, i, v, properties, extraToolConfig);
	}

}
