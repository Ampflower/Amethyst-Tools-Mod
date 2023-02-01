/**
 * Copyright © 2022 Kitoglav Licensed under the Apache License, Version 2.0
 **/
package com.macaronsteam.amethysttoolsmod.fabric.recipe;

import com.google.gson.JsonObject;
import com.macaronsteam.amethysttoolsmod.Constants;
import com.macaronsteam.amethysttoolsmod.config.AmethystToolsModConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Predicate;

public class ConfigValueCondition implements Predicate<JsonObject> {
  public static final ResourceLocation NAME = new ResourceLocation(Constants.MODID, "config_value");

  @Override
  public boolean test(final JsonObject json) {
    final var key = GsonHelper.getAsString(json, "configValue");
    final var out = AmethystToolsModConfig.spec.getValues().get(key);
    if (out instanceof ForgeConfigSpec.BooleanValue bool) {
      return bool.get();
    }
    throw new AssertionError("Invalid return " + out + " for " + key);
  }
}
