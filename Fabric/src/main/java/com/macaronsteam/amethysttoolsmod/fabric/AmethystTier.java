package com.macaronsteam.amethysttoolsmod.fabric;// Created 2023-27-01T20:16:29

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * @author Ampflower
 * @since ${version}
 **/
public class AmethystTier implements Tier {
    private final int level, uses, enchantmentValue;
    private final float speed, attackDamageBonus;
    private final Ingredient repairIngredient;

    AmethystTier(final int level, final int uses, final float speed, final float attackDamageBonus, final int enchantmentValue, final Ingredient repairIngredient) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamageBonus;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }
}
