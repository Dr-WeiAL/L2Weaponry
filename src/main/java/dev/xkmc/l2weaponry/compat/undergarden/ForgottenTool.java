package dev.xkmc.l2weaponry.compat.undergarden;

import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.init.materials.LWExtraConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;
import quek.undergarden.Undergarden;

import java.util.List;

public class ForgottenTool extends ExtraToolConfig implements LWExtraConfig {

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state, float old) {
		float ans = 1;
		var rl = BuiltInRegistries.BLOCK.getKey(state.getBlock());
		if (rl != null && rl.getNamespace().equals(Undergarden.MODID))
			ans = 1.5f;
		return ans * super.getDestroySpeed(stack, state, old);
	}

	@Override
	public void onDamage(DamageData.Offence cache, ItemStack stack) {
		var target = cache.getTarget();
		if (target.getType().is(Tags.EntityTypes.BOSSES)) return;
		var rl = BuiltInRegistries.ENTITY_TYPE.getKey(cache.getTarget().getType());
		if (rl != null && rl.getNamespace().equals(Undergarden.MODID)) {
			var id = stack.getItemHolder().unwrapKey().orElseThrow().location();
			cache.addHurtModifier(DamageModifier.multTotal(1.5f, id));
		}
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(Component.translatable("tooltip.undergarden.forgotten_weapon").withStyle(ChatFormatting.GREEN));
		list.add(Component.translatable("tooltip.undergarden.forgotten_tool").withStyle(ChatFormatting.GREEN));
	}

}
