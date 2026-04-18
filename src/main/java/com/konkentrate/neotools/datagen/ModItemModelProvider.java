package com.konkentrate.neotools.datagen;

import com.konkentrate.neotools.NeoTools;
import com.konkentrate.neotools.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, NeoTools.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
//        basicItem(ModItems.BISMUTH.get());
//        basicItem(ModItems.RAW_BISMUTH.get());
//
//        basicItem(ModItems.RADISH.get());
//        basicItem(ModItems.STARLIGHT_ASHES.get());
//        basicItem(ModItems.FROSTFIRE_ICE.get());
//        basicItem(ModItems.CHISEL.get());

        basicItem(ModItems.COPPER_PICKAXE.get());
        basicItem(ModItems.COPPER_AXE.get());
        basicItem(ModItems.COPPER_HOE.get());
        basicItem(ModItems.COPPER_SHOVEL.get());
        basicItem(ModItems.COPPER_SWORD.get());

    }
}
