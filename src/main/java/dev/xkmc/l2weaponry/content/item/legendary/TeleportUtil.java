package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2library.content.raytrace.RayTraceUtil;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TeleportUtil {

	public static boolean teleport(Player player, LivingEntity target, boolean back) {
		if (player.level().isClientSide) {
			return true;
		}
		Vec3 tPos = target.position();
		double reach = player.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE) - 0.5;
		Vec3 end;
		if (back) {
			end = RayTraceUtil.getRayTerm(tPos, target.getXRot(), target.getYRot(), -reach);
		} else {
			end = RayTraceUtil.getRayTerm(tPos, player.getXRot(), player.getYRot(), -reach);
		}
		BlockPos pos = BlockPos.containing(end);
		AABB aabb = player.getBoundingBox();
		for (int i = 0; i < 5; i++) {
			BlockPos iPos = pos.above(i);
			Vec3 cen = new Vec3(iPos.getX() + 0.5, iPos.getY() + 1, iPos.getZ() + 0.5);
			AABB iab = aabb.move(cen.subtract(aabb.getCenter()));
			if (player.level().noCollision(player, iab)) {
				player.teleportTo(iPos.getX() + 0.5, iPos.getY(), iPos.getZ() + 0.5);
				player.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
				player.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
				player.level().gameEvent(GameEvent.TELEPORT, iPos, GameEvent.Context.of(player));
				return true;
			}
		}
		return false;
	}

}
