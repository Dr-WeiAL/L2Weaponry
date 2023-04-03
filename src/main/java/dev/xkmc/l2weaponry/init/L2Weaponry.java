package dev.xkmc.l2weaponry.init;

import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.base.tabs.contents.AttributeEntry;
import dev.xkmc.l2library.init.events.attack.AttackEventHandler;
import dev.xkmc.l2library.repack.registrate.providers.ProviderType;
import dev.xkmc.l2weaponry.compat.GolemCompat;
import dev.xkmc.l2weaponry.content.capability.LWPlayerData;
import dev.xkmc.l2weaponry.events.LWAttackEventListener;
import dev.xkmc.l2weaponry.events.LegendaryWeaponEvents;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.data.LangData;
import dev.xkmc.l2weaponry.init.data.RecipeGen;
import dev.xkmc.l2weaponry.init.data.TagGen;
import dev.xkmc.l2weaponry.init.registrate.LWEntities;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import dev.xkmc.l2weaponry.network.NetworkManager;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(L2Weaponry.MODID)
public class L2Weaponry {

	public static final String MODID = "l2weaponry";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);
	//public static final GenItem MATS = new GenItem(MODID, REGISTRATE);

	private static void registerRegistrates(IEventBus bus) {
		LWItems.register();
		LWEntities.register();
		NetworkManager.register();
		if (ModList.get().isLoaded("modulargolems")) GolemCompat.register(bus);
		REGISTRATE.addDataGenerator(ProviderType.LANG, LangData::addTranslations);
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipe);
		REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, TagGen::onBlockTagGen);
		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, TagGen::onItemTagGen);
	}

	private static void registerForgeEvents() {
		LWConfig.init();
		LWPlayerData.register();
		MinecraftForge.EVENT_BUS.register(LegendaryWeaponEvents.class);
	}

	private static void registerModBusEvents(IEventBus bus) {
		bus.addListener(L2Weaponry::setup);
		bus.addListener(L2Weaponry::modifyAttributes);
		bus.addListener(EventPriority.LOWEST, L2Weaponry::gatherData);
	}

	public L2Weaponry() {
		FMLJavaModLoadingContext ctx = FMLJavaModLoadingContext.get();
		IEventBus bus = ctx.getModEventBus();
		registerModBusEvents(bus);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> L2WeaponryClient.onCtorClient(bus, MinecraftForge.EVENT_BUS));
		registerRegistrates(bus);
		registerForgeEvents();
	}

	private static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			AttackEventHandler.LISTENERS.add(new LWAttackEventListener());
			AttributeEntry.add(LWItems.SHIELD_DEFENSE, false, 14000);
		});
	}

	private static void modifyAttributes(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, LWItems.SHIELD_DEFENSE.get());
		event.add(EntityType.PLAYER, LWItems.REFLECT_TIME.get());
	}

	public static void gatherData(GatherDataEvent event) {
	}

}
