package dev.xkmc.l2weaponry.init.data;

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
import net.neoforged.neoforge.common.Tags;

public class TagGen {

	public static final TagKey<Item> CLAW = ItemTags.create(L2Weaponry.loc("claw"));
	public static final TagKey<Item> DAGGER = ItemTags.create(L2Weaponry.loc("dagger"));
	public static final TagKey<Item> HAMMER = ItemTags.create(L2Weaponry.loc("hammer"));
	public static final TagKey<Item> BATTLE_AXE = ItemTags.create(L2Weaponry.loc("battle_axe"));
	public static final TagKey<Item> SPEAR = ItemTags.create(L2Weaponry.loc("spear"));
	public static final TagKey<Item> MACHETE = ItemTags.create(L2Weaponry.loc("machete"));
	public static final TagKey<Item> ROUND_SHIELD = ItemTags.create(L2Weaponry.loc("round_shield"));
	public static final TagKey<Item> PLATE_SHIELD = ItemTags.create(L2Weaponry.loc("plate_shield"));
	public static final TagKey<Item> THROWING_AXE = ItemTags.create(L2Weaponry.loc("throwing_axe"));
	public static final TagKey<Item> JAVELIN = ItemTags.create(L2Weaponry.loc("javelin"));
	public static final TagKey<Item> NUNCHAKU = ItemTags.create(L2Weaponry.loc("nunchaku"));

	public static final TagKey<Item> THROWABLE = ItemTags.create(L2Weaponry.loc("throwable"));
	public static final TagKey<Item> HEAVY = ItemTags.create(L2Weaponry.loc("heavy"));
	public static final TagKey<Item> SHIELDS = ItemTags.create(L2Weaponry.loc("shields"));
	public static final TagKey<Item> DOUBLE_WIELD = ItemTags.create(L2Weaponry.loc("double_wield"));

	public static void onBlockTagGen(RegistrateTagsProvider<Block> pvd) {
	}

	@SuppressWarnings({"unchecked"})
	public static void onItemTagGen(RegistrateTagsProvider<Item> pvd) {
		pvd.addTag(THROWABLE).addTags(JAVELIN, THROWING_AXE);
		pvd.addTag(Tags.Items.TOOLS_SHIELD).addTags(ROUND_SHIELD, PLATE_SHIELD);
		pvd.addTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("skilltree", "melee_weapon")))
				.addTags(ItemTags.SWORDS, ItemTags.AXES, JAVELIN, THROWING_AXE,
						CLAW, DAGGER, HAMMER, BATTLE_AXE, SPEAR, MACHETE, NUNCHAKU);

	}

	public static void onEntityTagGen(RegistrateTagsProvider.IntrinsicImpl<EntityType<?>> pvd) {
		pvd.addTag(EntityTypeTags.IMPACT_PROJECTILES).add(LWEntities.ET_AXE.get(), LWEntities.TE_JAVELIN.get());
	}

}
