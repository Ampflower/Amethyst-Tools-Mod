package com.macaronsteam.amethysttoolsmod.forge;// Created 2023-27-01T05:39:03

import com.macaronsteam.amethysttoolsmod.Constants;
import com.macaronsteam.amethysttoolsmod.xplat.RegistryFacade;
import com.macaronsteam.amethysttoolsmod.xplat.RegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

/**
 * Facade around the {@link DeferredRegister}.
 *
 * @author Ampflower
 * @since ${version}
 **/
public class ForgeRegistryFacade<V> implements RegistryFacade<V> {
    private final DeferredRegister<V> backing;

    public static <V> RegistryFacade<V> create(final IForgeRegistry<V> backing, final IEventBus bus) {
        final DeferredRegister<V> register = DeferredRegister.create(backing, Constants.MODID);
        register.register(bus);
        return new ForgeRegistryFacade<>(register);
    }

    private ForgeRegistryFacade(final DeferredRegister<V> backing) {
        this.backing = backing;
    }

    @Override
    public <T extends V> RegistryObject<T> register(final String name, final Supplier<T> supplier) {
        if(name == null || supplier == null) {
            return RegistryObject.empty();
        }
        return new RegistryObjectWrapper<>(backing.register(name, supplier));
    }

    private static class RegistryObjectWrapper<V> implements RegistryObject<V> {
        private final net.minecraftforge.registries.RegistryObject<V> backing;

        private RegistryObjectWrapper(final net.minecraftforge.registries.RegistryObject<V> backing) {
            this.backing = backing;
        }

        @Override
        public boolean isPresent() {
            return backing.isPresent();
        }

        @Override
        public V get() {
            return backing.get();
        }
    }
}
