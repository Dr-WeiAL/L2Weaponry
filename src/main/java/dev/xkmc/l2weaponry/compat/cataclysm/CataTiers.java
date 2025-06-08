package dev.xkmc.l2weaponry.compat.cataclysm;

import com.github.L_Ender.cataclysm.init.ModItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.util.Lazy;

import java.util.function.Supplier;

public enum CataTiers implements Tier {
	IGNITIUM(12, 8, 20, () -> Ingredient.of(ModItems.IGNITIUM_INGOT.get())),
	WITHERITE(12, 8, 15, () -> Ingredient.of(ModItems.WITHERITE_INGOT.get())),
	CURSIUM(12, 8, 20, () -> Ingredient.of(ModItems.CURSIUM_INGOT.get())),
	ANCIENT_METAL(8, 4, 25, () -> Ingredient.of(ModItems.ANCIENT_METAL_INGOT.get()));

	private final float speed;
	private final float attack;
	private final int enchantability;
	private final Supplier<Ingredient> repair;

	CataTiers(float speed, float attack, int enchantability, Supplier<Ingredient> repair) {

		this.speed = speed;
		this.attack = attack;
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
	public int getLevel() {
		return 5;
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
