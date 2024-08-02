package dev.xkmc.l2weaponry.init.data;

import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.xkmc.l2core.init.reg.registrate.L2Registrate;
import dev.xkmc.l2damagetracker.contents.damage.DamageTypeRoot;
import dev.xkmc.l2damagetracker.contents.damage.DamageTypeWrapper;
import dev.xkmc.l2damagetracker.contents.damage.DefaultDamageState;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2damagetracker.init.data.DamageTypeAndTagsGen;
import dev.xkmc.l2damagetracker.init.data.L2DamageTypes;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LWDamageTypeGen extends DamageTypeAndTagsGen {

	public LWDamageTypeGen(L2Registrate reg) {
		super(reg);
	}


	public static final DamageTypeRoot TRIDENT = new DamageTypeRoot(L2Weaponry.MODID, DamageTypes.TRIDENT,
			List.of(DamageTypeTags.IS_PROJECTILE), (type) -> new DamageType("trident", 0.1F));

	public static void register() {
		TRIDENT.add(DefaultDamageState.BYPASS_MAGIC);
		TRIDENT.add(DefaultDamageState.BYPASS_ARMOR);
		TRIDENT.add(LWNegateStates.NO_PROJECTILE);
		TRIDENT.add(LWDamageStates.NO_ANGER);
		L2DamageTypes.PLAYER_ATTACK.add(LWDamageStates.NO_ANGER);
		L2DamageTypes.MOB_ATTACK.add(LWDamageStates.NO_ANGER);
		DamageTypeRoot.configureGeneration(Set.of(L2DamageTracker.MODID, L2Weaponry.MODID), L2Weaponry.MODID, LIST);
	}

	protected static final List<DamageTypeWrapper> LIST = new ArrayList<>();

	@Override
	protected void addDamageTypes(BootstrapContext<DamageType> ctx) {
		DamageTypeRoot.generateAll();
		for (DamageTypeWrapper wrapper : LIST) {
			ctx.register(wrapper.type(), wrapper.getObject());
		}
	}

	@Override
	protected void addDamageTypeTags(RegistrateTagsProvider.Impl<DamageType> pvd) {
		DamageTypeRoot.generateAll();
		for (DamageTypeWrapper wrapper : LIST) {
			wrapper.gen(pvd::addTag);
		}
	}


}
