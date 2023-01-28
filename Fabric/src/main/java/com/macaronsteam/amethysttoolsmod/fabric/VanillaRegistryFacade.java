package com.macaronsteam.amethysttoolsmod.fabric;// Created 2023-27-01T20:06:29

import com.macaronsteam.amethysttoolsmod.Constants;
import com.macaronsteam.amethysttoolsmod.xplat.RegistryFacade;
import com.macaronsteam.amethysttoolsmod.xplat.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

/**
 * @author Ampflower
 * @since ${version}
 **/
public class VanillaRegistryFacade<V> implements RegistryFacade<V> {
    private final Registry<V> backing;

    public static <V> RegistryFacade<V> create(final Registry<V> registry) {
        return new VanillaRegistryFacade<>(registry);
    }

    private VanillaRegistryFacade(final Registry<V> backing) {
        this.backing = backing;
    }

    @Override
    public <T extends V> RegistryObject<T> register(final String name, final Supplier<T> supplier) {
        if (name == null || supplier == null) {
            return RegistryObject.empty();
        }
        final T t = Registry.register(backing, new ResourceLocation(Constants.MODID, name), supplier.get());
        return RegistryObject.wrap(t);
    }
}
