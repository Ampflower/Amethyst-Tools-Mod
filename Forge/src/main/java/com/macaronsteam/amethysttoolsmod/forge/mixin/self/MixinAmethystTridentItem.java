package com.macaronsteam.amethysttoolsmod.forge.mixin.self;// Created 2023-27-01T07:22:44

import com.macaronsteam.amethysttoolsmod.client.renderer.AmethystTridentBEWLR;
import com.macaronsteam.amethysttoolsmod.item.AmethystTridentItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.TridentItem;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Consumer;

/**
 * Self-inject to enforce compile-time constraint.
 *
 * @author Ampflower
 * @since ${version}
 **/
@Mixin(AmethystTridentItem.class)
public class MixinAmethystTridentItem extends TridentItem {
    public MixinAmethystTridentItem(final Properties p_43381_) {
        super(p_43381_);
    }

    @Override
    public void initializeClient(final Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return AmethystTridentBEWLR.INSTANCE;
            }
        });
    }
}
