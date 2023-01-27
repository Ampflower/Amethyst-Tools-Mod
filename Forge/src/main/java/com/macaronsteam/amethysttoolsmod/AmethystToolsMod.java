/**
 * Copyright © 2022 Kitoglav Licensed under the Apache License, Version 2.0
 **/
package com.macaronsteam.amethysttoolsmod;

import com.macaronsteam.amethysttoolsmod.client.events.ClientEvents;
import com.macaronsteam.amethysttoolsmod.config.AmethystToolsModConfig;
import com.macaronsteam.amethysttoolsmod.forge.ForgeRegistryFacade;
import com.macaronsteam.amethysttoolsmod.forge.ForgeTierProvider;
import com.macaronsteam.amethysttoolsmod.init.EntitiesInit;
import com.macaronsteam.amethysttoolsmod.init.ItemsInit;
import com.macaronsteam.amethysttoolsmod.init.RecipesInit;
import com.macaronsteam.amethysttoolsmod.recipe.ConfigValueCondition;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(Constants.MODID)
public class AmethystToolsMod {
  public AmethystToolsMod() {
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    AmethystToolsModConfig.setup(FMLPaths.CONFIGDIR.get());
    bus.addListener(this::setup);
    bus.addListener(this::setupClient);
    EntitiesInit.register(ForgeRegistryFacade.create(ForgeRegistries.ENTITY_TYPES, bus));
    ItemsInit.register(new ForgeTierProvider(), ForgeRegistryFacade.create(ForgeRegistries.ITEMS, bus));
    RecipesInit.register(ForgeRegistryFacade.create(ForgeRegistries.RECIPE_SERIALIZERS, bus));

    // TODO: Figure out the Fabric equivalent of this.
    CraftingHelper.register(ConfigValueCondition.Serializer.INSTANCE);
  }

  private void setup(FMLCommonSetupEvent event) {
    event.enqueueWork(ItemsInit::registerBehavior);
  }

  private void setupClient(FMLClientSetupEvent event) {
    event.enqueueWork(ClientEvents::doClientWork);
  }
}
