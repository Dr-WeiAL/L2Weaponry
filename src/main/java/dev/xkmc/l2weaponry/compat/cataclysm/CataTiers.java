package dev.xkmc.l2weaponry.compat.cataclysm;

import com.github.L_Ender.cataclysm.init.ModItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.util.Lazy;

import java.util.function.Supplier;

public enum CataTiers implements Tier {
	IGNITIUM(12, 8, 20, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, () -> Ingredient.of(ModItems.IGNITIUM_INGOT)),
	WITHERITE(12, 8, 15, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, () -> Ingredient.of(ModItems.WITHERITE_INGOT)),
	CURSIUM(12, 8, 20, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, () -> Ingredient.of(ModItems.CURSIUM_INGOT)),
	ANCIENT_METAL(8, 4, 25, BlockTags.INCORRECT_FOR_DIAMOND_TOOL, () -> Ingredient.of(ModItems.ANCIENT_METAL_INGOT));

	private final float speed;
	private final float attack;
	private final TagKey<Block> incorrect;
	private final int enchantability;
	private final Supplier<Ingredient> repair;

	CataTiers(float speed, float attack, int enchantability, TagKey<Block> incorrect, Supplier<Ingredient> repair) {

		this.speed = speed;
		this.attack = attack;
		this.incorrect = incorrect;
		this.enchantability = enchantability;
		this.repair = Lazy.of(repair);
	}

	@Override
	public int getUses() {
		return 3000;
	}

	@Override
	public float getSpeed() {
		return speed;
	}

	@Override
	public float getAttackDamageBonus() {
		return attack;
	}

	@Override
	public TagKey<Block> getIncorrectBlocksForDrops() {
		return incorrect;
	}

	@Override
	public int getEnchantmentValue() {
		return enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return repair.get();
	}
}
