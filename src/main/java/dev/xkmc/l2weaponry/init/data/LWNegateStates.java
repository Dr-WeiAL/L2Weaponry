package dev.xkmc.l2weaponry.init.data;

import dev.xkmc.l2damagetracker.contents.damage.DamageState;
import dev.xkmc.l2weaponry.init.L2Weaponry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

import java.util.Locale;
import java.util.function.Consumer;

public enum LWNegateStates implements DamageState {
	NO_PROJECTILE(DamageTypeTags.IS_PROJECTILE);

	private final TagKey<DamageType> tag;
	private final ResourceLocation id;

	LWNegateStates(TagKey<DamageType> tag) {
		this.tag = tag;
		this.id = L2Weaponry.loc(name().toLowerCase(Locale.ROOT));
	}

	@Override
	public void gatherTags(Consumer<TagKey<DamageType>> consumer) {

	}

	@Override
	public void removeTags(Consumer<TagKey<DamageType>> consumer) {
		consumer.accept(tag);
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}
}
