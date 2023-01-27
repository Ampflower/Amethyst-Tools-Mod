package com.macaronsteam.amethysttoolsmod.forge.mixin.self;// Created 2023-27-01T07:56:52

import com.macaronsteam.amethysttoolsmod.item.AmethystArrowItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Self-inject to enforce compile-time constraint.
 *
 * @author Ampflower
 * @since ${version}
 **/
@Mixin({AmethystArrowItem.class, AmethystArrowItem.TippedItem.class, AmethystArrowItem.SpectralItem.class})
public class MixinAmethystArrowItem extends ArrowItem {
    public MixinAmethystArrowItem(final Properties p_40512_) {
        super(p_40512_);
    }

    @Override
    public boolean isInfinite(ItemStack itemstack, ItemStack bow, Player player) {
        return bow.getEnchantmentLevel(Enchantments.INFINITY_ARROWS) > 0;
    }
}
