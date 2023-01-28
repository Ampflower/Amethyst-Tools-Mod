package com.macaronsteam.amethysttoolsmod.client;// Created 2023-27-01T23:59:22

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.alchemy.PotionUtils;

/**
 * @author Ampflower
 * @since ${version}
 **/
public final class AmethystItemColors {
    public static final ItemColor AMETHYST_TIPPED_ARROW = (itemStack, layer) -> layer == 0 ? -1 : PotionUtils.getColor(itemStack);
}
