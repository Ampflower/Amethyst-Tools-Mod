package com.macaronsteam.amethysttoolsmod.entity;

import com.macaronsteam.amethysttoolsmod.config.AmethystToolsModConfig;
import com.macaronsteam.amethysttoolsmod.init.EntitiesInit;
import com.macaronsteam.amethysttoolsmod.xplat.mixin.AccessorThrownTridentEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownAmethystTrident extends ThrownTrident {

  public ThrownAmethystTrident(Level level) {
    super(EntitiesInit.ENTITY_AMETHYST_TRIDENT.get(), level);
  }

  public ThrownAmethystTrident(Level level, double x, double y, double z) {
    this(level);
    this.setPos(x, y, z);
  }

  public ThrownAmethystTrident(Level level, LivingEntity livingentity) {
    this(level, livingentity.getX(), livingentity.getEyeY() - 0.1F, livingentity.getZ());
    this.setOwner(livingentity);
    if (livingentity instanceof Player) {
      this.pickup = AbstractArrow.Pickup.ALLOWED;
    }
  }

  public ThrownAmethystTrident(Level level, LivingEntity livingentity, ItemStack itemstack) {
    this(level, livingentity);
    ((AccessorThrownTridentEntity) this).setTridentItem(itemstack.copy());
    this.entityData.set(AccessorThrownTridentEntity.getID_LOYALTY(), (byte) EnchantmentHelper.getLoyalty(itemstack));
    this.entityData.set(AccessorThrownTridentEntity.getID_FOIL(), itemstack.hasFoil());
  }

  @Override
  protected void onHitEntity(EntityHitResult hitresult) {
    final AccessorThrownTridentEntity self = (AccessorThrownTridentEntity) this;
    ItemStack itemstack = self.getTridentItem();
    Entity hitEntity = hitresult.getEntity();
    float f = 8.0F + AmethystToolsModConfig.arrowExtraDamage.get().floatValue();
    if (hitEntity instanceof LivingEntity livingentity) {
      f += EnchantmentHelper.getDamageBonus(itemstack, livingentity.getMobType());
    }

    Entity owningEntity = this.getOwner();
    DamageSource damagesource = DamageSource.trident(this, owningEntity == null ? this : owningEntity);
    self.setDealtDamage(true);
    SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
    if (hitEntity.hurt(damagesource, f)) {
      if (hitEntity.getType() == EntityType.ENDERMAN) {
        return;
      }

      if (hitEntity instanceof LivingEntity hitLivingEntity) {
        if (owningEntity instanceof LivingEntity owningLivingEntity) {
          EnchantmentHelper.doPostHurtEffects(hitLivingEntity, owningEntity);
          EnchantmentHelper.doPostDamageEffects(owningLivingEntity, hitLivingEntity);
        }

        this.doPostHurtEffects(hitLivingEntity);
      }
    }

    this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
    float f1 = 1.0F;
    if (this.level instanceof ServerLevel && this.level.isThundering() && this.isChanneling()) {
      BlockPos blockpos = hitEntity.blockPosition();
      if (this.level.canSeeSky(blockpos)) {
        LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(this.level);
        lightningbolt.moveTo(Vec3.atBottomCenterOf(blockpos));
        lightningbolt.setCause(owningEntity instanceof ServerPlayer ? (ServerPlayer) owningEntity : null);
        this.level.addFreshEntity(lightningbolt);
        soundevent = SoundEvents.TRIDENT_THUNDER;
        f1 = 5.0F;
      }
    }

    this.playSound(soundevent, f1, 1.0F);
  }
}
