package com.macaronsteam.amethysttoolsmod.fabric.mixin.client;// Created 2023-27-01T20:25:38

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * @author Ampflower
 * @since ${version}
 **/
@Mixin(EntityRenderers.class)
public interface AccessorEntityRenderers {
    @Invoker
    static <T extends Entity> void invokeRegister(EntityType<? extends T> entityType, EntityRendererProvider<T> entityRendererProvider) {
        throw new AssertionError();
    }
}
