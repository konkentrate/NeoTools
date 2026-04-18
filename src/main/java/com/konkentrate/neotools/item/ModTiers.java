package com.konkentrate.neotools.item;

import com.konkentrate.neotools.util.ModTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Tier;
import net.neoforged.neoforge.common.SimpleTier;
import net.minecraft.world.item.crafting.Ingredient;

public class ModTiers {

    public static final Tier COPPER = new SimpleTier(
            ModTags.Blocks.INCORRECT_FOR_COPPER_TOOL,
            200,   // durability
            5.0f,  // mining speed
            1.5f,  // attack damage bonus
            20,    // enchantability
            () -> Ingredient.of(ItemTags.create(
                    net.minecraft.resources.ResourceLocation.withDefaultNamespace("copper_ingots")
            ))
    );



}