package dev.xkmc.l2weaponry.init.materials;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class LWItemModelProvider extends RegistrateItemModelProvider {

	public LWItemModelProvider(AbstractRegistrate<?> parent, PackOutput output, ExistingFileHelper helper) {
		super(parent, new PackOutput(output.getOutputFolder()
				.resolve("resourcepacks")
				.resolve("old_weapon_model")), helper);
	}

	@Override
	protected void clear() {

	}

	@Override
	protected void registerModels() {

	}

}
