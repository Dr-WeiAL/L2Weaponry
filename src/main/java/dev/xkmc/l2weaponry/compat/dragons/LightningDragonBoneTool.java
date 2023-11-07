package dev.xkmc.l2weaponry.compat.dragons;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.events.LWGeneralEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class LightningDragonBoneTool extends ExtraToolConfig {

	@Override
	public void onHit(ItemStack stack, LivingEntity target, LivingEntity user) {
		super.onHit(stack, target, user);

		if (IafConfig.dragonWeaponLightningAbility) {
			boolean flag = !(user instanceof Player) || !((double) user.attackAnim > 0.2D);

			if (!user.level().isClientSide && flag) {
				LightningBolt entity = EntityType.LIGHTNING_BOLT.create(target.level());
				assert entity != null;
				entity.addTag(LWGeneralEvents.LIGHTNING);
				entity.moveTo(target.position());
				if (user instanceof ServerPlayer sp) entity.setCause(sp);
				if (!target.level().isClientSide) {
					target.level().addFreshEntity(entity);
				}
			}

			if (target instanceof EntityFireDragon || target instanceof EntityIceDragon) {
				target.hurt(user.level().damageSources().lightningBolt(), 9.5F);
			}

			target.knockback(1.0D, user.getX() - target.getX(), user.getZ() - target.getZ());
		}
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(Component.translatable("dragon_sword_lightning.hurt1").withStyle(ChatFormatting.GREEN));
		if (IafConfig.dragonWeaponLightningAbility) {
			list.add(Component.translatable("dragon_sword_lightning.hurt2").withStyle(ChatFormatting.DARK_PURPLE));
		}
	}
}
