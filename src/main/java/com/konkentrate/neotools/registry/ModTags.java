package com.konkentrate.neotools.registry;

import com.konkentrate.neotools.NeoTools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        /// Block tags registry

        // COPPER
        public static final TagKey<Block> NEEDS_COPPER_TOOL = createTag("needs_copper_tool");
        public static final TagKey<Block> INCORRECT_FOR_COPPER_TOOL = createTag("incorrect_for_copper_tool");

        // FLINT
        public static final TagKey<Block> NEEDS_FLINT_TOOL = createTag("needs_flint_tool");
        public static final TagKey<Block> INCORRECT_FOR_FLINT_TOOL = createTag("incorrect_for_flint_tool");

        // BRONZE
        public static final TagKey<Block> NEEDS_BRONZE_TOOL = createTag("needs_bronze_tool");
        public static final TagKey<Block> INCORRECT_FOR_BRONZE_TOOL = createTag("incorrect_for_bronze_tool");

        // NEO IRON
        public static final TagKey<Block> NEEDS_NEO_IRON_TOOL = createTag("needs_neo_iron_tool");
        public static final TagKey<Block> INCORRECT_FOR_NEO_IRON_TOOL = createTag("incorrect_for_neo_iron_tool");

        // STEEL
        public static final TagKey<Block> NEEDS_STEEL_TOOL = createTag("needs_steel_tool");
        public static final TagKey<Block> INCORRECT_FOR_STEEL_TOOL = createTag("incorrect_for_steel_tool");

        // TUNGSTEN STEEL
        public static final TagKey<Block> NEEDS_TUNGSTEN_STEEL_TOOL = createTag("needs_tungsten_steel_tool");
        public static final TagKey<Block> INCORRECT_FOR_TUNGSTEN_STEEL_TOOL = createTag("incorrect_for_tungsten_steel_tool");

        // TITANIUM
        public static final TagKey<Block> NEEDS_TITANIUM_TOOL = createTag("needs_titanium_tool");
        public static final TagKey<Block> INCORRECT_FOR_TITANIUM_TOOL = createTag("incorrect_for_titanium_tool");


        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(NeoTools.MODID, name));
        }

    }

    public static class Items {
        /// Item tags registry

        // Used to detect bronze ingots from any mod
        public static final TagKey<Item> INGOTS_BRONZE = ItemTags.create(
                ResourceLocation.fromNamespaceAndPath("c", "ingots/bronze"));

        // Used to detect steel ingots from any mod
        public static final TagKey<Item> INGOTS_STEEL = ItemTags.create(
                ResourceLocation.fromNamespaceAndPath("c", "ingots/steel"));

        // Used to accept any wooden rod (sticks + mod equivalents)
        public static final TagKey<Item> RODS_WOODEN = ItemTags.create(
                ResourceLocation.fromNamespaceAndPath("c", "rods/wooden"));

        // Used to detect tungsten steel ingots from any mod
        public static final TagKey<Item> INGOTS_TUNGSTEN_STEEL = ItemTags.create(
                ResourceLocation.fromNamespaceAndPath("c", "ingots/tungsten_steel"));

        // Used to detect titanium ingots from any mod
        public static final TagKey<Item> INGOTS_TITANIUM = ItemTags.create(
                ResourceLocation.fromNamespaceAndPath("c", "ingots/titanium"));

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(NeoTools.MODID, name));
        }

    }
}
