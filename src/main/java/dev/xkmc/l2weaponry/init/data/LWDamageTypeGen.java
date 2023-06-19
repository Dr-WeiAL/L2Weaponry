package dev.xkmc.l2weaponry.init.data;

import dev.xkmc.l2damagetracker.contents.damage.DamageTypeRoot;
import dev.xkmc.l2damagetracker.contents.damage.DamageTypeWrapper;
import dev.xkmc.l2damagetracker.contents.damage.DamageWrapperTagProvider;
import dev.xkmc.l2damagetracker.contents.damage.DefaultDamageState;
import dev.xkmc.l2damagetracker.init.data.DamageTypeAndTagsGen;
import dev.xkmc.l2library.init.L2Library;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class LWDamageTypeGen extends DamageTypeAndTagsGen {

	public LWDamageTypeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> pvd, ExistingFileHelper helper) {
		super(output, pvd, helper, L2Weaponry.MODID);
	}


	public static final DamageTypeRoot TRIDENT = new DamageTypeRoot(L2Weaponry.MODID, DamageTypes.TRIDENT,
			List.of(DamageTypeTags.IS_PROJECTILE), (type) -> new DamageType("trident", 0.1F));

	public static void register() {
		TRIDENT.add(DefaultDamageState.BYPASS_MAGIC);
		TRIDENT.add(DefaultDamageState.BYPASS_ARMOR);
		DamageTypeRoot.configureGeneration(Set.of(L2Library.MODID, L2Weaponry.MODID), L2Weaponry.MODID, LIST);
	}

	protected static final List<DamageTypeWrapper> LIST = new ArrayList<>();

	@Override
	protected void addDamageTypes(BootstapContext<DamageType> ctx) {
		DamageTypeRoot.generateAll();
		for (DamageTypeWrapper wrapper : LIST) {
			ctx.register(wrapper.type(), wrapper.getObject());
		}
	}

	@Override
	protected void addDamageTypeTags(DamageWrapperTagProvider pvd, HolderLookup.Provider lookup) {
		DamageTypeRoot.generateAll();
		for (DamageTypeWrapper wrapper : LIST) {
			wrapper.gen(pvd, lookup);
		}
	}


}
