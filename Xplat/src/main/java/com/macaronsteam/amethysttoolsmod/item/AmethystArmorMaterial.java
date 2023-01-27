package com.macaronsteam.amethysttoolsmod.item;

import com.macaronsteam.amethysttoolsmod.Constants;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import static com.macaronsteam.amethysttoolsmod.config.AmethystToolsModConfig.*;

public class AmethystArmorMaterial implements ArmorMaterial {

  private final ArmorMaterial armorMaterial;

  public AmethystArmorMaterial(ArmorMaterial material) {
    this.armorMaterial = material;
  }

  @Override
  public int getDefenseForSlot(EquipmentSlot slot) {
    return armorMaterial.getDefenseForSlot(slot) + extraArmor.get();
  }

  @Override
  public int getDurabilityForSlot(EquipmentSlot slot) {
    return (int) (armorMaterial.getDurabilityForSlot(slot) * durabilityMultiplier.get());
  }

  @Override
  public int getEnchantmentValue() {
    return armorMaterial.getEnchantmentValue() + extraEnchantability.get();
  }

  @Override
  public SoundEvent getEquipSound() {
    return armorMaterial.getEquipSound();
  }

  @Override
  public float getKnockbackResistance() {
    return armorMaterial.getKnockbackResistance() + extraKR.get().floatValue();
  }

  @Override
  public String getName() {
    return Constants.MODID + ':' + armorMaterial.getName() + "_amethyst";
  }

  @Override
  public Ingredient getRepairIngredient() {
    return armorMaterial.getRepairIngredient();
  }

  @Override
  public float getToughness() {
    return armorMaterial.getToughness() + (armorMaterial.getToughness() > 0.0F ? extraToughness.get().floatValue() : 0);
  }
}
