package dev.xkmc.l2weaponry.init;

import com.tterrag.registrate.providers.ProviderType;
import dev.xkmc.l2complements.events.ItemUseEventHandler;
import dev.xkmc.l2core.init.reg.registrate.L2Registrate;
import dev.xkmc.l2core.init.reg.simple.Reg;
import dev.xkmc.l2damagetracker.contents.attack.AttackEventHandler;
import dev.xkmc.l2serial.network.PacketHandler;
import dev.xkmc.l2weaponry.compat.GolemCompat;
import dev.xkmc.l2weaponry.events.LWAttackEventListener;
import dev.xkmc.l2weaponry.events.LWClickListener;
import dev.xkmc.l2weaponry.init.data.*;
import dev.xkmc.l2weaponry.init.registrate.LWEnchantments;
import dev.xkmc.l2weaponry.init.registrate.LWEntities;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforgespi.language.IModFileInfo;
import net.neoforged.neoforgespi.locating.IModFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(L2Weaponry.MODID)
@EventBusSubscriber(modid = L2Weaponry.MODID, bus = EventBusSubscriber.Bus.MOD)
public class L2Weaponry {

	public static final String MODID = "l2weaponry";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final Reg REG = new Reg(MODID);
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);

	public static final PacketHandler HANDLER = new PacketHandler(MODID, 1);

	public L2Weaponry(IEventBus bus) {
		LWItems.register();
		LWEntities.register();
		LWDamageTypeGen.register();
		LWConfig.init();
		LWEnchantments.register();
		ItemUseEventHandler.LIST.add(new LWClickListener());
		if (ModList.get().isLoaded("modulargolems")) GolemCompat.register(bus);
	}

	public static ResourceLocation loc(String id) {
		return ResourceLocation.fromNamespaceAndPath(MODID, id);
	}

	@SubscribeEvent
	public static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			AttackEventHandler.register(4000, new LWAttackEventListener());
			//TODO if (ModList.get().isLoaded(XEnchantment.MOD_ID)) XEnchCompat.onInit();
		});
	}

	@SubscribeEvent
	public static void modifyAttributes(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, LWItems.SHIELD_DEFENSE.holder());
		event.add(EntityType.PLAYER, LWItems.REFLECT_TIME.holder());
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void gatherData(GatherDataEvent event) {
		REGISTRATE.addDataGenerator(ProviderType.LANG, LangData::addTranslations);
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipe);
		REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, TagGen::onBlockTagGen);
		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, TagGen::onItemTagGen);
		REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, TagGen::onEntityTagGen);
		REGISTRATE.addDataGenerator(ProviderType.DATA_MAP, LWAttributeConfigGen::onDataMapGen);
		new LWDamageTypeGen(REGISTRATE).generate();
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
				Pack pack = Pack.readMetaAndCreate(
						new PackLocationInfo(MODID + ":" + builtin, Component.literal("Old L2Weaponry Model"),
								PackSource.FEATURE, Optional.empty()),
						new ModFilePackResources(modFile, "resourcepacks/" + builtin),
						PackType.CLIENT_RESOURCES,
						new PackSelectionConfig(false, Pack.Position.TOP, false)
				);
				if (pack != null) {
					consumer.accept(pack);
				}
			});
		}
	}

}
