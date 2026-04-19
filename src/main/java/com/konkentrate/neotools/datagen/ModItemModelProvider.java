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

        handheldItem(ModItems.FLINT_PICKAXE.get());
        handheldItem(ModItems.FLINT_AXE.get());
        handheldItem(ModItems.FLINT_HOE.get());
        handheldItem(ModItems.FLINT_SHOVEL.get());
        handheldItem(ModItems.FLINT_SWORD.get());

        handheldItem(ModItems.BRONZE_PICKAXE.get());
        handheldItem(ModItems.BRONZE_AXE.get());
        handheldItem(ModItems.BRONZE_HOE.get());
        handheldItem(ModItems.BRONZE_SHOVEL.get());
        handheldItem(ModItems.BRONZE_SWORD.get());

        handheldItem(ModItems.IRON_PICKAXE.get());
        handheldItem(ModItems.IRON_AXE.get());
        handheldItem(ModItems.IRON_HOE.get());
        handheldItem(ModItems.IRON_SHOVEL.get());
        handheldItem(ModItems.IRON_SWORD.get());

        handheldItem(ModItems.COPPER_PICKAXE.get());
        handheldItem(ModItems.COPPER_AXE.get());
        handheldItem(ModItems.COPPER_HOE.get());
        handheldItem(ModItems.COPPER_SHOVEL.get());
        handheldItem(ModItems.COPPER_SWORD.get());

        handheldItem(ModItems.STEEL_PICKAXE.get());
        handheldItem(ModItems.STEEL_AXE.get());
        handheldItem(ModItems.STEEL_HOE.get());
        handheldItem(ModItems.STEEL_SHOVEL.get());
        handheldItem(ModItems.STEEL_SWORD.get());

        handheldItem(ModItems.TUNGSTEN_STEEL_PICKAXE.get());
        handheldItem(ModItems.TUNGSTEN_STEEL_AXE.get());
        handheldItem(ModItems.TUNGSTEN_STEEL_HOE.get());
        handheldItem(ModItems.TUNGSTEN_STEEL_SHOVEL.get());
        handheldItem(ModItems.TUNGSTEN_STEEL_SWORD.get());

        handheldItem(ModItems.TITANIUM_PICKAXE.get());
        handheldItem(ModItems.TITANIUM_AXE.get());
        handheldItem(ModItems.TITANIUM_HOE.get());
        handheldItem(ModItems.TITANIUM_SHOVEL.get());
        handheldItem(ModItems.TITANIUM_SWORD.get());

    }
}
