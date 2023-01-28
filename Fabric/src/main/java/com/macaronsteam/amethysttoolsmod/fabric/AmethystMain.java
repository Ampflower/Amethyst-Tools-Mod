package com.macaronsteam.amethysttoolsmod.fabric;// Created 2023-27-01T20:03:23

import com.macaronsteam.amethysttoolsmod.config.AmethystToolsModConfig;
import com.macaronsteam.amethysttoolsmod.init.EntitiesInit;
import com.macaronsteam.amethysttoolsmod.init.ItemsInit;
import com.macaronsteam.amethysttoolsmod.init.RecipesInit;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;

/**
 * @author Ampflower
 * @since ${version}
 **/
public class AmethystMain implements ModInitializer {
    @Override
    public void onInitialize() {
        AmethystToolsModConfig.setup(FabricLoader.getInstance().getConfigDir());

        EntitiesInit.register(VanillaRegistryFacade.create(Registry.ENTITY_TYPE));
        ItemsInit.register(new VanillaTierProvider(), VanillaRegistryFacade.create(Registry.ITEM));
        RecipesInit.register(VanillaRegistryFacade.create(Registry.RECIPE_SERIALIZER));

        ItemsInit.registerBehavior();
    }
}
