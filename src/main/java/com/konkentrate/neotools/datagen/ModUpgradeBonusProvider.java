package com.konkentrate.neotools.datagen;

import com.google.gson.JsonElement;
import com.konkentrate.neotools.item.component.UpgradeBonus;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        futures.add(saveGemstone(cache, Items.DIAMOND, gemstone()
                .miningSpeed(1.5f)
                .durability(100)));

        futures.add(saveGemstone(cache, Items.EMERALD, gemstone()
                .miningSpeedMultiplier(1.2f)
                .fortune(1)));

        futures.add(saveGemstone(cache, Items.LAPIS_LAZULI, gemstone()
                .miningSpeed(2.0f)
                .durabilityMultiplier(100)
                .enchantability(5)));

        // ==================== MOD GEMSTONES ====================
        // Add gems from other mods here using ModItems - format: saveGemstone(cache, ModItems.YOUR_GEM.get(), ...)
        // Example: futures.add(saveGemstone(cache, ModItems.TWILIGHT_GEM.get(), gemstone()
        //         .miningSpeed(2.0f)
        //         .enchantability(3)));

        // ==================== MINECRAFT COATINGS ====================
        futures.add(saveCoating(cache, Items.NETHERITE_INGOT, coating()
                .durabilityMultiplier(2.0f)
                .miningSpeedMultiplier(1.1f)));

        futures.add(saveCoating(cache, Items.OBSIDIAN, coating()
                .durabilityMultiplier(1.8f)));

        futures.add(saveCoating(cache, Items.GOLD_INGOT, coating()
                .enchantability(15)));


        // ==================== MOD COATINGS ====================
        // Add coatings from other mods here using ModItems - format: saveCoating(cache, ModItems.YOUR_COATING.get(), ...)
        // Example: futures.add(saveCoating(cache, ModItems.TWILIGHT_WOOD.get(), coating()
        //         .durabilityMultiplier(1.5f)));

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    // ============
    // Boiler plate
    // ============

    private CompletableFuture<?> saveGemstone(CachedOutput cache, ItemLike item, BonusBuilder builder) {
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item.asItem());
        builder.item(itemId);
        String fileName = itemId.getPath();
        return save(cache, "gemstones/" + fileName, builder.build());
    }

    private CompletableFuture<?> saveCoating(CachedOutput cache, ItemLike item, BonusBuilder builder) {
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item.asItem());
        builder.item(itemId);
        String fileName = itemId.getPath();
        return save(cache, "coatings/" + fileName, builder.build());
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, UpgradeBonus bonus) {
        Path outputPath = this.pathProvider.json(ResourceLocation.fromNamespaceAndPath("neotools", path));
        JsonElement json = UpgradeBonus.CODEC.encodeStart(JsonOps.INSTANCE, bonus).getOrThrow();
        return DataProvider.saveStable(cache, json, outputPath);
    }

    @Override
    public String getName() {
        return "Upgrade Bonuses";
    }

    // Fluent builder for easier bonus creation
    private static BonusBuilder gemstone() {
        return new BonusBuilder();
    }

    private static BonusBuilder coating() {
        return new BonusBuilder();
    }

    private static class BonusBuilder {
        private Optional<Float> miningSpeedBonus = Optional.empty();
        private Optional<Float> miningSpeedMultiplier = Optional.empty();
        private Optional<Float> attackDamageBonus = Optional.empty();
        private Optional<Float> attackDamageMultiplier = Optional.empty();
        private Optional<Integer> durabilityBonus = Optional.empty();
        private Optional<Float> durabilityMultiplier = Optional.empty();
        private Optional<Integer> fortuneBonus = Optional.empty();
        private Optional<Float> experienceMultiplier = Optional.empty();
        private Optional<Integer> enchantabilityBonus = Optional.empty();
        private Optional<Boolean> autoSmelt = Optional.empty();

        BonusBuilder miningSpeed(float value) { this.miningSpeedBonus = Optional.of(value); return this; }
        BonusBuilder miningSpeedMultiplier(float value) { this.miningSpeedMultiplier = Optional.of(value); return this; }
        BonusBuilder attackDamage(float value) { this.attackDamageBonus = Optional.of(value); return this; }
        BonusBuilder attackDamageMultiplier(float value) { this.attackDamageMultiplier = Optional.of(value); return this; }
        BonusBuilder durability(int value) { this.durabilityBonus = Optional.of(value); return this; }
        BonusBuilder durabilityMultiplier(float value) { this.durabilityMultiplier = Optional.of(value); return this; }
        BonusBuilder fortune(int value) { this.fortuneBonus = Optional.of(value); return this; }
        BonusBuilder experienceMultiplier(float value) { this.experienceMultiplier = Optional.of(value); return this; }
        BonusBuilder enchantability(int value) { this.enchantabilityBonus = Optional.of(value); return this; }
        BonusBuilder autoSmelt(boolean value) { this.autoSmelt = Optional.of(value); return this; }

        UpgradeBonus build() {
            return new UpgradeBonus(
                    item, miningSpeedBonus, miningSpeedMultiplier,
                    attackDamageBonus, attackDamageMultiplier,
                    durabilityBonus, durabilityMultiplier,
                    fortuneBonus, experienceMultiplier, enchantabilityBonus,
                    autoSmelt
            );
        }

        // ...existing code...

        // Store the item ID
        private ResourceLocation item;

        BonusBuilder item(ResourceLocation itemId) {
            this.item = itemId;
            return this;
        }
    }
}