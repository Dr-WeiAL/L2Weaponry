package dev.xkmc.l2weaponry.init.data;

import com.mojang.datafixers.util.Pair;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import dev.xkmc.l2weaponry.init.registrate.LWEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.List;

public class TagGen {

	public static final TagKey<Item> CLAW = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "claw"));
	public static final TagKey<Item> DAGGER = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "dagger"));
	public static final TagKey<Item> HAMMER = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "hammer"));
	public static final TagKey<Item> BATTLE_AXE = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "battle_axe"));
	public static final TagKey<Item> SPEAR = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "spear"));
	public static final TagKey<Item> MACHETE = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "machete"));

	public static final TagKey<Item> ROUND_SHIELD = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "round_shield"));
	public static final TagKey<Item> PLATE_SHIELD = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "plate_shield"));

	public static final TagKey<Item> THROWING_AXE = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "throwing_axe"));
	public static final TagKey<Item> JAVELIN = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "javelin"));
	public static final TagKey<Item> THROWABLE = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "throwable"));

	public static final TagKey<Item> NUNCHAKU = ItemTags.create(new ResourceLocation(L2Weaponry.MODID, "nunchaku"));

	private static final List<Pair<TagKey<Item>, ResourceLocation>> LIST = new ArrayList<>();

	public static void onBlockTagGen(RegistrateTagsProvider<Block> pvd) {
	}

	@SuppressWarnings({"unchecked"})
	public static void onItemTagGen(RegistrateTagsProvider<Item> pvd) {
		pvd.addTag(Tags.Items.TOOLS_TRIDENTS).addTags(JAVELIN, THROWING_AXE);
		for (var e : LIST) {
			pvd.addTag(e.getFirst()).addOptional(e.getSecond());
		}
		pvd.addTag(Tags.Items.TOOLS_SHIELDS).addTags(ROUND_SHIELD, PLATE_SHIELD);
		pvd.addTag(ItemTags.create(new ResourceLocation("skilltree", "melee_weapon")))
				.addTags(ItemTags.SWORDS, ItemTags.AXES, JAVELIN, THROWING_AXE,
				CLAW, DAGGER, HAMMER, BATTLE_AXE, SPEAR, MACHETE, NUNCHAKU);

	}

	public static void onEntityTagGen(RegistrateTagsProvider.IntrinsicImpl<EntityType<?>> pvd) {
		pvd.addTag(EntityTypeTags.IMPACT_PROJECTILES).add(LWEntities.ET_AXE.get(), LWEntities.TE_JAVELIN.get());
	}

	public static void addItem(TagKey<Item> tag, ResourceLocation id) {
		LIST.add(Pair.of(tag, id));
	}
}
