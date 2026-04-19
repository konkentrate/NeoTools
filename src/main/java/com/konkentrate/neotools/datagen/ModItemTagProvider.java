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
        // Ensures all tools show up in generic tool queries / loot tables.
        tag(Tags.Items.TOOLS)
                .add(ModItems.FLINT_PICKAXE.get(),        ModItems.FLINT_AXE.get(),
                     ModItems.FLINT_SHOVEL.get(),         ModItems.FLINT_HOE.get(),
                     ModItems.FLINT_SWORD.get())
                .add(ModItems.COPPER_PICKAXE.get(),       ModItems.COPPER_AXE.get(),
                     ModItems.COPPER_SHOVEL.get(),        ModItems.COPPER_HOE.get(),
                     ModItems.COPPER_SWORD.get())
                .add(ModItems.BRONZE_PICKAXE.get(),       ModItems.BRONZE_AXE.get(),
                     ModItems.BRONZE_SHOVEL.get(),        ModItems.BRONZE_HOE.get(),
                     ModItems.BRONZE_SWORD.get())
                .add(ModItems.IRON_PICKAXE.get(),         ModItems.IRON_AXE.get(),
                     ModItems.IRON_SHOVEL.get(),          ModItems.IRON_HOE.get(),
                     ModItems.IRON_SWORD.get())
                .add(ModItems.STEEL_PICKAXE.get(),        ModItems.STEEL_AXE.get(),
                     ModItems.STEEL_SHOVEL.get(),         ModItems.STEEL_HOE.get(),
                     ModItems.STEEL_SWORD.get())
                .add(ModItems.TUNGSTEN_STEEL_PICKAXE.get(), ModItems.TUNGSTEN_STEEL_AXE.get(),
                     ModItems.TUNGSTEN_STEEL_SHOVEL.get(), ModItems.TUNGSTEN_STEEL_HOE.get(),
                     ModItems.TUNGSTEN_STEEL_SWORD.get())
                .add(ModItems.TITANIUM_PICKAXE.get(),     ModItems.TITANIUM_AXE.get(),
                     ModItems.TITANIUM_SHOVEL.get(),      ModItems.TITANIUM_HOE.get(),
                     ModItems.TITANIUM_SWORD.get());

        // ── Vanilla tool-type tags ─────────────────────────────────────────────
        // These cascade into ALL enchantable/* tags automatically via vanilla's
        // tag inheritance:
        //   PICKAXES/AXES/SHOVELS/HOES → MINING_ENCHANTABLE (Efficiency)
        //                              → MINING_LOOT_ENCHANTABLE (Fortune, Silk Touch)
        //                              → DURABILITY_ENCHANTABLE (Unbreaking, Mending)
        //                              → VANISHING_ENCHANTABLE (Curse of Vanishing)
        //   SWORDS → SWORD_ENCHANTABLE (Sweeping Edge)
        //          → SHARP_WEAPON_ENCHANTABLE (Sharpness, Smite, Bane)
        //          → WEAPON_ENCHANTABLE (Looting, Knockback)
        //          → FIRE_ASPECT_ENCHANTABLE (Fire Aspect)
        //          → DURABILITY_ENCHANTABLE + VANISHING_ENCHANTABLE
        //   AXES   → SHARP_WEAPON_ENCHANTABLE too (axes can get Sharpness etc.)

        tag(ItemTags.PICKAXES)
                .add(ModItems.FLINT_PICKAXE.get(),          ModItems.COPPER_PICKAXE.get(),
                     ModItems.BRONZE_PICKAXE.get(),         ModItems.IRON_PICKAXE.get(),
                     ModItems.STEEL_PICKAXE.get(),          ModItems.TUNGSTEN_STEEL_PICKAXE.get(),
                     ModItems.TITANIUM_PICKAXE.get());

        tag(ItemTags.AXES)
                .add(ModItems.FLINT_AXE.get(),              ModItems.COPPER_AXE.get(),
                     ModItems.BRONZE_AXE.get(),             ModItems.IRON_AXE.get(),
                     ModItems.STEEL_AXE.get(),              ModItems.TUNGSTEN_STEEL_AXE.get(),
                     ModItems.TITANIUM_AXE.get());

        tag(ItemTags.SHOVELS)
                .add(ModItems.FLINT_SHOVEL.get(),           ModItems.COPPER_SHOVEL.get(),
                     ModItems.BRONZE_SHOVEL.get(),          ModItems.IRON_SHOVEL.get(),
                     ModItems.STEEL_SHOVEL.get(),           ModItems.TUNGSTEN_STEEL_SHOVEL.get(),
                     ModItems.TITANIUM_SHOVEL.get());

        tag(ItemTags.HOES)
                .add(ModItems.FLINT_HOE.get(),              ModItems.COPPER_HOE.get(),
                     ModItems.BRONZE_HOE.get(),             ModItems.IRON_HOE.get(),
                     ModItems.STEEL_HOE.get(),              ModItems.TUNGSTEN_STEEL_HOE.get(),
                     ModItems.TITANIUM_HOE.get());

        tag(ItemTags.SWORDS)
                .add(ModItems.FLINT_SWORD.get(),            ModItems.COPPER_SWORD.get(),
                     ModItems.BRONZE_SWORD.get(),           ModItems.IRON_SWORD.get(),
                     ModItems.STEEL_SWORD.get(),            ModItems.TUNGSTEN_STEEL_SWORD.get(),
                     ModItems.TITANIUM_SWORD.get());
    }
}
