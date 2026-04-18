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

        // Define block tags for copper tools
        tag(ModTags.Blocks.NEEDS_COPPER_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_COPPER_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_STONE_TOOL)
                .remove(ModTags.Blocks.NEEDS_COPPER_TOOL);

        // Lock deepslate behind IRON+ picks
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(Blocks.DEEPSLATE);

//        tag(BlockTags.MINEABLE_WITH_PICKAXE)
//                .add(ModBlocks.BISMUTH_BLOCK.get())
//                .add(ModBlocks.BISMUTH_ORE.get())
//                .add(ModBlocks.BISMUTH_DEEPSLATE_ORE.get());
//


    }
}
