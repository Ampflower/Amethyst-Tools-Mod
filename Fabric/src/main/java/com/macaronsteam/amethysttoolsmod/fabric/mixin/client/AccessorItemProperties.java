package com.macaronsteam.amethysttoolsmod.fabric.mixin.client;// Created 2023-27-01T20:28:25

import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * @author Ampflower
 * @since ${version}
 **/
@Mixin(ItemProperties.class)
public interface AccessorItemProperties {
    @Invoker
    static void invokeRegister(Item item, ResourceLocation resourceLocation, ClampedItemPropertyFunction clampedItemPropertyFunction) {
        throw new AssertionError();
    }
}
