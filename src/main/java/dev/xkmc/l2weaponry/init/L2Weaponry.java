package dev.xkmc.l2weaponry.init;

import com.doo.xenchantment.XEnchantment;
import com.tterrag.registrate.providers.ProviderType;
import dev.xkmc.l2complements.events.ItemUseEventHandler;
import dev.xkmc.l2damagetracker.contents.attack.AttackEventHandler;
import dev.xkmc.l2library.serial.config.PacketHandler;
import dev.xkmc.l2weaponry.compat.GolemCompat;
import dev.xkmc.l2weaponry.compat.XEnchCompat;
import dev.xkmc.l2weaponry.content.capability.LWPlayerData;
import dev.xkmc.l2weaponry.events.LWAttackEventListener;
import dev.xkmc.l2weaponry.events.LWClickListener;
import dev.xkmc.l2weaponry.init.data.*;
import dev.xkmc.l2weaponry.init.registrate.LWEnchantments;
import dev.xkmc.l2weaponry.init.registrate.LWEntities;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import dev.xkmc.l2weaponry.init.registrate.LWRegistrate;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(L2Weaponry.MODID)
@Mod.EventBusSubscriber(modid = L2Weaponry.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class L2Weaponry {

	public static final String MODID = "l2weaponry";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final LWRegistrate REGISTRATE = new LWRegistrate(MODID);

	public static final PacketHandler HANDLER = new PacketHandler(
			new ResourceLocation(L2Weaponry.MODID, "main"), 1
	);

	public L2Weaponry() {
		FMLJavaModLoadingContext ctx = FMLJavaModLoadingContext.get();
		IEventBus bus = ctx.getModEventBus();
		LWItems.register();
		LWEntities.register();
		LWDamageTypeGen.register();
		LWConfig.init();
		LWPlayerData.register();
		LWEnchantments.register();
		ItemUseEventHandler.LIST.add(new LWClickListener());
		if (ModList.get().isLoaded("modulargolems")) GolemCompat.register(bus);
		REGISTRATE.addDataGenerator(ProviderType.LANG, LangData::addTranslations);
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipe);
		REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, TagGen::onBlockTagGen);
		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, TagGen::onItemTagGen);
		REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, TagGen::onEntityTagGen);
	}

	@SubscribeEvent
	public static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			AttackEventHandler.register(4000, new LWAttackEventListener());
			if (ModList.get().isLoaded(XEnchantment.MOD_ID)) {
				XEnchCompat.onInit();
			}
		});
	}

	@SubscribeEvent
	public static void modifyAttributes(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, LWItems.SHIELD_DEFENSE.get());
		event.add(EntityType.PLAYER, LWItems.REFLECT_TIME.get());
	}

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		boolean gen = event.includeServer();
		var output = event.getGenerator().getPackOutput();
		var lookup = event.getLookupProvider();
		var helper = event.getExistingFileHelper();
		new LWDamageTypeGen(output, lookup, helper).generate(gen, event.getGenerator());
		event.getGenerator().addProvider(event.includeServer(), new LWAttributeConfigGen(event.getGenerator()));
	}

	@SubscribeEvent
	public static void addPackFinders(AddPackFindersEvent event) {
		if (event.getPackType() == PackType.CLIENT_RESOURCES) {
			IModFileInfo modFileInfo = ModList.get().getModFileById(MODID);
			if (modFileInfo == null)
				return;
			String builtin = "old_weapon_model";
			IModFile modFile = modFileInfo.getFile();
			event.addRepositorySource((consumer) -> {
				Pack pack = Pack.readMetaAndCreate(MODID + ":" + builtin,
						Component.literal("Old L2Weaponry Model"), false,
						(id) -> new ModFilePackResources(id, modFile, "resourcepacks/" + builtin),
						PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);
				if (pack != null) {
					consumer.accept(pack);
				}
			});
		}
	}

}
