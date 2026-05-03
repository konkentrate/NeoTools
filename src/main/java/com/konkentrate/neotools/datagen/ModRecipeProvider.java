package com.konkentrate.neotools.datagen;

import com.konkentrate.neotools.NeoTools;
import com.konkentrate.neotools.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

/**
 * Generates recipes that override vanilla tool recipes to craft NeoTools versions.
 * This ensures vanilla crafting tables produce addon-capable tools.
 * 
 * Run `./gradlew runData` to generate all recipes.
 */
public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        NeoTools.LOGGER.info("Generating vanilla tool recipe overrides...");

        // Wooden tools
        toolRecipes(output, "wooden",
                ItemTags.PLANKS,
                ModItems.WOODEN_PICKAXE.get(),
                ModItems.WOODEN_AXE.get(),
                ModItems.WOODEN_SHOVEL.get(),
                ModItems.WOODEN_HOE.get(),
                ModItems.WOODEN_SWORD.get());

        // Stone tools
        toolRecipes(output, "stone",
                ItemTags.STONE_TOOL_MATERIALS,
                ModItems.STONE_PICKAXE.get(),
                ModItems.STONE_AXE.get(),
                ModItems.STONE_SHOVEL.get(),
                ModItems.STONE_HOE.get(),
                ModItems.STONE_SWORD.get());

        // Iron tools
        toolRecipes(output, "iron",
                Tags.Items.INGOTS_IRON,
                ModItems.IRON_PICKAXE.get(),
                ModItems.IRON_AXE.get(),
                ModItems.IRON_SHOVEL.get(),
                ModItems.IRON_HOE.get(),
                ModItems.IRON_SWORD.get());

        // Golden tools
        toolRecipes(output, "golden",
                Tags.Items.INGOTS_GOLD,
                ModItems.GOLDEN_PICKAXE.get(),
                ModItems.GOLDEN_AXE.get(),
                ModItems.GOLDEN_SHOVEL.get(),
                ModItems.GOLDEN_HOE.get(),
                ModItems.GOLDEN_SWORD.get());

        // Diamond tools
        toolRecipes(output, "diamond",
                Tags.Items.GEMS_DIAMOND,
                ModItems.DIAMOND_PICKAXE.get(),
                ModItems.DIAMOND_AXE.get(),
                ModItems.DIAMOND_SHOVEL.get(),
                ModItems.DIAMOND_HOE.get(),
                ModItems.DIAMOND_SWORD.get());

        // Netherite tools (smithing upgrades)
        netheriteSmithing(output, ModItems.DIAMOND_PICKAXE.get(), ModItems.NETHERITE_PICKAXE.get());
        netheriteSmithing(output, ModItems.DIAMOND_AXE.get(), ModItems.NETHERITE_AXE.get());
        netheriteSmithing(output, ModItems.DIAMOND_SHOVEL.get(), ModItems.NETHERITE_SHOVEL.get());
        netheriteSmithing(output, ModItems.DIAMOND_HOE.get(), ModItems.NETHERITE_HOE.get());
        netheriteSmithing(output, ModItems.DIAMOND_SWORD.get(), ModItems.NETHERITE_SWORD.get());

        NeoTools.LOGGER.info("Generated 30 vanilla tool recipe overrides");
    }

    /**
     * Generate all 5 tool recipes for a given material tier
     */
    private void toolRecipes(RecipeOutput output, String tierName,
                            net.minecraft.tags.TagKey<Item> materialTag,
                            Item pickaxe, Item axe, Item shovel, Item hoe, Item sword) {

        // Pickaxe: XXX / #
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxe)
                .pattern("XXX")
                .pattern(" # ")
                .pattern(" # ")
                .define('X', materialTag)
                .define('#', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_material", has(materialTag))
                .save(output, "minecraft:" + tierName + "_pickaxe");  // Override vanilla recipe

        // Axe: XX / X# / #
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe)
                .pattern("XX ")
                .pattern("X# ")
                .pattern(" # ")
                .define('X', materialTag)
                .define('#', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_material", has(materialTag))
                .save(output, "minecraft:" + tierName + "_axe");

        // Shovel: X / # / #
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel)
                .pattern(" X ")
                .pattern(" # ")
                .pattern(" # ")
                .define('X', materialTag)
                .define('#', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_material", has(materialTag))
                .save(output, "minecraft:" + tierName + "_shovel");

        // Hoe: XX / # / #
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoe)
                .pattern("XX ")
                .pattern(" # ")
                .pattern(" # ")
                .define('X', materialTag)
                .define('#', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_material", has(materialTag))
                .save(output, "minecraft:" + tierName + "_hoe");

        // Sword: X / X / #
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sword)
                .pattern(" X ")
                .pattern(" X ")
                .pattern(" # ")
                .define('X', materialTag)
                .define('#', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_material", has(materialTag))
                .save(output, "minecraft:" + tierName + "_sword");
    }

    /**
     * Generate netherite smithing upgrade recipe
     */
    private void netheriteSmithing(RecipeOutput output, Item base, Item result) {
        String name = result.toString().split(":")[1]; // Extract name after "neotools:"
        
        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                Ingredient.of(base),
                Ingredient.of(Tags.Items.INGOTS_NETHERITE),
                RecipeCategory.TOOLS,
                result
        )
        .unlocks("has_netherite_ingot", has(Tags.Items.INGOTS_NETHERITE))
        .save(output, "minecraft:" + name + "_smithing");  // Override vanilla netherite upgrade
    }
}

