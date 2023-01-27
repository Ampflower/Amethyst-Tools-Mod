package com.macaronsteam.amethysttoolsmod.forge;// Created 2023-27-01T07:18:17

import com.macaronsteam.amethysttoolsmod.xplat.TierProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

/**
 * Minimal wrapping for {@link ForgeTier} and {@link TierSortingRegistry}.
 *
 * @author Ampflower
 * @since ${version}
 **/
public class ForgeTierProvider implements TierProvider {
    @Override
    public Tier buildTier(final Tier tier, final int level, final int uses, final float speed, final float attackDamageBonus, final int enchantmentValue, final Ingredient repairIngredient) {
        return new ForgeTier(level, uses, speed, attackDamageBonus, enchantmentValue, tier.getTag(), () -> repairIngredient);
    }

    @Override
    public ResourceLocation nameOf(final Tier tier) {
        return TierSortingRegistry.getName(tier);
    }
}
