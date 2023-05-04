package dev.xkmc.l2weaponry.init.data;

import com.tterrag.registrate.providers.RegistrateTagsProvider;
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
	public static final TagKey<Item> MACHETE = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "machete"));

	public static final TagKey<Item> ROUND_SHIELD = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "round_shield"));
	public static final TagKey<Item> PLATE_SHIELD = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "plate_shield"));

	public static final TagKey<Item> THROWIG_AXE = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "throwing_axe"));
	public static final TagKey<Item> JAVELIN = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "javelin"));

	public static void onBlockTagGen(RegistrateTagsProvider<Block> pvd) {
	}

	public static void onItemTagGen(RegistrateTagsProvider<Item> pvd) {
	}

}
