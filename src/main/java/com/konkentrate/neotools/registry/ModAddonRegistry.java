package com.konkentrate.neotools.registry;

import com.konkentrate.neotools.NeoTools;
import com.konkentrate.neotools.item.component.AddonMaterial;
import com.konkentrate.neotools.item.component.AddonType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages addon types and materials loaded from datapacks.
 * Command-only system - addons are applied via /give commands.
 */
public class ModAddonRegistry {
    private static ModAddonRegistry INSTANCE;

    // Keyed by addon type ID (e.g., "neotools:gemstone")
    private final Map<ResourceLocation, AddonType> addonTypes = new HashMap<>();

    // Keyed by addon material ID (e.g., "neotools:gemstone/diamond")
    private final Map<ResourceLocation, AddonMaterial> addonMaterials = new HashMap<>();

    private ModAddonRegistry() {}

    public static ModAddonRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModAddonRegistry();
        }
        return INSTANCE;
    }

    /**
     * Load addon types from datapacks.
     */
    public void loadAddonTypes(Map<ResourceLocation, JsonElement> data, ResourceManager resourceManager) {
        addonTypes.clear();
        data.forEach((id, json) -> {
            try {
                AddonType type = AddonType.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();
                addonTypes.put(type.id(), type);
                NeoTools.LOGGER.info("✓ Loaded addon type: {}", type.id());
            } catch (Exception e) {
                NeoTools.LOGGER.error("✗ Error loading addon type '{}'", id, e);
            }
        });
        NeoTools.LOGGER.info("==> Loaded {} addon types total", addonTypes.size());
    }

    /**
     * Load addon materials from datapacks.
     */
    public void loadAddonMaterials(Map<ResourceLocation, JsonElement> data, ResourceManager resourceManager) {
        addonMaterials.clear();
        data.forEach((fileId, json) -> {
            try {
                AddonMaterial material = AddonMaterial.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();
                addonMaterials.put(material.id(), material);
                NeoTools.LOGGER.info("✓ Loaded addon material: {}", material.id());
            } catch (Exception e) {
                NeoTools.LOGGER.error("✗ Error loading addon material '{}': {}", fileId, e.getMessage());
            }
        });
        NeoTools.LOGGER.info("==> Loaded {} addon materials total", addonMaterials.size());
    }

    /**
     * Get an addon type by ID
     */
    public AddonType getAddonType(ResourceLocation typeId) {
        return addonTypes.get(typeId);
    }

    /**
     * Get an addon material by ID
     */
    public AddonMaterial getAddonMaterial(ResourceLocation materialId) {
        return addonMaterials.get(materialId);
    }

    /**
     * Check if an addon type is registered
     */
    public boolean isAddonTypeRegistered(ResourceLocation typeId) {
        return addonTypes.containsKey(typeId);
    }

    /**
     * Check if an addon material is registered
     */
    public boolean isAddonMaterialRegistered(ResourceLocation materialId) {
        return addonMaterials.containsKey(materialId);
    }

    /**
     * Get all registered addon materials
     */
    public java.util.Collection<com.konkentrate.neotools.item.component.AddonMaterial> getAddonMaterials() {
        return addonMaterials.values();
    }
}



