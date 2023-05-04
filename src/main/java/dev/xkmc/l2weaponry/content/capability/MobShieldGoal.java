package dev.xkmc.l2weaponry.content.capability;

import dev.xkmc.l2weaponry.content.item.base.BaseShieldItem;
import dev.xkmc.l2weaponry.init.registrate.LWItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;

public class MobShieldGoal extends Goal implements IShieldData {

	public static MobShieldGoal getShieldGoal(Mob mob) {
		var opt = mob.goalSelector.getRunningGoals()
				.filter(e -> e.getGoal() instanceof MobShieldGoal).findFirst();
		if (opt.isPresent()) {
			return (MobShieldGoal) opt.get().getGoal();
		} else {
			var ans = new MobShieldGoal(mob);
			mob.goalSelector.addGoal(0, ans);
			return ans;
		}
	}

	private final Mob mob;

	private double shieldDefense;

	private MobShieldGoal(Mob mob) {
		this.mob = mob;
	}

	public boolean onBlock(ItemStack stack, BaseShieldItem item, boolean shouldDisable, LivingEntity target) {
		double strength = item.reflectImpl(stack, mob.level.damageSources().mobAttack(mob),
				mob.getAttributeValue(Attributes.ATTACK_DAMAGE), this, target);
		target.knockback(strength, mob.getX() - target.getX(), mob.getZ() - target.getZ());
		if (!shouldDisable && !item.lightWeight(stack)) {
			return false;
		}
		int cd = item.damageShieldImpl(mob, this, stack, shouldDisable ? 1 : -1);
		return cd > 0;
	}

	public void onShieldDamage(ItemStack stack, BaseShieldItem item, double damage) {
		stack.getOrCreateTag().putInt(BaseShieldItem.KEY_LAST_DAMAGE, (int) damage);
	}

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

	@Override
	public boolean canReflect() {
		return mob.isOnGround() && mob.getAttribute(LWItems.REFLECT_TIME.get()) != null && mob.getAttributeValue(LWItems.REFLECT_TIME.get()) > 0;
	}

	@Override
	public double popRetain() {
		return 0;
	}

	@Override
	public int getReflectTimer() {
		return shieldDefense == 0 && canReflect() ? 1 : 0;
	}

}
