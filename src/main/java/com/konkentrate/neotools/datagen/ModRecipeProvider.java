package com.konkentrate.neotools.datagen;
import com.konkentrate.neotools.registry.ModItems;
import com.konkentrate.neotools.registry.ModTags;
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
        // ── Flint Toolset ─────────────────────────────────────────────────────
        // F = Flint   R = c:rods/wooden
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.FLINT_PICKAXE.get())
                .pattern("FFF").pattern(" R ").pattern(" R ")
                .define('F', Items.FLINT).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(out, id("flint_pickaxe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.FLINT_AXE.get())
                .pattern("FF").pattern("FR").pattern(" R")
                .define('F', Items.FLINT).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(out, id("flint_axe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.FLINT_SHOVEL.get())
                .pattern(" F").pattern(" R").pattern(" R")
                .define('F', Items.FLINT).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(out, id("flint_shovel"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.FLINT_HOE.get())
                .pattern("FF").pattern(" R").pattern(" R")
                .define('F', Items.FLINT).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(out, id("flint_hoe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.FLINT_SWORD.get())
                .pattern(" F").pattern(" F").pattern(" R")
                .define('F', Items.FLINT).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(out, id("flint_sword"));
        // ── NeoTools Iron Toolset ──────────────────────────────────────────────
        // Saved under neotools:iron_* IDs. Vanilla minecraft:iron_* recipes are
        // disabled via static condition JSONs in data/minecraft/recipe/.
        // I = Iron Ingot   R = c:rods/wooden
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.IRON_PICKAXE.get())
                .pattern("III").pattern(" R ").pattern(" R ")
                .define('I', Items.IRON_INGOT).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_iron", has(Items.IRON_INGOT))
                .save(out, id("iron_pickaxe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.IRON_AXE.get())
                .pattern("II").pattern("IR").pattern(" R")
                .define('I', Items.IRON_INGOT).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_iron", has(Items.IRON_INGOT))
                .save(out, id("iron_axe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.IRON_SHOVEL.get())
                .pattern(" I").pattern(" R").pattern(" R")
                .define('I', Items.IRON_INGOT).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_iron", has(Items.IRON_INGOT))
                .save(out, id("iron_shovel"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.IRON_HOE.get())
                .pattern("II").pattern(" R").pattern(" R")
                .define('I', Items.IRON_INGOT).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_iron", has(Items.IRON_INGOT))
                .save(out, id("iron_hoe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.IRON_SWORD.get())
                .pattern(" I").pattern(" I").pattern(" R")
                .define('I', Items.IRON_INGOT).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_iron", has(Items.IRON_INGOT))
                .save(out, id("iron_sword"));
        // ── Bronze Toolset ─────────────────────────────────────────────────────
        // B = Bronze Ingot (c:ingots/bronze tag)   R = c:rods/wooden
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.BRONZE_PICKAXE.get())
                .pattern("BBB").pattern(" R ").pattern(" R ")
                .define('B', ModTags.Items.INGOTS_BRONZE).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_bronze", has(ModTags.Items.INGOTS_BRONZE))
                .save(out, id("bronze_pickaxe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.BRONZE_AXE.get())
                .pattern("BB").pattern("BR").pattern(" R")
                .define('B', ModTags.Items.INGOTS_BRONZE).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_bronze", has(ModTags.Items.INGOTS_BRONZE))
                .save(out, id("bronze_axe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.BRONZE_SHOVEL.get())
                .pattern(" B").pattern(" R").pattern(" R")
                .define('B', ModTags.Items.INGOTS_BRONZE).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_bronze", has(ModTags.Items.INGOTS_BRONZE))
                .save(out, id("bronze_shovel"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.BRONZE_HOE.get())
                .pattern("BB").pattern(" R").pattern(" R")
                .define('B', ModTags.Items.INGOTS_BRONZE).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_bronze", has(ModTags.Items.INGOTS_BRONZE))
                .save(out, id("bronze_hoe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.BRONZE_SWORD.get())
                .pattern(" B").pattern(" B").pattern(" R")
                .define('B', ModTags.Items.INGOTS_BRONZE).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_bronze", has(ModTags.Items.INGOTS_BRONZE))
                .save(out, id("bronze_sword"));
        // ── Copper Toolset ────────────────────────────────────────────────────
        // C = Copper Ingot   R = c:rods/wooden
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_PICKAXE.get())
                .pattern("CCC").pattern(" R ").pattern(" R ")
                .define('C', Items.COPPER_INGOT).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                .save(out, id("copper_pickaxe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_AXE.get())
                .pattern("CC").pattern("CR").pattern(" R")
                .define('C', Items.COPPER_INGOT).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                .save(out, id("copper_axe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_SHOVEL.get())
                .pattern(" C").pattern(" R").pattern(" R")
                .define('C', Items.COPPER_INGOT).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                .save(out, id("copper_shovel"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_HOE.get())
                .pattern("CC").pattern(" R").pattern(" R")
                .define('C', Items.COPPER_INGOT).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                .save(out, id("copper_hoe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.COPPER_SWORD.get())
                .pattern(" C").pattern(" C").pattern(" R")
                .define('C', Items.COPPER_INGOT).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                .save(out, id("copper_sword"));
        // ── Steel Toolset ──────────────────────────────────────────────────────
        // S = Steel Ingot (c:ingots/steel tag)   R = c:rods/wooden
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.STEEL_PICKAXE.get())
                .pattern("SSS").pattern(" R ").pattern(" R ")
                .define('S', ModTags.Items.INGOTS_STEEL).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_steel", has(ModTags.Items.INGOTS_STEEL))
                .save(out, id("steel_pickaxe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.STEEL_AXE.get())
                .pattern("SS").pattern("SR").pattern(" R")
                .define('S', ModTags.Items.INGOTS_STEEL).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_steel", has(ModTags.Items.INGOTS_STEEL))
                .save(out, id("steel_axe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.STEEL_SHOVEL.get())
                .pattern(" S").pattern(" R").pattern(" R")
                .define('S', ModTags.Items.INGOTS_STEEL).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_steel", has(ModTags.Items.INGOTS_STEEL))
                .save(out, id("steel_shovel"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.STEEL_HOE.get())
                .pattern("SS").pattern(" R").pattern(" R")
                .define('S', ModTags.Items.INGOTS_STEEL).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_steel", has(ModTags.Items.INGOTS_STEEL))
                .save(out, id("steel_hoe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.STEEL_SWORD.get())
                .pattern(" S").pattern(" S").pattern(" R")
                .define('S', ModTags.Items.INGOTS_STEEL).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_steel", has(ModTags.Items.INGOTS_STEEL))
                .save(out, id("steel_sword"));
        // ── Tungsten Steel Toolset ─────────────────────────────────────────────
        // T = Tungsten Steel Ingot (c:ingots/tungsten_steel tag)   R = c:rods/wooden
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TUNGSTEN_STEEL_PICKAXE.get())
                .pattern("TTT").pattern(" R ").pattern(" R ")
                .define('T', ModTags.Items.INGOTS_TUNGSTEN_STEEL).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_tungsten_steel", has(ModTags.Items.INGOTS_TUNGSTEN_STEEL))
                .save(out, id("tungsten_steel_pickaxe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TUNGSTEN_STEEL_AXE.get())
                .pattern("TT").pattern("TR").pattern(" R")
                .define('T', ModTags.Items.INGOTS_TUNGSTEN_STEEL).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_tungsten_steel", has(ModTags.Items.INGOTS_TUNGSTEN_STEEL))
                .save(out, id("tungsten_steel_axe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TUNGSTEN_STEEL_SHOVEL.get())
                .pattern(" T").pattern(" R").pattern(" R")
                .define('T', ModTags.Items.INGOTS_TUNGSTEN_STEEL).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_tungsten_steel", has(ModTags.Items.INGOTS_TUNGSTEN_STEEL))
                .save(out, id("tungsten_steel_shovel"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TUNGSTEN_STEEL_HOE.get())
                .pattern("TT").pattern(" R").pattern(" R")
                .define('T', ModTags.Items.INGOTS_TUNGSTEN_STEEL).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_tungsten_steel", has(ModTags.Items.INGOTS_TUNGSTEN_STEEL))
                .save(out, id("tungsten_steel_hoe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.TUNGSTEN_STEEL_SWORD.get())
                .pattern(" T").pattern(" T").pattern(" R")
                .define('T', ModTags.Items.INGOTS_TUNGSTEN_STEEL).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_tungsten_steel", has(ModTags.Items.INGOTS_TUNGSTEN_STEEL))
                .save(out, id("tungsten_steel_sword"));
        // ── Titanium Toolset ───────────────────────────────────────────────────
        // I = Titanium Ingot (c:ingots/titanium tag)   R = c:rods/wooden
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TITANIUM_PICKAXE.get())
                .pattern("III").pattern(" R ").pattern(" R ")
                .define('I', ModTags.Items.INGOTS_TITANIUM).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_titanium", has(ModTags.Items.INGOTS_TITANIUM))
                .save(out, id("titanium_pickaxe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TITANIUM_AXE.get())
                .pattern("II").pattern("IR").pattern(" R")
                .define('I', ModTags.Items.INGOTS_TITANIUM).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_titanium", has(ModTags.Items.INGOTS_TITANIUM))
                .save(out, id("titanium_axe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TITANIUM_SHOVEL.get())
                .pattern(" I").pattern(" R").pattern(" R")
                .define('I', ModTags.Items.INGOTS_TITANIUM).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_titanium", has(ModTags.Items.INGOTS_TITANIUM))
                .save(out, id("titanium_shovel"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TITANIUM_HOE.get())
                .pattern("II").pattern(" R").pattern(" R")
                .define('I', ModTags.Items.INGOTS_TITANIUM).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_titanium", has(ModTags.Items.INGOTS_TITANIUM))
                .save(out, id("titanium_hoe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.TITANIUM_SWORD.get())
                .pattern(" I").pattern(" I").pattern(" R")
                .define('I', ModTags.Items.INGOTS_TITANIUM).define('R', ModTags.Items.RODS_WOODEN)
                .unlockedBy("has_titanium", has(ModTags.Items.INGOTS_TITANIUM))
                .save(out, id("titanium_sword"));
    }
    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath("neotools", path);
    }
}
