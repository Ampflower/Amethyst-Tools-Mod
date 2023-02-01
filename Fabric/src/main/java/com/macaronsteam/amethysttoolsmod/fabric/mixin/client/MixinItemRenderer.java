package com.macaronsteam.amethysttoolsmod.fabric.mixin.client;// Created 2023-01-02T00:00:11

import com.macaronsteam.amethysttoolsmod.fabric.AmethystClientMain;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Referenced Netherite Plus at <a href="https://github.com/OroArmor/Netherite-Plus-Mod/blob/master/src/main/java/com/oroarmor/netherite_plus/mixin/render/ItemRendererMixin.java">ItemRendererMixin</a>
 * for inject points.
 *
 * @author Ampflower
 * @since ${version}
 **/
@Mixin(ItemRenderer.class)
public class MixinItemRenderer {
    @Shadow
    @Final
    private ItemModelShaper itemModelShaper;

    @ModifyVariable(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 0), argsOnly = true)
    private BakedModel amethysttoolsmod$onRender$supplyRenderer(final BakedModel original, final ItemStack itemStack) {
        final AmethystClientMain.CustomModel model = AmethystClientMain.itemToModelResourceLocation.get(itemStack.getItem());
        if (model != null) {
            return this.itemModelShaper.getModelManager().getModel(model.slot());
        }
        return original;
    }

    @ModifyVariable(method = "getModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemModelShaper;getItemModel(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/client/resources/model/BakedModel;", shift = At.Shift.BY, by = 2))
    private BakedModel amethysttoolsmod$onGetModel$supplyRenderer(final BakedModel original, final ItemStack itemStack) {
        final AmethystClientMain.CustomModel model = AmethystClientMain.itemToModelResourceLocation.get(itemStack.getItem());
        if (model != null) {
            return this.itemModelShaper.getModelManager().getModel(model.held());
        }
        return original;
    }
}
