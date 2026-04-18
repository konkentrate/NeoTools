package com.konkentrate.neotools.datagen;

import com.konkentrate.neotools.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput out) {
        // Gemstone / coating upgrades happen at the anvil (AnvilUpgradeHandler) — no datagen needed.

        // ── Copper Toolset ────────────────────────────────────────────────────
        // C = Copper Ingot   S = Stick

        // Pickaxe:  CCC        Axe:    CC_
        //           _S_                CS_
        //           _S_                _S_
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_PICKAXE.get())
                .pattern("CCC").pattern(" S ").pattern(" S ")
                .define('C', Items.COPPER_INGOT).define('S', Items.STICK)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                .save(out, id("copper_pickaxe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_AXE.get())
                .pattern("CC").pattern("CS").pattern(" S")
                .define('C', Items.COPPER_INGOT).define('S', Items.STICK)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                .save(out, id("copper_axe"));

        // Shovel:   _C_        Hoe:    CC_
        //           _S_                _S_
        //           _S_                _S_
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_SHOVEL.get())
                .pattern(" C").pattern(" S").pattern(" S")
                .define('C', Items.COPPER_INGOT).define('S', Items.STICK)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                .save(out, id("copper_shovel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_HOE.get())
                .pattern("CC").pattern(" S").pattern(" S")
                .define('C', Items.COPPER_INGOT).define('S', Items.STICK)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                .save(out, id("copper_hoe"));

        // Sword:    _C_
        //           _C_
        //           _S_
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.COPPER_SWORD.get())
                .pattern(" C").pattern(" C").pattern(" S")
                .define('C', Items.COPPER_INGOT).define('S', Items.STICK)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                .save(out, id("copper_sword"));
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath("neotools", path);
    }
}
