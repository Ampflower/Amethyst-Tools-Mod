/**
 * Copyright © 2022 Kitoglav Licensed under the Apache License, Version 2.0
 **/
package com.macaronsteam.amethysttoolsmod.init;

import com.macaronsteam.amethysttoolsmod.config.AmethystToolsModConfig;
import com.macaronsteam.amethysttoolsmod.recipe.TippedAmethystArrowRecipe;
import com.macaronsteam.amethysttoolsmod.xplat.RegistryFacade;
import com.macaronsteam.amethysttoolsmod.xplat.RegistryObject;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;

public final class RecipesInit {
  public static RegistryObject<RecipeSerializer<?>> RECIPE_AMETHYST_TIPPED_ARROW;

  public static void register(RegistryFacade<RecipeSerializer<?>> facade) {
    RECIPE_AMETHYST_TIPPED_ARROW = facade.register("crafting_special_amethysttippedarrow", () -> new SimpleRecipeSerializer<>(TippedAmethystArrowRecipe::new), AmethystToolsModConfig.enableAmethystArrows.get(), AmethystToolsModConfig.enableExtraArrows.get());
  }
}
