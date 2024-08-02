package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2core.base.effects.EffectUtil;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.types.HammerItem;
import dev.xkmc.l2weaponry.init.data.LWConfig;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class BlackHammer extends HammerItem implements LegendaryWeapon {

	public BlackHammer(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config);
	}

	@Override
	public void onCrit(Player player, Entity target) {
		int radius = LWConfig.SERVER.hammerOfIncarcerationRadius.get();
		int duration = LWConfig.SERVER.hammerOfIncarcerationDuration.get();
		var list = player.level().getEntitiesOfClass(LivingEntity.class,
				player.getBoundingBox().inflate(radius), e -> e instanceof Enemy);
		for (var e : list) {
			EffectUtil.addEffect(e, new MobEffectInstance(LCEffects.INCARCERATE.holder(), duration), player);
		}
	}

	@Override
	public void appendHoverText(ItemStack pStack, TooltipContext pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		int radius = LWConfig.SERVER.hammerOfIncarcerationRadius.get();
		list.add(LangData.BLACK_HAMMER.get(radius));
	}

}
