package dev.xkmc.l2weaponry.init.data;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

public class LWConfig {

	public static class Client {

		Client(ForgeConfigSpec.Builder builder) {
		}

	}

	public static class Common {

		public final ForgeConfigSpec.DoubleValue dagger_bonus;
		public final ForgeConfigSpec.DoubleValue claw_bonus;
		public final ForgeConfigSpec.IntValue claw_max;
		public final ForgeConfigSpec.IntValue claw_timeout;
		public final ForgeConfigSpec.DoubleValue reflectCost;

		public final ForgeConfigSpec.IntValue shadowHunterDistance;
		public final ForgeConfigSpec.IntValue hauntingDemonDistance;
		public final ForgeConfigSpec.IntValue hammerOfIncarcerationRadius;
		public final ForgeConfigSpec.IntValue hammerOfIncarcerationDuration;
		public final ForgeConfigSpec.DoubleValue dogmaticStandoffGain;
		public final ForgeConfigSpec.DoubleValue dogmaticStandoffMax;

		Common(ForgeConfigSpec.Builder builder) {
			dagger_bonus = builder.comment("Dagger damage multiplier when hitting targets not targeting user")
					.defineInRange("dagger_bonus", 2d, 1, 1000);
			claw_bonus = builder.comment("Claw damage bonus for each consecutive hit")
					.defineInRange("claw_bonus", 0.1d, 0, 10);
			claw_max = builder.comment("Claw damage bonus maximum hit for one claw (two claw has double maximum)")
					.defineInRange("claw_max", 5, 1, 1000);
			claw_timeout = builder.comment("Claw damage bonus timeout")
					.defineInRange("claw_timeout", 60, 1, 1000);
			reflectCost = builder.comment("Shield reflect cost")
					.defineInRange("reflectCost", 0.2, 0, 1);
			shadowHunterDistance = builder.comment("Shadow Hunter teleport distance")
					.defineInRange("shadowHunterDistance", 8, 1, 128);
			hauntingDemonDistance = builder.comment("Haunting Demon of the End teleport distance")
					.defineInRange("hauntingDemonDistance", 64, 1, 128);
			hammerOfIncarcerationRadius = builder.comment("Hammer of Incarceration effect radius")
					.defineInRange("hammerOfIncarcerationRadius", 8, 1, 64);
			hammerOfIncarcerationDuration = builder.comment("Hammer of Incarceration effect duration")
					.defineInRange("hammerOfIncarcerationDuration", 60, 1, 60000);
			dogmaticStandoffGain = builder.comment("Dogmatic Standoff absorption gain percentage")
					.defineInRange("dogmaticStandoffGain", 0.02, 0.0001, 1);
			dogmaticStandoffMax = builder.comment("Dogmatic Standoff absorption max percentage")
					.defineInRange("dogmaticStandoffMax", 0.1, 0.0001, 100);
		}

	}

	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	static {
		final Pair<Client, ForgeConfigSpec> client = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = client.getRight();
		CLIENT = client.getLeft();

		final Pair<Common, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = common.getRight();
		COMMON = common.getLeft();
	}

	/**
	 * Registers any relevant listeners for config
	 */
	public static void init() {
		ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.CLIENT, LWConfig.CLIENT_SPEC);
		ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, LWConfig.COMMON_SPEC);
	}


}
