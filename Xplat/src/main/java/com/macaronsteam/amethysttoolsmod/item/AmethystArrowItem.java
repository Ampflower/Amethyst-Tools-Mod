package com.macaronsteam.amethysttoolsmod.item;

import com.macaronsteam.amethysttoolsmod.entity.AmethystArrowEntity;
import com.macaronsteam.amethysttoolsmod.entity.AmethystSpectralArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class AmethystArrowItem extends ArrowItem {
  public static class SpectralItem extends SpectralArrowItem {

    public SpectralItem() {
      super(PROPERTIES);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack itemstack, LivingEntity livingentity) {
      return new AmethystSpectralArrowEntity(level, livingentity);
    }
  }

  public static class TippedItem extends TippedArrowItem {

    public TippedItem() {
      super(PROPERTIES);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack itemstack, LivingEntity livingentity) {
      return new AmethystArrowEntity(level, itemstack, livingentity);
    }
  }

  public static final Properties PROPERTIES = new Properties().tab(CreativeModeTab.TAB_COMBAT);

  public AmethystArrowItem() {
    super(PROPERTIES);
  }

  @Override
  public AbstractArrow createArrow(Level level, ItemStack itemstack, LivingEntity livingentity) {
    return new AmethystArrowEntity(level, itemstack, livingentity);
  }
}
