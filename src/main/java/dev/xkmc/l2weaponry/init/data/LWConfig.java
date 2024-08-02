package dev.xkmc.l2weaponry.init.data;

import dev.xkmc.l2core.util.ConfigInit;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class LWConfig {

	public static class Recipe extends ConfigInit {

		public final ModConfigSpec.BooleanValue defaultEnchantmentOnWeapons;

		public Recipe(Builder builder) {
			markL2();
			defaultEnchantmentOnWeapons = builder.comment("Default enchantments on crafted weapons")
					.define("defaultEnchantmentOnWeapons", true);
		}

	}

	public static class Server extends ConfigInit {

		public final ModConfigSpec.DoubleValue dagger_bonus;
		public final ModConfigSpec.DoubleValue claw_bonus;
		public final ModConfigSpec.IntValue claw_max;
		public final ModConfigSpec.IntValue claw_timeout;
		public final ModConfigSpec.IntValue claw_block_time;
		public final ModConfigSpec.DoubleValue reflectCost;
		public final ModConfigSpec.BooleanValue diggerEnchantmentOnWeapon;
		public final ModConfigSpec.ConfigValue<List<String>> extraCompatibleEnchantmentCategories;

		public final ModConfigSpec.IntValue shadowHunterDistance;
		public final ModConfigSpec.IntValue hauntingDemonDistance;
		public final ModConfigSpec.IntValue hammerOfIncarcerationRadius;
		public final ModConfigSpec.IntValue hammerOfIncarcerationDuration;
		public final ModConfigSpec.DoubleValue dogmaticStandoffGain;
		public final ModConfigSpec.DoubleValue dogmaticStandoffMax;
		public final ModConfigSpec.DoubleValue determinationRate;
		public final ModConfigSpec.DoubleValue illusionRate;

		public final ModConfigSpec.DoubleValue heavySpeedReduction;
		public final ModConfigSpec.DoubleValue heavyCritBonus;
		public final ModConfigSpec.DoubleValue stealthChance;
		public final ModConfigSpec.DoubleValue heavyShieldSpeedReduction;
		public final ModConfigSpec.DoubleValue heavyShieldDefenseBonus;
		public final ModConfigSpec.DoubleValue hardShieldDefenseBonus;
		public final ModConfigSpec.DoubleValue raisedSpiritSpeedBonus;
		public final ModConfigSpec.DoubleValue energizedWillReachBonus;
		public final ModConfigSpec.IntValue instantThrowCooldown;

		public final ModConfigSpec.DoubleValue knightmetalBonus;
		public final ModConfigSpec.DoubleValue knightmetalReflect;
		public final ModConfigSpec.DoubleValue fieryBonus;
		public final ModConfigSpec.IntValue fieryDuration;
		public final ModConfigSpec.DoubleValue steeleafBonus;
		public final ModConfigSpec.DoubleValue steeleafReflect;
		public final ModConfigSpec.DoubleValue steeleafChance;
		public final ModConfigSpec.IntValue ironwoodRegenDuration;
		public final ModConfigSpec.IntValue ironwoodEffectDuration;

		Server(ModConfigSpec.Builder builder) {
			builder.push("Weapon Type Features");
			dagger_bonus = builder.comment("Dagger damage multiplier when hitting targets not targeting user")
					.defineInRange("dagger_bonus", 2d, 1, 1000);
			claw_bonus = builder.comment("Claw damage bonus for each consecutive hit")
					.defineInRange("claw_bonus", 0.1d, 0, 10);
			claw_max = builder.comment("Claw damage bonus maximum hit for one claw (two claw has double maximum)")
					.defineInRange("claw_max", 5, 1, 1000);
			claw_timeout = builder.comment("Claw damage bonus timeout")
					.defineInRange("claw_timeout", 60, 1, 1000);
			claw_block_time = builder.comment("Claw block damage time")
					.defineInRange("claw_block_time", 3, 0, 1000);
			reflectCost = builder.comment("Shield reflect cost")
					.defineInRange("reflectCost", 0.2, 0, 1);
			diggerEnchantmentOnWeapon = builder.comment("Allow digger enchantments on weapon")
					.define("diggerEnchantmentOnWeapon", true);
			extraCompatibleEnchantmentCategories = builder
					.comment("List of enchantment categories for weapons. Use upper case enum name.")
					.comment("For modded enchantment categories, find them in their code through GitHub")
					.comment("Example: 'WEAPON', 'DIGGER'")
					.define("extraCompatibleEnchantmentCategories", new ArrayList<>(List.of()));

			builder.pop();
			builder.push("Legendary Weapon Effects");
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
			determinationRate = builder.comment("Claw of Determination increase rate")
					.defineInRange("determinationRate", 2d, 0, 100);
			illusionRate = builder.comment("Blade of illusion increase rate")
					.defineInRange("illusionRate", 1d, 0, 100);
			builder.pop();
			builder.push("Enchantments");
			heavySpeedReduction = builder.comment("Heavy enchantment reduction on attack speed")
					.defineInRange("heavySpeedReduction", 0.2, 0.0001, 100);
			heavyCritBonus = builder.comment("Heavy enchantment crit damage bonus")
					.defineInRange("heavyCritBonus", 0.3, 0.0001, 100);
			stealthChance = builder.comment("Stealth enchantment no aggro chance")
					.defineInRange("stealthChance", 0.2, 0.0001, 100);
			heavyShieldSpeedReduction = builder.comment("HeavyShield enchantment reduction on attack speed")
					.defineInRange("heavyShieldSpeedReduction", 0.1, 0.0001, 100);
			heavyShieldDefenseBonus = builder.comment("HeavyShield enchantment defense bonus")
					.defineInRange("heavyShieldDefenseBonus", 0.1, 0.0001, 100);
			hardShieldDefenseBonus = builder.comment("HardShield enchantment defense bonus")
					.defineInRange("hardShieldDefenseBonus", 0.05, 0.0001, 100);
			raisedSpiritSpeedBonus = builder.comment("Raised Spirit enchantment bonus on attack speed per stacking level per enchantment level")
					.defineInRange("raisedSpiritSpeedBonus", 0.01, 0.0001, 100);
			energizedWillReachBonus = builder.comment("Energized Will enchantment bonus on attack range per stacking level per enchantment level")
					.defineInRange("energizedWillReachBonus", 0.02, 0.0001, 100);
			instantThrowCooldown = builder.comment("Cooldown for Instant Throwing")
					.defineInRange("instantThrowCooldown", 60, 1, 6000);
			builder.pop();

			builder.push("Twilight Forest Compat");
			knightmetalBonus = builder.comment("Damage bonus to enemies with armor")
					.defineInRange("knightmetalBonus", 0.2, 0, 1000);
			knightmetalReflect = builder.comment("Extra damage reflection")
					.defineInRange("knightmetalReflect", 0.5, 0, 1000);
			fieryBonus = builder.comment("Damage bonus to enemies not immune to fire")
					.defineInRange("knightmetalBonus", 0.5, 0, 1000);
			fieryDuration = builder.comment("Ignite enemy by seconds")
					.defineInRange("fieryDuration", 15, 0, 1000);
			steeleafBonus = builder.comment("Damage bonus to enemies without armor")
					.defineInRange("steeleafBonus", 0.5, 0, 1000);
			steeleafReflect = builder.comment("Extra damage reflection")
					.defineInRange("steeleafReflect", 0.5, 0, 1000);
			steeleafChance = builder.comment("Effect Application Chance")
					.defineInRange("steeleafChance", 0.5, 0, 1000);
			ironwoodRegenDuration = builder.comment("Regeneration interval (in ticks)")
					.defineInRange("ironwoodRegenDuration", 100, 1, 10000);
			ironwoodEffectDuration = builder.comment("Resistance duration (in ticks)")
					.defineInRange("ironwoodEffectDuration", 100, 1, 10000);
			builder.pop();

		}

	}

	public static final Recipe RECIPE = L2Weaponry.REGISTRATE.registerUnsynced(Recipe::new);
	public static final Server SERVER = L2Weaponry.REGISTRATE.registerSynced(Server::new);

	public static void init() {
	}

}
