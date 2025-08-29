package dev.xkmc.l2weaponry.compat.dragons;

import com.iafenvoy.iceandfire.IceAndFire;
import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.data.component.FrozenData;
import com.iafenvoy.iceandfire.entity.FireDragonEntity;
import com.iafenvoy.iceandfire.entity.IceDragonEntity;
import com.iafenvoy.iceandfire.registry.IafBlocks;
import com.iafenvoy.iceandfire.registry.IafItems;
import com.iafenvoy.iceandfire.registry.IafToolMaterials;
import dev.xkmc.l2library.init.FlagMarker;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Supplier;

public class IAFProxyCE implements IAFProxy {

	@Override
	public void fireHit(ItemStack stack, LivingEntity target, LivingEntity user) {
		if (IafCommonConfig.INSTANCE.tools.dragonFireAbility.getValue()) {
			if (target instanceof IceDragonEntity) {
				target.hurt(user.level().damageSources().inFire(), 13.5F);
			}
			target.setRemainingFireTicks(100);
			target.knockback(1.0, user.getX() - target.getX(), user.getZ() - target.getZ());
		}

	}

	@Override
	public void fireDesc(ItemStack stack, List<Component> list) {
		list.add(Component.translatable("dragon_sword_fire.hurt1").withStyle(ChatFormatting.GREEN));
		if (IafCommonConfig.INSTANCE.tools.dragonFireAbility.getValue()) {
			list.add(Component.translatable("dragon_sword_fire.hurt2").withStyle(ChatFormatting.DARK_RED));
		}
	}

	@Override
	public void iceHit(ItemStack stack, LivingEntity target, LivingEntity user) {

		if (IafCommonConfig.INSTANCE.tools.dragonIceAbility.getValue()) {
			if (target instanceof FireDragonEntity) {
				target.hurt(user.level().damageSources().drown(), 13.5F);
			}

			FrozenData data = FrozenData.get(target);
			data.setFrozen(target, 200);
			target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
			target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 2));
			target.knockback(1.0, user.getX() - target.getX(), user.getZ() - target.getZ());
		}
	}

	@Override
	public void iceDesc(ItemStack stack, List<Component> list) {
		list.add(Component.translatable("dragon_sword_ice.hurt1").withStyle(ChatFormatting.GREEN));
		if (IafCommonConfig.INSTANCE.tools.dragonIceAbility.getValue()) {
			list.add(Component.translatable("dragon_sword_ice.hurt2").withStyle(ChatFormatting.AQUA));
		}
	}

	@Override
	public void lightningHit(ItemStack stack, LivingEntity target, LivingEntity user) {
		if (IafCommonConfig.INSTANCE.tools.dragonLightningAbility.getValue()) {
			boolean flag = true;
			if (user instanceof Player && (double) user.attackAnim > 0.2) {
				flag = false;
			}

			if (!user.level().isClientSide && flag) {
				LightningBolt e = EntityType.LIGHTNING_BOLT.create(target.level());
				assert e != null;
				e.getTags().add("iceandfire.bolt_skip_loot");
				e.getTags().add(user.getStringUUID());
				e.addTag(FlagMarker.LIGHTNING);
				e.moveTo(target.position());
				if (!target.level().isClientSide) {
					target.level().addFreshEntity(e);
				}
			}

			if (target instanceof FireDragonEntity || target instanceof IceDragonEntity) {
				target.hurt(user.level().damageSources().lightningBolt(), 9.5F);
			}

			target.knockback(1.0, user.getX() - target.getX(), user.getZ() - target.getZ());
		}

	}

	@Override
	public void lightningDesc(ItemStack stack, List<Component> list) {
		list.add(Component.translatable("dragon_sword_lightning.hurt1").withStyle(ChatFormatting.GREEN));
		if (IafCommonConfig.INSTANCE.tools.dragonLightningAbility.getValue()) {
			list.add(Component.translatable("dragon_sword_lightning.hurt2").withStyle(ChatFormatting.DARK_PURPLE));
		}
	}

	@Override
	public String modid() {
		return IceAndFire.MOD_ID;
	}

	@Override
	public Item witherBone() {
		return IafItems.WITHERBONE.get();
	}

	@Override
	public Tier tierIce() {
		return IafToolMaterials.DRAGONSTEEL_ICE;
	}

	@Override
	public Tier tierFire() {
		return IafToolMaterials.DRAGONSTEEL_FIRE;
	}

	@Override
	public Tier tierLightning() {
		return IafToolMaterials.DRAGONSTEEL_LIGHTNING;
	}

	@Override
	public Supplier<Item> ingotIceSteel() {
		return IafItems.DRAGONSTEEL_ICE_INGOT;
	}

	@Override
	public Supplier<Item> ingotFireSteel() {
		return IafItems.DRAGONSTEEL_FIRE_INGOT;
	}

	@Override
	public Supplier<Item> ingotLightningSteel() {
		return IafItems.DRAGONSTEEL_LIGHTNING_INGOT;
	}

	@Override
	public Supplier<Block> blockIceSteel() {
		return IafBlocks.DRAGONSTEEL_ICE_BLOCK;
	}

	@Override
	public Supplier<Block> blockFireSteel() {
		return IafBlocks.DRAGONSTEEL_FIRE_BLOCK;
	}

	@Override
	public Supplier<Block> blockLightningSteel() {
		return IafBlocks.DRAGONSTEEL_LIGHTNING_BLOCK;
	}

}
