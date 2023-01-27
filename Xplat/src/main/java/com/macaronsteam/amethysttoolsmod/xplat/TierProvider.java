package com.macaronsteam.amethysttoolsmod.xplat;// Created 2023-27-01T07:14:12

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * @author Ampflower
 * @since ${version}
 **/
public interface TierProvider {
    Tier buildTier(Tier tier, int level, int uses, float speed, float attackDamageBonus, int enchantmentValue,
                   Ingredient repairIngredient);

    ResourceLocation nameOf(Tier tier);
}
