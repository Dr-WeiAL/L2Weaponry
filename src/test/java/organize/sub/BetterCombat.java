package organize.sub;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import organize.ResourceOrganizer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Locale;

public class BetterCombat extends ResourceOrganizer {

	public enum BCToolMats {
		IRON, GOLD, DIAMOND, NETHERITE,
		TOTEMIC_GOLD, POSEIDITE, SHULKERATE, SCULKIUM, ETERNIUM
	}

	public enum BCToolTypes {
		CLAW, DAGGER, HAMMER, BATTLE_AXE, SPEAR, MACHETE, THROWING_AXE, JAVELIN
	}

	public enum BCLegendary {
		axe_of_cursed_flame(BCToolTypes.BATTLE_AXE, 0),
		hammer_of_incarceration(BCToolTypes.HAMMER, 0),
		haunting_demon_of_the_end(BCToolTypes.SPEAR, 1),
		poseidon_madness(BCToolTypes.SPEAR, 0),
		shadow_hunter(BCToolTypes.DAGGER, 1),
		spear_of_winter_storm(BCToolTypes.SPEAR, 0),
		void_escape(BCToolTypes.JAVELIN, 1);

		private final BCToolTypes type;
		private final int range;

		BCLegendary(BCToolTypes type, int range) {
			this.type = type;
			this.range = range;
		}
	}

	public BetterCombat() {
		super(Type.DATA, "better_combat", "weapon_attributes");
	}

	@Override
	public void organize(File f) throws Exception {
		for (BCToolTypes type : BCToolTypes.values()) {
			File fi = f.toPath().resolve(type.toString().toLowerCase(Locale.ROOT) + ".json").toFile();
			JsonObject json = new JsonParser().parse(new FileReader(fi.getPath())).getAsJsonObject();
			for (BCToolMats mat : BCToolMats.values()) {
				JsonObject copy = json.deepCopy();
				if (mat == BCToolMats.SHULKERATE) {
					double val = copy.getAsJsonObject("attributes").get("attack_range").getAsDouble();
					copy.getAsJsonObject("attributes").addProperty("attack_range", val + 1);
				}
				File target = new File(getTargetFolder() + "/" + mat.name().toLowerCase(Locale.ROOT) + "_" + type.name().toLowerCase(Locale.ROOT) + ".json");
				check(target);
				FileWriter w = new FileWriter(target);
				w.write(GSON.toJson(copy));
				w.close();
			}
		}
		for (BCLegendary legendary : BCLegendary.values()) {
			File fi = f.toPath().resolve(legendary.type.toString().toLowerCase(Locale.ROOT) + ".json").toFile();
			JsonObject json = new JsonParser().parse(new FileReader(fi.getPath())).getAsJsonObject();
			JsonObject copy = json.deepCopy();
			double val = copy.getAsJsonObject("attributes").get("attack_range").getAsDouble();
			copy.getAsJsonObject("attributes").addProperty("attack_range", val + legendary.range);
			File target = new File(getTargetFolder() + "/" + legendary.name().toLowerCase(Locale.ROOT) + ".json");
			check(target);
			FileWriter w = new FileWriter(target);
			w.write(GSON.toJson(copy));
			w.close();
		}
	}

}
