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
	}

}
