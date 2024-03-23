package dev.xkmc.l2weaponry.compat.undergarden;

import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.init.materials.LWExtraConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import quek.undergarden.Undergarden;

import java.util.List;

public class ForgottenTool extends ExtraToolConfig implements LWExtraConfig {

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state, float old) {
		float ans = 1;
		var rl = ForgeRegistries.BLOCKS.getKey(state.getBlock());
		if (rl != null && rl.getNamespace().equals(Undergarden.MODID))
			ans = 1.5f;
		return ans * super.getDestroySpeed(stack, state, old);
	}

	@Override
	public void onHurt(AttackCache cache, LivingEntity attacker, ItemStack stack) {
		var target = cache.getAttackTarget();
		if (target.getType().is(Tags.EntityTypes.BOSSES)) return;
		var rl = ForgeRegistries.ENTITY_TYPES.getKey(cache.getAttackTarget().getType());
		if (rl != null && rl.getNamespace().equals(Undergarden.MODID)) {
			cache.addHurtModifier(DamageModifier.multTotal(1.5f));
		}
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(Component.translatable("tooltip.forgotten_sword").withStyle(ChatFormatting.GREEN));
		list.add(Component.translatable("tooltip.forgotten_tool").withStyle(ChatFormatting.GREEN));
	}

}
