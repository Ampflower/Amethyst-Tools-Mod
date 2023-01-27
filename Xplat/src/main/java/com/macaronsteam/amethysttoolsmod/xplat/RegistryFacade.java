package com.macaronsteam.amethysttoolsmod.xplat;// Created 2023-27-01T05:35:01

import java.util.function.Supplier;

/**
 * @author Ampflower
 * @since ${version}
 **/
public interface RegistryFacade<V> {

    <T extends V> RegistryObject<T> register(final String name, final Supplier<T> supplier);

    default <T extends V> RegistryObject<T> register(final String name, final Supplier<T> supplier, final boolean... condition) {
        if(name == null || supplier == null || !XplatUtil.allTrue(condition)) {
            return RegistryObject.empty();
        }
        return register(name, supplier);
    }
}
