package dev.xkmc.l2weaponry.content.entity;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class BaseThrownWeapon<T extends BaseThrownWeapon<T>> extends AbstractArrow {
	private static final EntityDataAccessor<Byte> ID_LOYALTY = SynchedEntityData.defineId(BaseThrownWeapon.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(BaseThrownWeapon.class, EntityDataSerializers.BOOLEAN);
	private ItemStack item;
	private int remainingHit = 1;
	public int clientSideReturnTridentTickCount;

	public BaseThrownWeapon(EntityType<T> type, Level pLevel, Item def) {
		super(type, pLevel);
		item = new ItemStack(def);
	}

	public BaseThrownWeapon(EntityType<T> type, Level pLevel, LivingEntity pShooter, ItemStack pStack) {
		super(type, pShooter, pLevel);
		this.item = pStack.copy();
		this.entityData.set(ID_LOYALTY, (byte) EnchantmentHelper.getLoyalty(pStack));
		this.entityData.set(ID_FOIL, pStack.hasFoil());
	}

	// ------ base weapon code

	protected float getDamage() {
		var list = item.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);
		AttributeInstance ins = new AttributeInstance(Attributes.ATTACK_DAMAGE, e -> {
		});
		ins.setBaseValue(1);
		list.forEach(ins::addTransientModifier);
		return (float) ins.getValue();
	}

	public ItemStack getItem(){
		return item;
	}

	// ------ default trident code

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ID_LOYALTY, (byte) 0);
		this.entityData.define(ID_FOIL, false);
	}

	public void tick() {
		if (this.inGroundTime > 4) {
			this.remainingHit = 0;
		}

		Entity entity = this.getOwner();
		int loyal = this.entityData.get(ID_LOYALTY);
		if (loyal > 0 && (this.remainingHit == 0 || this.isNoPhysics()) && entity != null) {
			if (!this.isAcceptibleReturnOwner()) {
				if (!this.level.isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED) {
					this.spawnAtLocation(this.getPickupItem(), 0.1F);
				}
				this.discard();
			} else {
				this.setNoPhysics(true);
				Vec3 vec3 = entity.getEyePosition().subtract(this.position());
				this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015D * (double) loyal, this.getZ());
				if (this.level.isClientSide) {
					this.yOld = this.getY();
				}
				double d0 = 0.05D * (double) loyal;
				this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vec3.normalize().scale(d0)));
				if (this.clientSideReturnTridentTickCount == 0) {
					this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
				}
				++this.clientSideReturnTridentTickCount;
			}
		}

		super.tick();
	}

	private boolean isAcceptibleReturnOwner() {
		Entity entity = this.getOwner();
		if (entity != null && entity.isAlive()) {
			return !(entity instanceof ServerPlayer) || !entity.isSpectator();
		} else {
			return false;
		}
	}

	protected ItemStack getPickupItem() {
		return this.item.copy();
	}

	public boolean isFoil() {
		return this.entityData.get(ID_FOIL);
	}

	@Nullable
	protected EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
		return this.remainingHit == 0 ? null : super.findHitEntity(pStartVec, pEndVec);
	}

	protected void onHitEntity(EntityHitResult pResult) {
		Entity entity = pResult.getEntity();
		float damage = getDamage();
		if (entity instanceof LivingEntity livingentity) {
			damage += EnchantmentHelper.getDamageBonus(this.item, livingentity.getMobType());
		}
		Entity owner = this.getOwner();
		DamageSource damagesource = DamageSource.trident(this, owner == null ? this : owner);
		if (this.remainingHit > 0) {
			this.remainingHit--;
			if (this.getPierceLevel() > 0) {
				if (this.piercingIgnoreEntityIds == null) {
					this.piercingIgnoreEntityIds = new IntOpenHashSet(5);
				}
				this.piercingIgnoreEntityIds.add(entity.getId());
			}
		}
		SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
		if (entity.hurt(damagesource, damage)) {
			if (entity.getType() == EntityType.ENDERMAN) {
				return;
			}
			if (entity instanceof LivingEntity le) {
				if (owner instanceof LivingEntity) {
					EnchantmentHelper.doPostHurtEffects(le, owner);
					EnchantmentHelper.doPostDamageEffects((LivingEntity) owner, le);
				}

				this.doPostHurtEffects(le);
			}
		}
		this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
		float f1 = 1.0F;
		this.playSound(soundevent, f1, 1.0F);
	}

	protected boolean tryPickup(Player pPlayer) {
		return super.tryPickup(pPlayer) || this.isNoPhysics() && this.ownedBy(pPlayer) && pPlayer.getInventory().add(this.getPickupItem());
	}

	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.TRIDENT_HIT_GROUND;
	}

	public void playerTouch(Player pEntity) {
		if (this.ownedBy(pEntity) || this.getOwner() == null) {
			super.playerTouch(pEntity);
		}
	}

	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		if (pCompound.contains("Item", 10)) {
			this.item = ItemStack.of(pCompound.getCompound("Item"));
		}
		this.remainingHit = pCompound.getInt("RemainingHit");
		this.entityData.set(ID_LOYALTY, (byte) EnchantmentHelper.getLoyalty(this.item));
	}

	public void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);
		pCompound.put("Item", this.item.save(new CompoundTag()));
		pCompound.putInt("RemainingHit", this.remainingHit);
	}

	public void tickDespawn() {
		int i = this.entityData.get(ID_LOYALTY);
		if (this.pickup != AbstractArrow.Pickup.ALLOWED || i <= 0) {
			super.tickDespawn();
		}
	}

	public boolean shouldRender(double pX, double pY, double pZ) {
		return true;
	}

}