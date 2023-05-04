package dev.xkmc.l2weaponry.content.item.legendary;

import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2library.base.effects.EffectUtil;
import dev.xkmc.l2library.init.materials.generic.ExtraToolConfig;
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
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlackHammer extends HammerItem implements LegendaryWeapon {

	public BlackHammer(Tier tier, int damage, float speed, Properties prop, ExtraToolConfig config) {
		super(tier, damage, speed, prop, config);
	}

	@Override
	public void onCrit(Player player, Entity target) {
		int radius = LWConfig.COMMON.hammerOfIncarcerationRadius.get();
		int duration = LWConfig.COMMON.hammerOfIncarcerationDuration.get();
		var list = player.level.getEntitiesOfClass(LivingEntity.class,
				player.getBoundingBox().inflate(radius), e -> e instanceof Enemy);
		for (var e : list) {
			EffectUtil.addEffect(e, new MobEffectInstance(LCEffects.STONE_CAGE.get(), duration), EffectUtil.AddReason.NONE, player);
		}
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		int radius = LWConfig.COMMON.hammerOfIncarcerationRadius.get();
		list.add(LangData.BLACK_HAMMER.get(radius));
	}

}
