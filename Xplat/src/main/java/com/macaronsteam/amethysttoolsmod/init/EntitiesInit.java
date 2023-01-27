/**
 * Copyright © 2022 Kitoglav Licensed under the Apache License, Version 2.0
 **/
package com.macaronsteam.amethysttoolsmod.init;

import com.macaronsteam.amethysttoolsmod.Constants;
import com.macaronsteam.amethysttoolsmod.config.AmethystToolsModConfig;
import com.macaronsteam.amethysttoolsmod.entity.AmethystArrowEntity;
import com.macaronsteam.amethysttoolsmod.entity.AmethystSpectralArrowEntity;
import com.macaronsteam.amethysttoolsmod.entity.ThrownAmethystTrident;
import com.macaronsteam.amethysttoolsmod.xplat.RegistryFacade;
import com.macaronsteam.amethysttoolsmod.xplat.RegistryObject;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class EntitiesInit {
  public static RegistryObject<EntityType<AmethystArrowEntity>> ENTITY_AMETHYST_ARROW;
  public static RegistryObject<EntityType<AmethystSpectralArrowEntity>> ENTITY_AMETHYST_SPECTRAL_ARROW;
  public static RegistryObject<EntityType<ThrownAmethystTrident>> ENTITY_AMETHYST_TRIDENT;

  public static void register(RegistryFacade<EntityType<?>> facade) {
    ENTITY_AMETHYST_ARROW = facade.register("amethyst_arrow_entity", () -> EntityType.Builder.<AmethystArrowEntity>of(AmethystArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build(Constants.MODID + ":amethyst_arrow_entity"), AmethystToolsModConfig.enableAmethystArrows.get());
    ENTITY_AMETHYST_SPECTRAL_ARROW = facade.register("amethyst_spectral_arrow_entity", () -> EntityType.Builder.<AmethystSpectralArrowEntity>of(AmethystSpectralArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build(Constants.MODID + ":amethyst_spectral_arrow_entity"), AmethystToolsModConfig.enableAmethystArrows.get(), AmethystToolsModConfig.enableExtraArrows.get());
    ENTITY_AMETHYST_TRIDENT = facade.register("amethyst_trident_entity", () -> EntityType.Builder.<ThrownAmethystTrident>of((type, level) -> new ThrownAmethystTrident(level), MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build(Constants.MODID + ":amethyst_trident_entity"));
  }
}
