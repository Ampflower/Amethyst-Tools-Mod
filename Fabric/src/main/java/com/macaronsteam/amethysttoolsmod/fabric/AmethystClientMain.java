package com.macaronsteam.amethysttoolsmod.fabric;// Created 2023-27-01T20:03:46

import com.macaronsteam.amethysttoolsmod.Constants;
import com.macaronsteam.amethysttoolsmod.client.AmethystItemColors;
import com.macaronsteam.amethysttoolsmod.client.renderer.AmethystArrowRenderer;
import com.macaronsteam.amethysttoolsmod.client.renderer.AmethystTridentBEWLR;
import com.macaronsteam.amethysttoolsmod.client.renderer.AmethystTridentRenderer;
import com.macaronsteam.amethysttoolsmod.fabric.mixin.client.AccessorEntityRenderers;
import com.macaronsteam.amethysttoolsmod.fabric.mixin.client.AccessorItemProperties;
import com.macaronsteam.amethysttoolsmod.init.EntitiesInit;
import com.macaronsteam.amethysttoolsmod.init.ItemsInit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * @author Ampflower
 * @since ${version}
 **/
public class AmethystClientMain implements ClientModInitializer {
    public static final Map<Item, CustomModel> itemToModelResourceLocation = new HashMap<>();

    public static final Set<Item> hasCustomHeldRenderer = new HashSet<>();

    @Override
    public void onInitializeClient() {
        EntitiesInit.ENTITY_AMETHYST_ARROW.ifPresent(entity -> AccessorEntityRenderers.invokeRegister(entity, ctx -> new AmethystArrowRenderer<>(ctx, "textures/models/amethyst_arrow.png")));
        EntitiesInit.ENTITY_AMETHYST_SPECTRAL_ARROW.ifPresent(entity -> AccessorEntityRenderers.invokeRegister(entity, ctx -> new AmethystArrowRenderer<>(ctx, "textures/models/amethyst_spectral_arrow.png")));
        EntitiesInit.ENTITY_AMETHYST_TRIDENT.ifPresent(entity -> AccessorEntityRenderers.invokeRegister(entity, AmethystTridentRenderer::new));

        ItemsInit.ITEM_AMETHYST_TRIDENT.ifPresent(item -> {
            AccessorItemProperties.invokeRegister(item, new ResourceLocation("throwing"), (itemstack, level, livingentity, i) -> livingentity != null && livingentity.isUsingItem() && livingentity.getUseItem() == itemstack ? 1.0F : 0.0F);

            ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new AmethystTridentReloadListener());
            BuiltinItemRendererRegistry.INSTANCE.register(item, AmethystTridentBEWLR.INSTANCE::renderByItem);

            final var slot = new ModelResourceLocation(Constants.MODID, "amethyst_trident", "inventory");
            final var held = new ModelResourceLocation(Constants.MODID, "amethyst_trident_in_hand", "inventory");
            final var model = new CustomModel(slot, held);

            itemToModelResourceLocation.put(item, model);

            ModelLoadingRegistry.INSTANCE.registerModelProvider((resourceManager, bakery) -> bakery.accept(held));

            hasCustomHeldRenderer.add(item);
        });
        ItemsInit.ITEM_AMETHYST_TIPPED_ARROW.ifPresent(item -> ColorProviderRegistry.ITEM.register(AmethystItemColors.AMETHYST_TIPPED_ARROW, item));
    }

    public record CustomModel(@NotNull ModelResourceLocation slot, @NotNull ModelResourceLocation held) {
    }

    private static class AmethystTridentReloadListener implements IdentifiableResourceReloadListener {
        private static final ResourceLocation fabricId = ResourceLocation.tryBuild(Constants.MODID, "amethyst_trident");

        @Override
        public ResourceLocation getFabricId() {
            return fabricId;
        }

        @Override
        public Collection<ResourceLocation> getFabricDependencies() {
            return Collections.singleton(ResourceReloadListenerKeys.MODELS);
        }

        @Override
        public CompletableFuture<Void> reload(final PreparationBarrier preparationBarrier, final ResourceManager resourceManager, final ProfilerFiller profilerFiller, final ProfilerFiller profilerFiller2, final Executor executor, final Executor executor2) {
            return AmethystTridentBEWLR.INSTANCE.reload(preparationBarrier, resourceManager, profilerFiller, profilerFiller2, executor, executor2);
        }
    }
}
