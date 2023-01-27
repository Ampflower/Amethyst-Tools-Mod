package com.macaronsteam.amethysttoolsmod.xplat;// Created 2023-27-01T05:51:44

/**
 * @author Ampflower
 * @since ${version}
 **/
public final class XplatUtil {
    public static boolean allTrue(final boolean[] array) {
        for(final boolean value : array) {
            if(!value) {
                return false;
            }
        }
        return true;
    }
}
