package dev.xkmc.l2weaponry.compat;

import dev.xkmc.l2weaponry.content.capability.MobShieldGoal;
import dev.xkmc.l2weaponry.content.item.base.BaseShieldItem;
import dev.xkmc.l2weaponry.content.item.base.BaseThrowableWeaponItem;
import dev.xkmc.l2weaponry.content.item.base.GenericShieldItem;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import dev.xkmc.modulargolems.content.entity.humanoid.HumanoidGolemEntity;
import dev.xkmc.modulargolems.events.event.GolemDamageShieldEvent;
import dev.xkmc.modulargolems.events.event.GolemDisableShieldEvent;
import dev.xkmc.modulargolems.events.event.GolemEquipEvent;
import dev.xkmc.modulargolems.events.event.GolemThrowableEvent;
import dev.xkmc.modulargolems.init.registrate.GolemTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class GolemCompat {

	public static void register(IEventBus bus) {
		MinecraftForge.EVENT_BUS.register(GolemCompat.class);
		bus.addListener(GolemCompat::onGolemSpawn);
	}

	public static void onGolemSpawn(EntityAttributeModificationEvent event) {
		event.add(GolemTypes.ENTITY_HUMANOID.get(), LWItems.SHIELD_DEFENSE.get());
		event.add(GolemTypes.ENTITY_HUMANOID.get(), LWItems.REFLECT_TIME.get());
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
		if (stack.getItem() instanceof BaseShieldItem item) {
			HumanoidGolemEntity golem = event.getEntity();
			MobShieldGoal goal = MobShieldGoal.getShieldGoal(golem);
			event.setDisabled(goal.onBlock(stack, item, event.shouldDisable(), event.getSource()));
		}
	}

	@SubscribeEvent
	public static void onDamageShield(GolemDamageShieldEvent event) {
		ItemStack stack = event.getStack();
		if (stack.getItem() instanceof BaseShieldItem item) {
			HumanoidGolemEntity golem = event.getEntity();
			MobShieldGoal goal = MobShieldGoal.getShieldGoal(golem);
			goal.onShieldDamage(stack, item, event.getDamage());
		}
	}

}
