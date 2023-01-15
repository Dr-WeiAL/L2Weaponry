package dev.xkmc.l2weaponry.init.materials;

import dev.xkmc.l2complements.init.materials.LCMats;
import dev.xkmc.l2complements.init.materials.api.IMatToolType;
import net.minecraft.world.item.Tiers;

public enum LWToolMats {
	IRON(new VanillaMats(Tiers.IRON)),
	GOLD(new VanillaMats(Tiers.GOLD)),
	DIAMOND(new VanillaMats(Tiers.DIAMOND)),
	NETHERITE(new VanillaMats(Tiers.NETHERITE)),
	TOTEMIC_GOLD(LCMats.TOTEMIC_GOLD),
	POSEIDITE(LCMats.POSEIDITE),
	SHULKERATE(LCMats.SHULKERATE),
	SCULKIUM(LCMats.SCULKIUM),
	ETERNIUM(LCMats.ETERNIUM);

	public final IMatToolType type;

	LWToolMats(IMatToolType type) {
		this.type = type;
	}

}
