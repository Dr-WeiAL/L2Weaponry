package dev.xkmc.l2weaponry.compat.undergarden;

import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.init.materials.LWExtraConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import quek.undergarden.network.CreateCritParticlePacket;
import quek.undergarden.registry.UGParticleTypes;
import quek.undergarden.registry.UGTags;

import java.util.List;

public class UteriumTool extends ExtraToolConfig implements LWExtraConfig {

	@Override
	public void onHurt(DamageData.Offence cache, LivingEntity attacker, ItemStack stack) {
		var target = cache.getTarget();
		if (!target.getType().is(UGTags.Entities.ROTSPAWN)) return;
		var id = stack.getItemHolder().unwrapKey().orElseThrow().location();
		cache.addHurtModifier(DamageModifier.multTotal(1.5f, id));
		if (target.level().isClientSide()) return;
		PacketDistributor.sendToPlayersTrackingEntity(target, new CreateCritParticlePacket(
				target.getId(), 2, UGParticleTypes.UTHERIUM_CRIT.get()));
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(Component.translatable("tooltip.undergarden.utherium_weapon").withStyle(ChatFormatting.RED));
	}

}
