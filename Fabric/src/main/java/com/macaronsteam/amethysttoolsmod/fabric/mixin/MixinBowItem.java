package com.macaronsteam.amethysttoolsmod.fabric.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.macaronsteam.amethysttoolsmod.item.InfinityCompatible;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * @author Ampflower
 * @since ${version}
 **/
@Mixin(BowItem.class)
public class MixinBowItem {
    @ModifyVariable(method = "releaseUsing",
            at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/world/level/Level;isClientSide:Z"),
            ordinal = 1,
            name = "bl2"
    )
    private boolean setInfinity(MixinBowItem this, final boolean old,
                                @Local(ordinal = 0) final boolean isInfinity, @Local(ordinal = 1) final ItemStack arrows) {
        if (isInfinity && arrows.getItem() instanceof InfinityCompatible) {
            return true;
        }
        return old;
    }
}
