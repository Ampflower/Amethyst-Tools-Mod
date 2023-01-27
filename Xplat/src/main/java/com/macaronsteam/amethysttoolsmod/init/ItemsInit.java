/**
 * Copyright © 2022 Kitoglav Licensed under the Apache License, Version 2.0
 **/
package com.macaronsteam.amethysttoolsmod.init;

import com.macaronsteam.amethysttoolsmod.config.AmethystToolsModConfig;
import com.macaronsteam.amethysttoolsmod.item.*;
import com.macaronsteam.amethysttoolsmod.xplat.RegistryFacade;
import com.macaronsteam.amethysttoolsmod.xplat.RegistryObject;
import com.macaronsteam.amethysttoolsmod.xplat.TierProvider;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

import javax.management.RuntimeErrorException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.macaronsteam.amethysttoolsmod.config.AmethystToolsModConfig.*;

public final class ItemsInit {

  public static RegistryObject<Item> ITEM_AMETHYST_DUST, ITEM_AMETHYST_CLUSTER, ITEM_AMETHYST_CLUSTER_LV2, ITEM_AMETHYST_ARROW, ITEM_AMETHYST_TIPPED_ARROW, ITEM_AMETHYST_SPECTRAL_ARROW, ITEM_AMETHYST_TRIDENT;

  public static final Int2ObjectMap<ArmorMaterial> MATERIALS_CACHE = new Int2ObjectOpenHashMap<>();

  public static final Int2ObjectMap<Tier> TIERS_CACHE = new Int2ObjectOpenHashMap<>();

  private static ArmorMaterial buildArmorMaterial(ArmorMaterial material) {
    int hash = material.getName().hashCode();
    ArmorMaterial amethystMaterial;
    if (!MATERIALS_CACHE.containsKey(hash)) {
      amethystMaterial = new AmethystArmorMaterial(material);
      MATERIALS_CACHE.put(hash, amethystMaterial);
      return amethystMaterial;
    }
    return MATERIALS_CACHE.get(hash);
  }

  private static Properties buildProperties(Item input) {
    Properties properties = new Properties().tab(input.getItemCategory());
    if (input.isFireResistant())
      properties.fireResistant();
    return properties;
  }

  private static Tier buildTier(TierProvider tierProvider, Tier tier) {
    int hash = tierProvider.nameOf(tier).hashCode();
    Tier amethystTier;
    if (!TIERS_CACHE.containsKey(hash)) {
      amethystTier = tierProvider.buildTier(tier, tier.getLevel(), (int) (tier.getUses() * durabilityMultiplier.get()), tier.getSpeed() + extraDigSpeed.get().floatValue(), tier.getAttackDamageBonus() + extraAttackDamage.get().floatValue(), tier.getEnchantmentValue() + extraEnchantability.get(), tier.getRepairIngredient());
      TIERS_CACHE.put(hash, amethystTier);
      return amethystTier;
    }
    return TIERS_CACHE.get(hash);
  }

  private static boolean isItemEnabled(Item input) {
    return (Stream.of("Iron", "Diamond", "Netherite").anyMatch(str -> Registry.ITEM.getKey(input).getPath().contains(str.toLowerCase()) && ((BooleanValue) AmethystToolsModConfig.spec.getValues().get("enable" + str)).get()));
  }

  public static void register(TierProvider tierProvider, RegistryFacade<Item> facade) {
    registerAmethystEquipment(tierProvider, facade);
    ITEM_AMETHYST_DUST = facade.register("amethyst_dust", MaterialItem::new);
    ITEM_AMETHYST_CLUSTER = facade.register("amethyst_cluster", MaterialItem::new);
    ITEM_AMETHYST_CLUSTER_LV2 = facade.register("amethyst_cluster_lv2", () -> new MaterialItem(Rarity.UNCOMMON));
    ITEM_AMETHYST_ARROW = facade.register("amethyst_arrow", AmethystArrowItem::new, enableAmethystArrows.get());
    ITEM_AMETHYST_TIPPED_ARROW = facade.register("amethyst_tipped_arrow", AmethystArrowItem.TippedItem::new, enableAmethystArrows.get(), enableExtraArrows.get());
    ITEM_AMETHYST_SPECTRAL_ARROW = facade.register("amethyst_spectral_arrow", AmethystArrowItem.SpectralItem::new, enableAmethystArrows.get(), enableExtraArrows.get());
    ITEM_AMETHYST_TRIDENT = facade.register("amethyst_trident", AmethystTridentItem::new, enableAmethystTrident.get());
  }

  public static void registerAmethystEquipment(TierProvider tierProvider, RegistryFacade<Item> facade) {
    String[] types = new String[] {"sword", "pickaxe", "axe", "shovel", "hoe", "boots", "leggings", "chestplate", "helmet"};
    List<Item> list = Stream.of("iron", "diamond", "netherite").flatMap(str -> {
      String[] arr = new String[types.length];
      IntStream.range(0, types.length).forEach(i -> arr[i] = String.join("_", str, types[i]));
      return Stream.of(arr);
    }).map(name -> Registry.ITEM.get(new ResourceLocation(name))).toList();
    list.stream().filter(ItemsInit::isItemEnabled).forEach(item -> facade.register(Registry.ITEM.getKey(item).getPath() + "_amethyst", () -> ItemsInit.tryToCreate(tierProvider, item)));
  }

  public static void registerBehavior() {
    AbstractProjectileDispenseBehavior behavior = new AmethystDispenseBehavior();
    ITEM_AMETHYST_ARROW.ifPresent(item -> DispenserBlock.registerBehavior(item, behavior));
    ITEM_AMETHYST_TIPPED_ARROW.ifPresent(item -> DispenserBlock.registerBehavior(item, behavior));
    ITEM_AMETHYST_SPECTRAL_ARROW.ifPresent(item -> DispenserBlock.registerBehavior(item, behavior));
  }

  public static Item tryToCreate(TierProvider tierProvider, Item input) {
    if (input instanceof ArmorItem iof)
      return new ArmorItem(buildArmorMaterial(iof.getMaterial()), iof.getSlot(), buildProperties(input));
    else if (input instanceof TieredItem iof) {
      Object attackDamage = (iof instanceof DiggerItem iof1 ? iof1.getAttackDamage() : ((SwordItem) iof).getDamage()) - iof.getTier().getAttackDamageBonus();
      Constructor<?> constructor = input.getClass().getDeclaredConstructors()[0];
      if (constructor.getParameterTypes()[1] == int.class)
        attackDamage = ((Float) attackDamage).intValue();
      try {
        return (Item) constructor.newInstance(buildTier(tierProvider, iof.getTier()), attackDamage, (float) input.getDefaultInstance().getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_SPEED).toArray(AttributeModifier[]::new)[0].getAmount(), buildProperties(input));
      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
        throw new RuntimeErrorException(new Error(input + " instantination by reflection had failed"));
      }
    }
    return null;
  }
}
