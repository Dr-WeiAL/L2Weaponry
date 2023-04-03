package dev.xkmc.l2weaponry.compat;

import com.google.gson.JsonElement;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class BetterCombatDataGen implements DataProvider {

	private final DataGenerator generator;
	private final String folder_path;
	private final String name;
	private final Map<String, JsonElement> map = new HashMap<>();

	public BetterCombatDataGen(DataGenerator generator) {
		this.generator = generator;
		this.folder_path = "data/" + L2Weaponry.MODID + "/weapon_attributes/";
		this.name = "Better Combat Compat Config";
	}

	public void add(Map<String, JsonElement> map) {

	}

	public void run(CachedOutput cache) {
		Path folder = this.generator.getOutputFolder();
		this.add(this.map);
		this.map.forEach((k, v) -> {
			try {
				Path path = folder.resolve(this.folder_path + k + ".json");
				DataProvider.saveStable(cache, v, path);
			} catch (IOException var7) {
				var7.printStackTrace();
			}
		});
	}

	public String getName() {
		return this.name;
	}

}
