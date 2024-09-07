package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2damagetracker.contents.attack.CreateSourceEvent;
import dev.xkmc.l2damagetracker.contents.damage.DefaultDamageState;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2weaponry.content.item.types.HammerItem;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AbyssHammer extends HammerItem implements LegendaryWeapon {

	public AbyssHammer(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config);
	}

	@Override
	public void modifySource(LivingEntity attacker, CreateSourceEvent event, ItemStack item, @Nullable Entity target) {
		super.modifySource(attacker, event, item, target);
		if (attacker instanceof Player player) {// create source is called before crit event
			float f2 = player.getAttackStrengthScale(0.5F);
			boolean flag4 = f2 > 0.9F;
			boolean flag1 = flag4
					&& player.fallDistance > 0.0F
					&& !player.onGround()
					&& !player.onClimbable()
					&& !player.isInWater()
					&& !player.hasEffect(MobEffects.BLINDNESS)
					&& !player.isPassenger()
					&& target instanceof LivingEntity
					&& !player.isSprinting();
			flag1 |= player.getRandom().nextDouble() < player.getAttributeValue(L2DamageTracker.CRIT_RATE);
			if (flag1) {
				event.enable(DefaultDamageState.BYPASS_MAGIC);
			}
		}
		if (attacker instanceof Mob) {
			event.enable(DefaultDamageState.BYPASS_MAGIC);
		}
	}

	@Override
	public void appendHoverText(ItemStack pStack, TooltipContext pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.ABYSS_HAMMER.get());
	}

}
