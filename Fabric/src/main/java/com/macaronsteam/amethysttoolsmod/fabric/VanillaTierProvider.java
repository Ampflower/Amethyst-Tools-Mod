package com.macaronsteam.amethysttoolsmod.fabric;// Created 2023-27-01T20:14:08

import com.macaronsteam.amethysttoolsmod.xplat.TierProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Locale;

/**
 * @author Ampflower
 * @since ${version}
 **/
public class VanillaTierProvider implements TierProvider {
    @Override
    public Tier buildTier(final Tier tier, final int level, final int uses, final float speed, final float attackDamageBonus, final int enchantmentValue, final Ingredient repairIngredient) {
        return new AmethystTier(level, uses, speed, attackDamageBonus, enchantmentValue, repairIngredient);
    }

    @Override
    public ResourceLocation nameOf(final Tier tier) {
        if (tier instanceof Tiers minecraftTier) {
            return ResourceLocation.tryParse(minecraftTier.name().toLowerCase(Locale.ROOT));
        }
        return null;
    }
}
