package dev.xkmc.l2weaponry.init.data;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateTagsProvider;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagGen {

	public static final TagKey<Item> CLAW = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "claw"));
	public static final TagKey<Item> DAGGER = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "dagger"));
	public static final TagKey<Item> HAMMER = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "hammer"));
	public static final TagKey<Item> BATTLE_AXE = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "battle_axe"));
	public static final TagKey<Item> SPEAR = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "spear"));
	public static final TagKey<Item> HAEVY_CLAW = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "heavy_claw"));

	public static void onBlockTagGen(RegistrateTagsProvider<Block> pvd) {
	}

	public static void onItemTagGen(RegistrateTagsProvider<Item> pvd) {
	}

}
