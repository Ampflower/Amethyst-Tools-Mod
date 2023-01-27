package com.macaronsteam.amethysttoolsmod.xplat.mixin;// Created 2023-27-01T06:15:30

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Accessors for {@link com.macaronsteam.amethysttoolsmod.entity.ThrownAmethystTrident}.
 *
 * @author Ampflower
 * @since ${version}
 **/
@Mixin(ThrownTrident.class)
public interface AccessorThrownTridentEntity {
    @Accessor
    static EntityDataAccessor<Byte> getID_LOYALTY() { throw new AssertionError(); }

    @Accessor
    static EntityDataAccessor<Boolean> getID_FOIL() { throw new AssertionError(); }

    @Accessor
    ItemStack getTridentItem();

    @Accessor
    void setTridentItem(ItemStack stack);

    @Accessor
    void setDealtDamage(boolean value);
}
