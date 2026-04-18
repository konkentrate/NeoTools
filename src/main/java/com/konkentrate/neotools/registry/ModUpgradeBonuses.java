package com.konkentrate.neotools.registry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.konkentrate.neotools.NeoTools;
import com.konkentrate.neotools.item.component.UpgradeBonus;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages upgrade bonuses loaded from datapacks.
 */
public class ModUpgradeBonuses extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static ModUpgradeBonuses INSTANCE;

    private final Map<ResourceLocation, UpgradeBonus> gemstoneBonuses = new HashMap<>();
    private final Map<ResourceLocation, UpgradeBonus> coatingBonuses = new HashMap<>();

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

        data.forEach((id, json) -> {
            try {
                UpgradeBonus bonus = UpgradeBonus.CODEC.parse(JsonOps.INSTANCE, json)
                        .getOrThrow();

                // Use the item ID stored in the bonus itself
                String path = id.getPath();
                if (path.startsWith("gemstones/")) {
                    gemstoneBonuses.put(bonus.item(), bonus);
                    NeoTools.LOGGER.debug("Loaded gemstone: {}", bonus.item());
                } else if (path.startsWith("coatings/")) {
                    coatingBonuses.put(bonus.item(), bonus);
                    NeoTools.LOGGER.debug("Loaded coating: {}", bonus.item());
                }
            } catch (Exception e) {
                NeoTools.LOGGER.error("Error loading upgrade '{}'", id, e);
            }
        });

        NeoTools.LOGGER.info("Loaded {} gemstones, {} coatings",
                gemstoneBonuses.size(), coatingBonuses.size());
    }

    public static UpgradeBonus getGemstoneBonus(ResourceLocation gemstone) {
        return getInstance().gemstoneBonuses.getOrDefault(gemstone, UpgradeBonus.EMPTY);
    }

    public static UpgradeBonus getCoatingBonus(ResourceLocation coating) {
        return getInstance().coatingBonuses.getOrDefault(coating, UpgradeBonus.EMPTY);
    }

    public static UpgradeBonus getCombinedBonus(ResourceLocation gemstone, ResourceLocation coating) {
        UpgradeBonus total = UpgradeBonus.EMPTY;
        if (gemstone != null) total = total.combine(getGemstoneBonus(gemstone));
        if (coating != null) total = total.combine(getCoatingBonus(coating));
        return total;
    }
}