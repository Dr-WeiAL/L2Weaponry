package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2library.util.raytrace.RayTraceUtil;
import dev.xkmc.l2weaponry.content.item.types.SpearItem;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnderDagger extends SpearItem implements LegendaryWeapon {

	public EnderDagger(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config) {
		super(tier, damage, speed, prop, config);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		var hit = RayTraceUtil.rayTraceEntity(player, 8, e -> e instanceof LivingEntity);//TODO config
		if (hit != null && (level.isClientSide() || teleport(player, hit.getLocation(), hit.getEntity())))
			return InteractionResultHolder.success(stack);
		return InteractionResultHolder.fail(stack);
	}

	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
		if (player.level.isClientSide) return InteractionResult.SUCCESS;
		var hit = RayTraceUtil.rayTraceEntity(player,
				player.getAttributeValue(ForgeMod.REACH_DISTANCE.get()), e -> e instanceof LivingEntity);
		if (hit != null && teleport(player, hit.getLocation(), target))
			return InteractionResult.SUCCESS;
		return InteractionResult.FAIL;
	}

	private boolean teleport(Player player, Vec3 target, Entity entity) {
		Vec3 pos = new Vec3(player.getX(), player.getEyeY(), player.getZ());
		double reach = player.getAttributeValue(ForgeMod.REACH_DISTANCE.get()) - 0.5;
		Vec3 end = RayTraceUtil.getRayTerm(target, player.getXRot(), player.getYRot(), reach);
		Vec3 diff = end.subtract(pos);
		Vec3 dst = diff.add(player.position());
		if (player.level.noCollision(player, player.getBoundingBox().move(diff))) {
			player.teleportTo(dst.x(), dst.y(), dst.z());
			float yrot = player.rotate(Rotation.CLOCKWISE_180);
			float xrot = 180 - player.getXRot();
			player.setYRot(yrot);
			player.setXRot(xrot);
			player.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
			if (entity instanceof Mob mob && mob.getTarget() == player) {
				mob.setTarget(null);
			}
			return true;
		}
		return false;
	}

	@Override
	public void modifySource(DamageSource source, LivingEntity player, LivingEntity target) {
		if (target instanceof Mob mob && mob.getTarget() != player)
			source.bypassArmor();
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.ENDER_DAGGER.get());
	}

}
