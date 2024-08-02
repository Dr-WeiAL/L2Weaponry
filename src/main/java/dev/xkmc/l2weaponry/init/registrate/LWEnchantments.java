package dev.xkmc.l2weaponry.init.registrate;

import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2core.init.reg.ench.EnchColor;
import dev.xkmc.l2core.init.reg.ench.EnchReg;
import dev.xkmc.l2core.init.reg.ench.EnchVal;
import dev.xkmc.l2weaponry.content.enchantments.*;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import dev.xkmc.l2weaponry.init.data.TagGen;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantments;

public class LWEnchantments {

	public static final EnchReg REG = EnchReg.of(L2Weaponry.REG, L2Weaponry.REGISTRATE);

	public static final EnchVal.Legacy<EnderHandEnchantment> ENDER_HAND;
	public static final EnchVal PROJECTION; // exclude loyalty
	public static final EnchVal INSTANT_THROWING;
	public static final EnchVal.Legacy<StealthEnchantment> NO_AGGRO;
	public static final EnchVal.Legacy<HeavyEnchantment> HEAVY;
	public static final EnchVal.Legacy<HardShieldEnchantment> HARD_SHIELD;
	public static final EnchVal.Legacy<HeavyShieldEnchantment> HEAVY_SHIELD;
	public static final EnchVal.Legacy<EnergizedWillEnchantment> ENERGIZED_WILL;
	public static final EnchVal.Legacy<RaisedSpiritEnchantment> RAISED_SPIRIT;
	public static final EnchVal GHOST_SLASH;
	public static final EnchVal CLAW_BLOCK;


	static {
		var green = new EnchColor(ChatFormatting.GREEN, ChatFormatting.GRAY);
		var gold = new EnchColor(ChatFormatting.GOLD, ChatFormatting.GRAY);
		var purple = new EnchColor(ChatFormatting.LIGHT_PURPLE, ChatFormatting.GRAY);
		var craft = new LCEnchantments.Order();
		int col = 0xff7f4f6f;

		ENDER_HAND = REG.enchLegacy("ender_hand", "Ender Hand",
				"Thrown attacks will appear as direct hit.",
				e -> e.items(TagGen.THROWABLE)
						.color(green).special(LCEnchantments.CRAFT, craft.of(col))
				, EnderHandEnchantment::new);

		PROJECTION = REG.ench("projection", "Projection",
				"Thrown attacks will not consume the used weapon",
				e -> e.items(TagGen.THROWABLE).exclusive(Enchantments.LOYALTY)
						.color(gold).special(LCEnchantments.CRAFT, craft.of(col))
		);

		INSTANT_THROWING = REG.ench("instant_shot", "Instant Throwing",
				"Throw the weapon out immediately on right click when not sneaking",
				e -> e.items(TagGen.THROWABLE)
						.color(gold).special(LCEnchantments.CRAFT, craft.of(col))
		);

		NO_AGGRO = REG.enchLegacy("stealth", "Stealth Attack",
				"Dagger damage has %s chance to not aggravate enemy",
				e -> e.items(TagGen.DAGGER).maxLevel(5)
						.color(green).special(LCEnchantments.CRAFT, craft.of(col)),
				StealthEnchantment::new
		);

		HEAVY = REG.enchLegacy("heavy", "Heavy",
				"For axe and heavy weapons:",
				e -> e.items(TagGen.HEAVY).maxLevel(5).group(EquipmentSlotGroup.MAINHAND)
						.color(purple).special(LCEnchantments.CRAFT, craft.of(col)),
				HeavyEnchantment::new
		);

		HARD_SHIELD = REG.enchLegacy("hard_shield", "Hard Shield",
				"For plate and round shields on both hands:",
				e -> e.items(TagGen.SHIELDS).maxLevel(5).group(EquipmentSlotGroup.HAND)
						.color(green).special(LCEnchantments.CRAFT, craft.of(col)),
				HardShieldEnchantment::new
		);

		HEAVY_SHIELD = REG.enchLegacy("heavy_shield", "Heavy Shield",
				"For plate and round shields on main hand:",
				e -> e.items(TagGen.SHIELDS).maxLevel(5).group(EquipmentSlotGroup.MAINHAND)
						.color(purple).special(LCEnchantments.CRAFT, craft.of(col)),
				HeavyShieldEnchantment::new
		);

		ENERGIZED_WILL = REG.enchLegacy("energized_will", "Energized Will",
				"Gradually increase machete attack range when stacking consecutive attacks. Conflicts with Raised Spirit.",
				e -> e.items(TagGen.MACHETE).maxLevel(5).group(EquipmentSlotGroup.MAINHAND)
						.color(green).special(LCEnchantments.CRAFT, craft.of(col)),
				EnergizedWillEnchantment::new
		);

		RAISED_SPIRIT = REG.enchLegacy("raised_spirit", "Raised Spirit",
				"Gradually increase machete attack speed when stacking consecutive attacks. Conflicts with Raised Spirit.",
				e -> e.items(TagGen.MACHETE).maxLevel(5).group(EquipmentSlotGroup.MAINHAND)
						.color(green).special(LCEnchantments.CRAFT, craft.of(col)),
				RaisedSpiritEnchantment::new
		);

		GHOST_SLASH = REG.ench("ghost_slash", "Ghost Slash",
				"Empty hits will stack hit count and consume durability as well.",
				e -> e.items(TagGen.DOUBLE_WIELD)
						.color(gold).special(LCEnchantments.CRAFT, craft.of(col))
		);

		CLAW_BLOCK = REG.ench("claw_shielding", "Claw Shielding",
				"Increase damage blocking time for claws. Works on either hand",
				e -> e.items(TagGen.CLAW).maxLevel(3)
						.color(gold).special(LCEnchantments.CRAFT, craft.of(col))
		);

	}

	public static void register() {

	}

}
