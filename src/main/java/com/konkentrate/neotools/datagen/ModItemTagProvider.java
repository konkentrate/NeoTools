package com.konkentrate.neotools.datagen;

import com.konkentrate.neotools.NeoTools;
import com.konkentrate.neotools.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, NeoTools.MODID, existingFileHelper);
    }

    // Convention tags (c:)
    private static final TagKey<Item> C_TOOLS                = itemTag("c", "tools");
    private static final TagKey<Item> C_ENCHANTABLES         = itemTag("c", "enchantables");
    private static final TagKey<Item> C_TOOLS_MINING         = itemTag("c", "tools/mining_tool");
    private static final TagKey<Item> C_TOOLS_AXE            = itemTag("c", "tools/axe");
    private static final TagKey<Item> C_TOOLS_SHOVEL         = itemTag("c", "tools/shovel");
    private static final TagKey<Item> C_TOOLS_HOE            = itemTag("c", "tools/hoe");
    private static final TagKey<Item> C_TOOLS_SWORD          = itemTag("c", "tools/sword");

    // Minecraft enchantable tags
    private static final TagKey<Item> ENCHANTABLE_MINING     = itemTag("minecraft", "enchantable/mining");
    private static final TagKey<Item> ENCHANTABLE_AXE        = itemTag("minecraft", "enchantable/axe");
    private static final TagKey<Item> ENCHANTABLE_SHOVEL     = itemTag("minecraft", "enchantable/shovel");
    private static final TagKey<Item> ENCHANTABLE_HOE        = itemTag("minecraft", "enchantable/hoe");
    private static final TagKey<Item> ENCHANTABLE_SWORD      = itemTag("minecraft", "enchantable/sword");
    private static final TagKey<Item> ENCHANTABLE_DURABILITY = itemTag("minecraft", "enchantable/durability");
    private static final TagKey<Item> ENCHANTABLE_VANISHING  = itemTag("minecraft", "enchantable/vanishing");

    // Pickaxe-specific
    private static final TagKey<Item> BREAKS_DECORATED_POTS      = itemTag("minecraft", "breaks_decorated_pots");
    private static final TagKey<Item> CLUSTER_MAX_HARVESTABLES   = itemTag("minecraft", "cluster_max_harvestables");

    private static TagKey<Item> itemTag(String namespace, String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        Item[] pickaxes = {
            ModItems.WOODEN_PICKAXE.get(), ModItems.STONE_PICKAXE.get(),
            ModItems.IRON_PICKAXE.get(),   ModItems.GOLDEN_PICKAXE.get(),
            ModItems.DIAMOND_PICKAXE.get(), ModItems.NETHERITE_PICKAXE.get()
        };
        Item[] axes = {
            ModItems.WOODEN_AXE.get(), ModItems.STONE_AXE.get(),
            ModItems.IRON_AXE.get(),   ModItems.GOLDEN_AXE.get(),
            ModItems.DIAMOND_AXE.get(), ModItems.NETHERITE_AXE.get()
        };
        Item[] shovels = {
            ModItems.WOODEN_SHOVEL.get(), ModItems.STONE_SHOVEL.get(),
            ModItems.IRON_SHOVEL.get(),   ModItems.GOLDEN_SHOVEL.get(),
            ModItems.DIAMOND_SHOVEL.get(), ModItems.NETHERITE_SHOVEL.get()
        };
        Item[] hoes = {
            ModItems.WOODEN_HOE.get(), ModItems.STONE_HOE.get(),
            ModItems.IRON_HOE.get(),   ModItems.GOLDEN_HOE.get(),
            ModItems.DIAMOND_HOE.get(), ModItems.NETHERITE_HOE.get()
        };
        Item[] swords = {
            ModItems.WOODEN_SWORD.get(), ModItems.STONE_SWORD.get(),
            ModItems.IRON_SWORD.get(),   ModItems.GOLDEN_SWORD.get(),
            ModItems.DIAMOND_SWORD.get(), ModItems.NETHERITE_SWORD.get()
        };
        Item[] allTools = concat(pickaxes, axes, shovels, hoes, swords);

        // Vanilla category tags
        tag(ItemTags.PICKAXES).add(pickaxes);
        tag(ItemTags.AXES).add(axes);
        tag(ItemTags.SHOVELS).add(shovels);
        tag(ItemTags.HOES).add(hoes);
        tag(ItemTags.SWORDS).add(swords);

        // Convention type subtags
        tag(C_TOOLS_MINING).add(pickaxes);
        tag(C_TOOLS_AXE).add(axes);
        tag(C_TOOLS_SHOVEL).add(shovels);
        tag(C_TOOLS_HOE).add(hoes);
        tag(C_TOOLS_SWORD).add(swords);

        // c:tools and c:enchantables — all tool types
        tag(C_TOOLS).add(allTools);
        tag(C_ENCHANTABLES).add(allTools);

        // Minecraft enchantable tags per tool type
        tag(ENCHANTABLE_MINING).add(pickaxes);
        tag(ENCHANTABLE_AXE).add(axes);
        tag(ENCHANTABLE_SHOVEL).add(shovels);
        tag(ENCHANTABLE_HOE).add(hoes);
        tag(ENCHANTABLE_SWORD).add(swords);

        // Durability and vanishing apply to all tools
        tag(ENCHANTABLE_DURABILITY).add(allTools);
        tag(ENCHANTABLE_VANISHING).add(allTools);

        // Pickaxe-specific
        tag(BREAKS_DECORATED_POTS).add(pickaxes);
        tag(CLUSTER_MAX_HARVESTABLES).add(pickaxes);
    }

    @SafeVarargs
    private static <T> T[] concat(T[]... arrays) {
        int total = 0;
        for (T[] a : arrays) total += a.length;
        @SuppressWarnings("unchecked")
        T[] result = (T[]) java.lang.reflect.Array.newInstance(arrays[0].getClass().getComponentType(), total);
        int pos = 0;
        for (T[] a : arrays) { System.arraycopy(a, 0, result, pos, a.length); pos += a.length; }
        return result;
    }
}
