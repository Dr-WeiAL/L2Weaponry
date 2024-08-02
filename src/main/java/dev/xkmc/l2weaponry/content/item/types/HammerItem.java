package dev.xkmc.l2weaponry.content.item.types;

import dev.xkmc.l2damagetracker.contents.attack.CreateSourceEvent;
import dev.xkmc.l2damagetracker.contents.damage.DefaultDamageState;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2weaponry.content.item.base.SlowWieldItem;
import dev.xkmc.l2weaponry.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HammerItem extends SlowWieldItem {

	public HammerItem(Tier tier, Properties prop, ExtraToolConfig config) {
		super(tier, prop, config, BlockTags.MINEABLE_WITH_PICKAXE);
	}

	@Override
	public boolean isSharp() {
		return false;
	}

	@Override
	public void modifySource(LivingEntity attacker, CreateSourceEvent event, ItemStack item, @Nullable Entity target) {
		event.enable(DefaultDamageState.BYPASS_ARMOR);
	}

	@Override
	public void appendHoverText(ItemStack pStack, TooltipContext pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
		list.add(LangData.TOOL_HAMMER.get());
		super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
	}

	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
		return true;
	}

}
