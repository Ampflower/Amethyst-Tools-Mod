/**
 * Copyright © 2022 Kitoglav Licensed under the Apache License, Version 2.0
 **/
package com.macaronsteam.amethysttoolsmod.event;

import com.macaronsteam.amethysttoolsmod.Constants;
import com.macaronsteam.amethysttoolsmod.client.AmethystItemColors;
import com.macaronsteam.amethysttoolsmod.init.ItemsInit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class ModEvents {
  @SubscribeEvent
  @OnlyIn(Dist.CLIENT)
  public static void onColorHandlerInit(RegisterColorHandlersEvent.Item event) {
    ItemsInit.ITEM_AMETHYST_TIPPED_ARROW.ifPresent(item -> event.register(AmethystItemColors.AMETHYST_TIPPED_ARROW, item));
  }
}
