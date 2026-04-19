package com.konkentrate.neotools.datagen;

import com.konkentrate.neotools.NeoTools;
import com.konkentrate.neotools.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, NeoTools.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        // ── Flint Toolset ─────────────────────────────────────────────────────
        // Flint tier sits between wood and stone:
        //   CAN mine: stone, cobblestone, stone-level ores
        //   CANNOT mine: deepslate, tuff, deepslate variants, and iron+ blocks

        // NEEDS_FLINT_TOOL is intentionally empty here — add blocks that
        // specifically require flint tier (and nothing below) if ever needed.
        tag(ModTags.Blocks.NEEDS_FLINT_TOOL);

        // INCORRECT_FOR_FLINT_TOOL = everything incorrect for stone
        //   + deepslate, tuff and all their variants
        tag(ModTags.Blocks.INCORRECT_FOR_FLINT_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_STONE_TOOL)
                .add(
                        // Deepslate family
                        Blocks.DEEPSLATE,
                        Blocks.COBBLED_DEEPSLATE,
                        Blocks.POLISHED_DEEPSLATE,
                        Blocks.DEEPSLATE_BRICKS,
                        Blocks.CRACKED_DEEPSLATE_BRICKS,
                        Blocks.DEEPSLATE_TILES,
                        Blocks.CRACKED_DEEPSLATE_TILES,
                        Blocks.CHISELED_DEEPSLATE,
                        Blocks.REINFORCED_DEEPSLATE,
                        Blocks.COBBLED_DEEPSLATE_SLAB,
                        Blocks.COBBLED_DEEPSLATE_STAIRS,
                        Blocks.COBBLED_DEEPSLATE_WALL,
                        Blocks.POLISHED_DEEPSLATE_SLAB,
                        Blocks.POLISHED_DEEPSLATE_STAIRS,
                        Blocks.POLISHED_DEEPSLATE_WALL,
                        Blocks.DEEPSLATE_BRICK_SLAB,
                        Blocks.DEEPSLATE_BRICK_STAIRS,
                        Blocks.DEEPSLATE_BRICK_WALL,
                        Blocks.DEEPSLATE_TILE_SLAB,
                        Blocks.DEEPSLATE_TILE_STAIRS,
                        Blocks.DEEPSLATE_TILE_WALL,
                        // Deepslate ores
                        Blocks.DEEPSLATE_COAL_ORE,
                        Blocks.DEEPSLATE_IRON_ORE,
                        Blocks.DEEPSLATE_COPPER_ORE,
                        Blocks.DEEPSLATE_GOLD_ORE,
                        Blocks.DEEPSLATE_REDSTONE_ORE,
                        Blocks.DEEPSLATE_EMERALD_ORE,
                        Blocks.DEEPSLATE_LAPIS_ORE,
                        Blocks.DEEPSLATE_DIAMOND_ORE,
                        // Tuff family
                        Blocks.TUFF,
                        Blocks.TUFF_SLAB,
                        Blocks.TUFF_STAIRS,
                        Blocks.TUFF_WALL,
                        Blocks.POLISHED_TUFF,
                        Blocks.POLISHED_TUFF_SLAB,
                        Blocks.POLISHED_TUFF_STAIRS,
                        Blocks.POLISHED_TUFF_WALL,
                        Blocks.TUFF_BRICKS,
                        Blocks.TUFF_BRICK_SLAB,
                        Blocks.TUFF_BRICK_STAIRS,
                        Blocks.TUFF_BRICK_WALL,
                        Blocks.CHISELED_TUFF,
                        Blocks.CHISELED_TUFF_BRICKS
                );

        // ── Copper Toolset ────────────────────────────────────────────────────
        // Copper tier sits between stone and iron:
        //   CAN mine: stone, cobblestone, stone-level ores (same as flint, no deepslate/tuff)
        //   CANNOT mine: deepslate, tuff, iron+ blocks
        //   CAN mine: stone, cobblestone, stone-level ores (same as flint, no deepslate/tuff)
        //   CANNOT mine: deepslate, tuff, iron+ blocks
        tag(ModTags.Blocks.NEEDS_COPPER_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_COPPER_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_STONE_TOOL)
                .remove(ModTags.Blocks.NEEDS_COPPER_TOOL)
                .add(
                        // Deepslate family
                        Blocks.DEEPSLATE,
                        Blocks.COBBLED_DEEPSLATE,
                        Blocks.POLISHED_DEEPSLATE,
                        Blocks.DEEPSLATE_BRICKS,
                        Blocks.CRACKED_DEEPSLATE_BRICKS,
                        Blocks.DEEPSLATE_TILES,
                        Blocks.CRACKED_DEEPSLATE_TILES,
                        Blocks.CHISELED_DEEPSLATE,
                        Blocks.REINFORCED_DEEPSLATE,
                        Blocks.COBBLED_DEEPSLATE_SLAB,
                        Blocks.COBBLED_DEEPSLATE_STAIRS,
                        Blocks.COBBLED_DEEPSLATE_WALL,
                        Blocks.POLISHED_DEEPSLATE_SLAB,
                        Blocks.POLISHED_DEEPSLATE_STAIRS,
                        Blocks.POLISHED_DEEPSLATE_WALL,
                        Blocks.DEEPSLATE_BRICK_SLAB,
                        Blocks.DEEPSLATE_BRICK_STAIRS,
                        Blocks.DEEPSLATE_BRICK_WALL,
                        Blocks.DEEPSLATE_TILE_SLAB,
                        Blocks.DEEPSLATE_TILE_STAIRS,
                        Blocks.DEEPSLATE_TILE_WALL,
                        // Deepslate ores
                        Blocks.DEEPSLATE_COAL_ORE,
                        Blocks.DEEPSLATE_IRON_ORE,
                        Blocks.DEEPSLATE_COPPER_ORE,
                        Blocks.DEEPSLATE_GOLD_ORE,
                        Blocks.DEEPSLATE_REDSTONE_ORE,
                        Blocks.DEEPSLATE_EMERALD_ORE,
                        Blocks.DEEPSLATE_LAPIS_ORE,
                        Blocks.DEEPSLATE_DIAMOND_ORE,
                        // Tuff family
                        Blocks.TUFF,
                        Blocks.TUFF_SLAB,
                        Blocks.TUFF_STAIRS,
                        Blocks.TUFF_WALL,
                        Blocks.POLISHED_TUFF,
                        Blocks.POLISHED_TUFF_SLAB,
                        Blocks.POLISHED_TUFF_STAIRS,
                        Blocks.POLISHED_TUFF_WALL,
                        Blocks.TUFF_BRICKS,
                        Blocks.TUFF_BRICK_SLAB,
                        Blocks.TUFF_BRICK_STAIRS,
                        Blocks.TUFF_BRICK_WALL,
                        Blocks.CHISELED_TUFF,
                        Blocks.CHISELED_TUFF_BRICKS
                );

        // ── Bronze Toolset ────────────────────────────────────────────────────
        // Bronze tier sits between copper and iron:
        //   CAN mine: tuff and tuff variants (and everything below)
        //   CANNOT mine: deepslate, deepslate ores, and diamond-level blocks
        tag(ModTags.Blocks.NEEDS_BRONZE_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_BRONZE_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .remove(ModTags.Blocks.NEEDS_BRONZE_TOOL)
                .add(
                        // Deepslate family
                        Blocks.DEEPSLATE,
                        Blocks.COBBLED_DEEPSLATE,
                        Blocks.POLISHED_DEEPSLATE,
                        Blocks.DEEPSLATE_BRICKS,
                        Blocks.CRACKED_DEEPSLATE_BRICKS,
                        Blocks.DEEPSLATE_TILES,
                        Blocks.CRACKED_DEEPSLATE_TILES,
                        Blocks.CHISELED_DEEPSLATE,
                        Blocks.REINFORCED_DEEPSLATE,
                        Blocks.COBBLED_DEEPSLATE_SLAB,
                        Blocks.COBBLED_DEEPSLATE_STAIRS,
                        Blocks.COBBLED_DEEPSLATE_WALL,
                        Blocks.POLISHED_DEEPSLATE_SLAB,
                        Blocks.POLISHED_DEEPSLATE_STAIRS,
                        Blocks.POLISHED_DEEPSLATE_WALL,
                        Blocks.DEEPSLATE_BRICK_SLAB,
                        Blocks.DEEPSLATE_BRICK_STAIRS,
                        Blocks.DEEPSLATE_BRICK_WALL,
                        Blocks.DEEPSLATE_TILE_SLAB,
                        Blocks.DEEPSLATE_TILE_STAIRS,
                        Blocks.DEEPSLATE_TILE_WALL,
                        // Deepslate ores
                        Blocks.DEEPSLATE_COAL_ORE,
                        Blocks.DEEPSLATE_IRON_ORE,
                        Blocks.DEEPSLATE_COPPER_ORE,
                        Blocks.DEEPSLATE_GOLD_ORE,
                        Blocks.DEEPSLATE_REDSTONE_ORE,
                        Blocks.DEEPSLATE_EMERALD_ORE,
                        Blocks.DEEPSLATE_LAPIS_ORE,
                        Blocks.DEEPSLATE_DIAMOND_ORE
                );

        // Lock deepslate behind iron+ picks
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(Blocks.DEEPSLATE);

        // ── NeoTools Iron Toolset ─────────────────────────────────────────────
        // Better stats than bronze; same mining reach (tuff yes, deepslate no).
        // INCORRECT_FOR_NEO_IRON_TOOL mirrors INCORRECT_FOR_BRONZE_TOOL.
        tag(ModTags.Blocks.NEEDS_NEO_IRON_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_NEO_IRON_TOOL)
                .addTag(ModTags.Blocks.INCORRECT_FOR_BRONZE_TOOL);

        // ── Steel Toolset ─────────────────────────────────────────────────────
        // Steel tier sits between iron and diamond:
        //   CAN mine: everything iron can, plus deepslate and all deepslate ores/variants.
        //   CANNOT mine: obsidian, ancient debris, and other diamond-level blocks.
        tag(ModTags.Blocks.NEEDS_STEEL_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_STEEL_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL)
                .remove(ModTags.Blocks.NEEDS_STEEL_TOOL);

        // ── Tungsten Steel Toolset ────────────────────────────────────────────
        // Diamond-level, high-durability:
        //   CAN mine: everything diamond can, including obsidian.
        //   CANNOT mine: netherite-exclusive blocks (ancient debris, netherite blocks).
        tag(ModTags.Blocks.NEEDS_TUNGSTEN_STEEL_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_TUNGSTEN_STEEL_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL)
                .remove(ModTags.Blocks.NEEDS_TUNGSTEN_STEEL_TOOL);

        // ── Titanium Toolset ──────────────────────────────────────────────────
        // Diamond-level, ultra-fast but fragile:
        //   CAN mine: everything diamond can, including obsidian.
        //   CANNOT mine: netherite-exclusive blocks.
        tag(ModTags.Blocks.NEEDS_TITANIUM_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_TITANIUM_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL)
                .remove(ModTags.Blocks.NEEDS_TITANIUM_TOOL);

    }
}
