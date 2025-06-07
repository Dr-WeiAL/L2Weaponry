package dev.xkmc.l2weaponry.compat;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.iafenvoy.iceandfire.IceAndFire;
import dev.xkmc.l2weaponry.compat.aerial.AHCompat;
import dev.xkmc.l2weaponry.compat.cataclysm.CataCompat;
import dev.xkmc.l2weaponry.compat.dragons.DragonCompat;
import dev.xkmc.l2weaponry.compat.twilightforest.TFCompat;
import dev.xkmc.l2weaponry.compat.undergarden.UGCompat;
import dev.xkmc.l2weaponry.init.materials.ILWToolMats;
import fr.factionbedrock.aerialhell.AerialHell;
import net.minecraftforge.fml.ModList;
import quek.undergarden.Undergarden;
import twilightforest.TwilightForestMod;

import java.util.ArrayList;
import java.util.List;

public abstract class CompatDispatch {

	public static final List<CompatDispatch> LIST = new ArrayList<>();

	public static void register() {
		if (ModList.get().isLoaded(TwilightForestMod.ID)) new TFCompat();
		if (ModList.get().isLoaded(Undergarden.MODID)) new UGCompat();
		if (ModList.get().isLoaded(IceAndFire.MOD_ID)) new DragonCompat();
		//if (ModList.get().isLoaded("allthemodium")) new ATMCompat();
		if (ModList.get().isLoaded(Cataclysm.MODID)) new CataCompat();
		if (ModList.get().isLoaded(AerialHell.MODID)) new AHCompat();
	}

	public CompatDispatch() {
		synchronized (LIST) {
			LIST.add(this);
		}
	}

	public abstract ILWToolMats[] values();

}
