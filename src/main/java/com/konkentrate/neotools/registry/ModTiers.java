package com.konkentrate.neotools.registry;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

public class ModTiers {

    public static final Tier COPPER = new SimpleTier(
            ModTags.Blocks.INCORRECT_FOR_COPPER_TOOL,
            200,   // durability
            5.0f,  // mining speed
            1.5f,  // attack damage bonus
            20,    // enchantability
            () -> Ingredient.of(Tags.Items.INGOTS_COPPER)
    );

    public static final Tier FLINT = new SimpleTier(
            ModTags.Blocks.INCORRECT_FOR_FLINT_TOOL,
            50,
            3.0f,
            1.0f,
            10,
            () -> Ingredient.of(Items.FLINT)
    );

    public static final Tier BRONZE = new SimpleTier(
            ModTags.Blocks.INCORRECT_FOR_BRONZE_TOOL,
            400,   // durability  (iron = 250, slightly more)
            7.0f,  // mining speed (iron = 6.0)
            2.5f,  // attack damage bonus (iron = 2.0)
            14,    // enchantability (iron = 14)
            () -> Ingredient.of(ModTags.Items.INGOTS_BRONZE)
    );

    // NeoTools Iron — replaces vanilla iron tools.
    // Same mining reach as bronze (tuff yes, deepslate no) but better stats.
    public static final Tier NEO_IRON = new SimpleTier(
            ModTags.Blocks.INCORRECT_FOR_NEO_IRON_TOOL,
            350,   // durability (vanilla iron = 250)
            7.5f,  // mining speed (vanilla iron = 6.0)
            2.5f,  // attack damage bonus (vanilla iron = 2.0)
            9,     // enchantability (vanilla iron = 9)
            () -> Ingredient.of(Tags.Items.INGOTS_IRON)
    );

    // NeoTools Steel — sits between iron and diamond.
    // CAN mine: everything iron can, INCLUDING deepslate and all deepslate ores/variants.
    // CANNOT mine: obsidian, ancient debris, and other diamond-level blocks.
    public static final Tier STEEL = new SimpleTier(
            ModTags.Blocks.INCORRECT_FOR_STEEL_TOOL,
            600,   // durability
            8.5f,  // mining speed
            3.0f,  // attack damage bonus
            10,    // enchantability
            () -> Ingredient.of(ModTags.Items.INGOTS_STEEL)
    );

    // NeoTools Tungsten Steel — diamond-level, high durability variant.
    // CAN mine: everything diamond can, including obsidian.
    // CANNOT mine: netherite-exclusive blocks (ancient debris, netherite blocks).
    public static final Tier TUNGSTEN_STEEL = new SimpleTier(
            ModTags.Blocks.INCORRECT_FOR_TUNGSTEN_STEEL_TOOL,
            2500,  // durability (diamond = 1561, netherite = 2031)
            9.0f,  // mining speed (diamond = 8.0)
            3.5f,  // attack damage bonus (diamond = 3.0)
            10,    // enchantability (diamond = 10)
            () -> Ingredient.of(ModTags.Items.INGOTS_TUNGSTEN_STEEL)
    );

    // NeoTools Titanium — diamond-level, ultra-fast but fragile.
    // CAN mine: everything diamond can, including obsidian.
    // CANNOT mine: netherite-exclusive blocks.
    // High enchantability means Speed enchantments + upgrades can push toward insta-mine.
    public static final Tier TITANIUM = new SimpleTier(
            ModTags.Blocks.INCORRECT_FOR_TITANIUM_TOOL,
            200,   // durability (very fragile — less than iron)
            16.0f, // mining speed (gold = 12.0 — titanium blows past it)
            2.0f,  // attack damage bonus (weak — it's a speed tool, not a weapon)
            22,    // enchantability (high like gold, enables powerful efficiency/fortune)
            () -> Ingredient.of(ModTags.Items.INGOTS_TITANIUM)
    );

}