package dev.xkmc.l2weaponry.content.capability;

import dev.xkmc.l2weaponry.content.item.base.BaseThrowableWeaponItem;
import dev.xkmc.l2weaponry.content.item.base.GenericShieldItem;
import dev.xkmc.modulargolems.events.event.GolemDisableShieldEvent;
import dev.xkmc.modulargolems.events.event.GolemEquipEvent;
import dev.xkmc.modulargolems.events.event.GolemThrowableEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

;

public class GolemCompat {

	public static void register() {
		if (ModList.get().isLoaded("modulargolems")) {
			registerImpl();
		}
	}

	private static void registerImpl() {
		MinecraftForge.EVENT_BUS.register(GolemCompat.class);
	}

	@SubscribeEvent
	public static void onEquip(GolemEquipEvent event) {
		if (event.getStack().getItem() instanceof GenericShieldItem item) {
			if (item.lightWeight(event.getStack()) && event.getEntity().getItemBySlot(EquipmentSlot.OFFHAND).isEmpty())
				event.setSlot(EquipmentSlot.OFFHAND, 1);
			else event.setSlot(EquipmentSlot.MAINHAND, 1);
		}
	}

	@SubscribeEvent
	public static void onThrow(GolemThrowableEvent event) {
		var golem = event.getEntity();
		if (event.getStack().getItem() instanceof BaseThrowableWeaponItem item) {
			event.setThrowable(level -> {
				var ans = item.getProjectile(level, golem, event.getStack());
				ans.setBaseDamage(golem.getAttributeValue(Attributes.ATTACK_DAMAGE));
				return ans;
			});
		}
	}

	@SubscribeEvent
	public static void onBlock(GolemDisableShieldEvent event) {
		ItemStack stack = event.getStack();
		if (stack.getItem() instanceof GenericShieldItem item) {
			if (!event.shouldDisable() && !item.lightWeight(stack)) return;
			GolemShieldGoal goal = getShieldGoal(event.getEntity());
			item.damageShieldImpl(event.getEntity(), goal, stack, event.shouldDisable() ? 1 : -1);
			event.setDisabled(false);
		}
	}

	private static GolemShieldGoal getShieldGoal(Mob mob) {
		var opt = mob.goalSelector.getRunningGoals()
				.filter(e -> e.getGoal() instanceof GolemShieldGoal).findFirst();
		if (opt.isPresent()) {
			return (GolemShieldGoal) opt.get().getGoal();
		} else {
			var ans = new GolemShieldGoal();
			mob.goalSelector.addGoal(0, ans);
			return ans;
		}
	}

	private static class GolemShieldGoal extends Goal implements IShieldData {


		private double shieldDefense;

		@Override
		public boolean canUse() {
			return shieldDefense > 0;
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public void tick() {
			shieldDefense = Math.max(0, shieldDefense - 0.01);
		}

		@Override
		public double getShieldDefense() {
			return shieldDefense;
		}

		@Override
		public void setShieldDefense(double i) {
			shieldDefense = i;
		}

	}

}
