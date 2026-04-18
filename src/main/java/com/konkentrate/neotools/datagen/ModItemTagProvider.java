package com.konkentrate.neotools.datagen;

import com.konkentrate.neotools.NeoTools;
import com.konkentrate.neotools.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, NeoTools.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        // ── NeoForge common tool tag ───────────────────────────────────────────
        tag(Tags.Items.TOOLS)
                .add(ModItems.COPPER_PICKAXE.get(), ModItems.COPPER_AXE.get(),
                     ModItems.COPPER_SHOVEL.get(), ModItems.COPPER_HOE.get(),
                     ModItems.COPPER_SWORD.get());

        // ── Vanilla item-type tags (used by enchantable/* tags) ───────────────
        // These are what make enchantments like Efficiency, Fortune, Silk Touch,
        // Sharpness, Looting etc. applicable to our tools.
        tag(ItemTags.PICKAXES)  .add(ModItems.COPPER_PICKAXE.get());
        tag(ItemTags.AXES)      .add(ModItems.COPPER_AXE.get());
        tag(ItemTags.SHOVELS)   .add(ModItems.COPPER_SHOVEL.get());
        tag(ItemTags.HOES)      .add(ModItems.COPPER_HOE.get());
        tag(ItemTags.SWORDS)    .add(ModItems.COPPER_SWORD.get());

        // ── Vanilla mining tags (for correct block interaction) ────────────────
        tag(ItemTags.MINING_LOOT_ENCHANTABLE)
                .add(ModItems.COPPER_PICKAXE.get(), ModItems.COPPER_AXE.get(),
                     ModItems.COPPER_SHOVEL.get(), ModItems.COPPER_HOE.get());
    }
}
