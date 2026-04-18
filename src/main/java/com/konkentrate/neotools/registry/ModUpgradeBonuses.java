package com.konkentrate.neotools.registry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.konkentrate.neotools.NeoTools;
import com.konkentrate.neotools.item.component.UpgradeBonus;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.tags.TagKey;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Manages upgrade bonuses loaded from datapacks.
 * Supports both exact item matches ("item") and tag-based matches ("tag").
 */
public class ModUpgradeBonuses extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static ModUpgradeBonuses INSTANCE;

    // Keyed by item ResourceLocation
    private final Map<ResourceLocation, UpgradeBonus> gemstoneBonuses = new HashMap<>();
    private final Map<ResourceLocation, UpgradeBonus> coatingBonuses = new HashMap<>();

    // Keyed by tag ResourceLocation (the tag id, e.g. "c:ingots/zinc")
    private final List<UpgradeBonus> gemstoneTagBonuses = new ArrayList<>();
    private final List<UpgradeBonus> coatingTagBonuses  = new ArrayList<>();

    private ModUpgradeBonuses() {
        super(GSON, "tools/upgrades");
    }

    public static ModUpgradeBonuses getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModUpgradeBonuses();
        }
        return INSTANCE;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> data, ResourceManager resourceManager, ProfilerFiller profiler) {
        gemstoneBonuses.clear();
        coatingBonuses.clear();
        gemstoneTagBonuses.clear();
        coatingTagBonuses.clear();

        data.forEach((id, json) -> {
            try {
                UpgradeBonus bonus = UpgradeBonus.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();

                String path = id.getPath();
                boolean isGemstone = path.startsWith("gemstones/");
                boolean isCoating  = path.startsWith("coatings/");

                if (!isGemstone && !isCoating) return;

                if (bonus.tag().isPresent()) {
                    // Tag-based entry
                    if (isGemstone) {
                        gemstoneTagBonuses.add(bonus);
                        NeoTools.LOGGER.debug("Loaded gemstone tag bonus: #{}", bonus.tag().get());
                    } else {
                        coatingTagBonuses.add(bonus);
                        NeoTools.LOGGER.debug("Loaded coating tag bonus: #{}", bonus.tag().get());
                    }
                } else if (bonus.item().isPresent()) {
                    // Exact item entry
                    if (isGemstone) {
                        gemstoneBonuses.put(bonus.item().get(), bonus);
                        NeoTools.LOGGER.debug("Loaded gemstone: {}", bonus.item().get());
                    } else {
                        coatingBonuses.put(bonus.item().get(), bonus);
                        NeoTools.LOGGER.debug("Loaded coating: {}", bonus.item().get());
                    }
                } else {
                    NeoTools.LOGGER.warn("Upgrade '{}' has neither 'item' nor 'tag' — skipping", id);
                }
            } catch (Exception e) {
                NeoTools.LOGGER.error("Error loading upgrade '{}'", id, e);
            }
        });

        NeoTools.LOGGER.info("Loaded {} gemstones ({} tag), {} coatings ({} tag)",
                gemstoneBonuses.size(), gemstoneTagBonuses.size(),
                coatingBonuses.size(), coatingTagBonuses.size());
    }

    // =====================
    // Lookup helpers
    // =====================

    public static UpgradeBonus getGemstoneBonus(ResourceLocation gemstone) {
        ModUpgradeBonuses inst = getInstance();
        // 1. Exact item match
        if (inst.gemstoneBonuses.containsKey(gemstone)) {
            return inst.gemstoneBonuses.get(gemstone);
        }
        // 2. Stored value IS a tag ID (e.g. "c:gems/amethyst" stored directly in the component)
        Optional<UpgradeBonus> byTagId = inst.gemstoneTagBonuses.stream()
                .filter(b -> b.tag().isPresent() && b.tag().get().equals(gemstone))
                .findFirst();
        if (byTagId.isPresent()) return byTagId.get();
        // 3. Stored value is an item ID — check if that item carries any registered tag bonus
        return inst.gemstoneTagBonuses.stream()
                .filter(b -> b.tag().isPresent() && itemHasTag(gemstone, b.tag().get()))
                .findFirst()
                .orElse(UpgradeBonus.EMPTY);
    }

    public static UpgradeBonus getCoatingBonus(ResourceLocation coating) {
        ModUpgradeBonuses inst = getInstance();
        // 1. Exact item match
        if (inst.coatingBonuses.containsKey(coating)) {
            return inst.coatingBonuses.get(coating);
        }
        // 2. Stored value IS a tag ID (e.g. "c:ingots/zinc" stored directly in the component)
        Optional<UpgradeBonus> byTagId = inst.coatingTagBonuses.stream()
                .filter(b -> b.tag().isPresent() && b.tag().get().equals(coating))
                .findFirst();
        if (byTagId.isPresent()) return byTagId.get();
        // 3. Stored value is an item ID — check if that item carries any registered tag bonus
        return inst.coatingTagBonuses.stream()
                .filter(b -> b.tag().isPresent() && itemHasTag(coating, b.tag().get()))
                .findFirst()
                .orElse(UpgradeBonus.EMPTY);
    }

    public static UpgradeBonus getCombinedBonus(ResourceLocation gemstone, ResourceLocation coating) {
        UpgradeBonus total = UpgradeBonus.EMPTY;
        if (gemstone != null) total = total.combine(getGemstoneBonus(gemstone));
        if (coating != null)  total = total.combine(getCoatingBonus(coating));
        return total;
    }

    /**
     * Check whether the item identified by itemId carries the given tag.
     * Tag id format: "namespace:path" (without the leading #).
     */
    private static boolean itemHasTag(ResourceLocation itemId, ResourceLocation tagId) {
        return BuiltInRegistries.ITEM.getHolder(itemId)
                .map(h -> h.is(TagKey.create(net.minecraft.core.registries.Registries.ITEM, tagId)))
                .orElse(false);
    }
}