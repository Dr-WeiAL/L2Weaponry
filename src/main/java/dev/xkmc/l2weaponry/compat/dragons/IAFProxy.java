package dev.xkmc.l2weaponry.compat.dragons;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Supplier;

public interface IAFProxy {

	class Provider {

		private static IAFProxy CACHE;

		private static IAFProxy get() {
			if (CACHE != null) return CACHE;
			try {
				CACHE = new IAFProxyAlex();
				return CACHE;
			} catch (Throwable ignore) {
			}
			try {
				CACHE = new IAFProxyCE();
				return CACHE;
			} catch (Throwable ignore) {
			}
			throw new IllegalStateException("No valid IaF target");
		}

	}

	static IAFProxy get() {
		return Provider.get();
	}

	String modid();

	Item witherBone();

	Tier tierIce();

	Tier tierFire();

	Tier tierLightning();

	Supplier<Item> ingotIceSteel();

	Supplier<Item> ingotFireSteel();

	Supplier<Item> ingotLightningSteel();

	Supplier<Block> blockIceSteel();

	Supplier<Block> blockFireSteel();

	Supplier<Block> blockLightningSteel();

	void fireHit(ItemStack stack, LivingEntity target, LivingEntity user);

	void fireDesc(ItemStack stack, List<Component> list);

	void iceHit(ItemStack stack, LivingEntity target, LivingEntity user);

	void iceDesc(ItemStack stack, List<Component> list);

	void lightningHit(ItemStack stack, LivingEntity target, LivingEntity user);

	void lightningDesc(ItemStack stack, List<Component> list);

}
