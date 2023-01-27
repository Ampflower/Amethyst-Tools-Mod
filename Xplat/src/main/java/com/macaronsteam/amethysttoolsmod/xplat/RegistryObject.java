package com.macaronsteam.amethysttoolsmod.xplat;// Created 2023-27-01T05:38:03

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Ampflower
 * @since ${version}
 **/
public interface RegistryObject<V> extends Supplier<V> {
    RegistryObject<?> EMPTY = new RegistryObject<>() {
        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public Object get() {
            return null;
        }
    };

    default void ifPresent(final Consumer<V> consumer) {
        if(this.isPresent()) {
            consumer.accept(this.get());
        }
    }

    boolean isPresent();

    @SuppressWarnings("unchecked")
    static <V> RegistryObject<V> empty() {
        return (RegistryObject<V>) RegistryObject.EMPTY;
    }

    static <V> RegistryObject<V> wrap(V v) {
        return new RegistryObject<>() {
            @Override
            public boolean isPresent() {
                return true;
            }

            @Override
            public V get() {
                return v;
            }
        };
    }
}
