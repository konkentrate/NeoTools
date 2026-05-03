package com.konkentrate.neotools.datagen;

import com.konkentrate.neotools.NeoTools;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = NeoTools.MODID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Recipe Provider
        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput, lookupProvider));

        // Block and Item Tag Providers
        BlockTagsProvider blockTagsProvider = new BlockTagsProvider(packOutput, lookupProvider, NeoTools.MODID, existingFileHelper) {
            @Override
            protected void addTags(HolderLookup.Provider provider) {}
        };
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ModItemTagProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));

        // Item Model Provider
        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));

        // Upgrade Bonus Provider
        generator.addProvider(event.includeServer(), new ModUpgradeBonusProvider(packOutput));

    }
}

