package com.konkentrate.neotools.datagen;

import com.google.gson.JsonElement;
import com.konkentrate.neotools.item.component.UpgradeBonus;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModUpgradeBonusProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;

    public ModUpgradeBonusProvider(PackOutput output) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "tools/upgrades");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        // ==================== MINECRAFT GEMSTONES ====================
        futures.add(saveGemstone(cache, "c:gems/diamond", UpgradeBonus.builder()
                .miningSpeed(100f)
                .attackDamageMultiplier(10.0f)
                .attackSpeed(15)
                .durabilityMultiplier(10)));

        futures.add(saveGemstone(cache, "c:gems/emerald", UpgradeBonus.builder()
                .miningSpeedMultiplier(1.2f)
                .fortune(1)));

        futures.add(saveGemstone(cache, "c:gems/lapis", UpgradeBonus.builder()
                .miningSpeed(2.0f)
                .enchantability(5)));

        // ==================== MOD GEMSTONES ====================
        // saveGemstone(cache, "c:gems/your_gem", UpgradeBonus.builder()...)

        // ==================== MINECRAFT COATINGS ====================
        futures.add(saveCoating(cache, "c:ingots/netherite", UpgradeBonus.builder()
                .durabilityMultiplier(2.0f)
                .miningSpeedMultiplier(1.1f)));

        futures.add(saveCoating(cache, "minecraft:obsidian", UpgradeBonus.builder()
                .durabilityMultiplier(1.8f)));

        futures.add(saveCoating(cache, "c:ingots/gold", UpgradeBonus.builder()
                .enchantability(15)));

        // ==================== MOD COATINGS ====================
        futures.add(saveCoating(cache, "c:ingots/zinc", UpgradeBonus.builder()
                .durabilityMultiplier(1.5f)
                .miningSpeedMultiplier(1.05f)));

        futures.add(saveCoating(cache, "c:ingots/brass", UpgradeBonus.builder()
                .durabilityMultiplier(2)
                .miningSpeedMultiplier(2)));

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    // ── Save helpers ──────────────────────────────────────────────────

    /** Tag-based gemstone: e.g. "c:gems/diamond" — no leading # */
    private CompletableFuture<?> saveGemstone(CachedOutput cache, String tagId, UpgradeBonus.Builder builder) {
        ResourceLocation id = ResourceLocation.parse(tagId);
        String filename = id.getNamespace() + "/" + id.getPath().replace('/', '_');
        return save(cache, "gemstones/tags/" + filename, builder.tag(id).build());
    }

    /** Tag-based coating: e.g. "c:ingots/zinc" — no leading # */
    private CompletableFuture<?> saveCoating(CachedOutput cache, String tagId, UpgradeBonus.Builder builder) {
        ResourceLocation id = ResourceLocation.parse(tagId);
        String filename = id.getNamespace() + "/" + id.getPath().replace('/', '_');
        return save(cache, "coatings/tags/" + filename, builder.tag(id).build());
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, UpgradeBonus bonus) {
        Path outputPath = this.pathProvider.json(ResourceLocation.fromNamespaceAndPath("neotools", path));
        JsonElement json = UpgradeBonus.CODEC.encodeStart(JsonOps.INSTANCE, bonus).getOrThrow();
        return DataProvider.saveStable(cache, json, outputPath);
    }

    @Override
    public String getName() { return "Upgrade Bonuses"; }
}
