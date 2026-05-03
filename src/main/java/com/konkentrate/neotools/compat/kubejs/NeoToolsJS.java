package com.konkentrate.neotools.compat.kubejs;

import com.konkentrate.neotools.item.component.Addon;
import com.konkentrate.neotools.item.component.Addons;
import com.konkentrate.neotools.registry.ModAddonRegistry;
import com.konkentrate.neotools.registry.ModDataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * KubeJS bindings for NeoTools addon system.
 * Usage: NeoTools.applyAddon(itemStack, materialId)
 */
public class NeoToolsJS {

    /**
     * Apply an addon material to a tool, preserving all existing NBT.
     * @param tool The tool ItemStack to modify
     * @param materialId The addon material ID (e.g., "neotools:gemstone/diamond")
     * @return A new ItemStack with the addon applied, or the original if material not found
     */
    public static ItemStack applyAddon(ItemStack tool, String materialId) {
        if (tool.isEmpty()) {
            return tool;
        }

        ResourceLocation materialLoc = ResourceLocation.parse(materialId);
        var registry = ModAddonRegistry.getInstance();
        var material = registry.getAddonMaterial(materialLoc);

        if (material == null) {
            return tool;
        }

        return applyAddon(tool, materialLoc, material.id());
    }

    /**
     * Apply an addon to a tool given the material ID.
     * Preserves all existing NBT data (enchantments, durability, names, etc.)
     */
    private static ItemStack applyAddon(ItemStack tool, ResourceLocation materialId, ResourceLocation actualMaterialId) {
        // Copy tool to preserve all NBT
        ItemStack result = tool.copy();

        // Extract addon type from material ID (e.g., "gemstone" from "neotools:gemstone/diamond")
        String materialPath = actualMaterialId.getPath();
        String typeName = materialPath.split("/")[0];
        ResourceLocation typeId = ResourceLocation.fromNamespaceAndPath(actualMaterialId.getNamespace(), typeName);

        // Create addon
        Addon newAddon = new Addon(typeId, actualMaterialId);

        // Get current addons and check limits
        Addons currentAddons = result.getOrDefault(ModDataComponents.ADDONS, Addons.EMPTY);
        long countOfType = currentAddons.addons().stream().filter(a -> a.type().equals(typeId)).count();

        var registry = ModAddonRegistry.getInstance();
        var addonType = registry.getAddonType(typeId);

        // Check if we can add this addon
        if (addonType != null && countOfType >= addonType.maxStackSize()) {
            return result; // Can't add, limit reached
        }

        // Add the addon
        List<Addon> newAddonsList = new ArrayList<>(currentAddons.addons());
        newAddonsList.add(newAddon);
        result.set(ModDataComponents.ADDONS, new Addons(newAddonsList));

        return result;
    }

    /**
     * Check if an addon type can be added to a tool (respects maxStackSize)
     */
    public static boolean canAddAddon(ItemStack tool, String addonTypeId) {
        ResourceLocation typeId = ResourceLocation.parse(addonTypeId);
        Addons currentAddons = tool.getOrDefault(ModDataComponents.ADDONS, Addons.EMPTY);
        long countOfType = currentAddons.addons().stream().filter(a -> a.type().equals(typeId)).count();

        var registry = ModAddonRegistry.getInstance();
        var addonType = registry.getAddonType(typeId);

        return addonType == null || countOfType < addonType.maxStackSize();
    }
}
