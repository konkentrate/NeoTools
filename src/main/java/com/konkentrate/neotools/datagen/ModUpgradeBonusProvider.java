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

        /// HERE ***


        // ==================== MINECRAFT GEMSTONES ====================
        futures.add(saveGemstone(cache, Items.DIAMOND, gemstone()
                .miningSpeed(100f)
                .durabilityMultiplier(10)));

        futures.add(saveGemstone(cache, Items.EMERALD, gemstone()
                .miningSpeedMultiplier(1.2f)
                .fortune(1)));

        futures.add(saveGemstone(cache, Items.LAPIS_LAZULI, gemstone()
                .miningSpeed(2.0f)
                .enchantability(5)));

        // ==================== MOD GEMSTONES ====================
        // Exact item:  saveGemstone(cache, "modid:item_name", gemstone()...)
        // Tag-based:   saveGemstoneTag(cache, "c:gems/your_gem", gemstone()...)

        // ==================== MINECRAFT COATINGS ====================
        futures.add(saveCoating(cache, Items.NETHERITE_INGOT, coating()
                .durabilityMultiplier(2.0f)
                .miningSpeedMultiplier(1.1f)));

        futures.add(saveCoating(cache, Items.OBSIDIAN, coating()
                .durabilityMultiplier(1.8f)));

        futures.add(saveCoating(cache, Items.GOLD_INGOT, coating()
                .enchantability(15)));

        // ==================== MOD COATINGS ====================
        // Tag-based: matches zinc ingot from Create, Thermal, Immersive Engineering, etc.
        futures.add(saveCoatingTag(cache, "c:ingots/zinc", coating()
                .durabilityMultiplier(1.5f)
                .miningSpeedMultiplier(1.05f)));

        futures.add(saveCoatingTag(cache, "c:ingots/brass", coating()
                .durabilityMultiplier(2)
                .miningSpeedMultiplier(2)));

        // Exact item fallback (only used if no mod provides c:ingots/zinc):
        // futures.add(saveCoating(cache, "create:zinc_ingot", coating()...));

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    // ============
    // Save helpers
    // ============

    /** Vanilla/compile-time item by ItemLike */
    private CompletableFuture<?> saveGemstone(CachedOutput cache, ItemLike item, BonusBuilder builder) {
        return saveGemstone(cache, BuiltInRegistries.ITEM.getKey(item.asItem()).toString(), builder);
    }

    /** Mod item by "modid:path" string */
    private CompletableFuture<?> saveGemstone(CachedOutput cache, String itemId, BonusBuilder builder) {
        ResourceLocation id = ResourceLocation.parse(itemId);
        builder.item(id);
        return save(cache, "gemstones/" + id.getNamespace() + "/" + id.getPath(), builder.build());
    }

    /** Tag-based gemstone: "namespace:tag/path" — no leading # */
    private CompletableFuture<?> saveGemstoneTag(CachedOutput cache, String tagId, BonusBuilder builder) {
        ResourceLocation id = ResourceLocation.parse(tagId);
        builder.tag(id);
        // Use tag path as filename, e.g. gemstones/tags/c/gems_zinc
        String filename = id.getNamespace() + "/" + id.getPath().replace('/', '_');
        return save(cache, "gemstones/tags/" + filename, builder.build());
    }

    /** Vanilla/compile-time item by ItemLike */
    private CompletableFuture<?> saveCoating(CachedOutput cache, ItemLike item, BonusBuilder builder) {
        return saveCoating(cache, BuiltInRegistries.ITEM.getKey(item.asItem()).toString(), builder);
    }

    /** Mod item by "modid:path" string */
    private CompletableFuture<?> saveCoating(CachedOutput cache, String itemId, BonusBuilder builder) {
        ResourceLocation id = ResourceLocation.parse(itemId);
        builder.item(id);
        return save(cache, "coatings/" + id.getNamespace() + "/" + id.getPath(), builder.build());
    }

    /** Tag-based coating: "namespace:tag/path" — no leading # */
    private CompletableFuture<?> saveCoatingTag(CachedOutput cache, String tagId, BonusBuilder builder) {
        ResourceLocation id = ResourceLocation.parse(tagId);
        builder.tag(id);
        String filename = id.getNamespace() + "/" + id.getPath().replace('/', '_');
        return save(cache, "coatings/tags/" + filename, builder.build());
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

    // Fluent builder factories
    private static BonusBuilder gemstone() { return new BonusBuilder(); }
    private static BonusBuilder coating()  { return new BonusBuilder(); }

    private static class BonusBuilder {
        private Optional<ResourceLocation> item = Optional.empty();
        private Optional<ResourceLocation> tag  = Optional.empty();

        private Optional<Float>   miningSpeedBonus       = Optional.empty();
        private Optional<Float>   miningSpeedMultiplier  = Optional.empty();
        private Optional<Float>   attackDamageBonus      = Optional.empty();
        private Optional<Float>   attackDamageMultiplier = Optional.empty();
        private Optional<Integer> durabilityBonus        = Optional.empty();
        private Optional<Float>   durabilityMultiplier   = Optional.empty();
        private Optional<Integer> fortuneBonus           = Optional.empty();
        private Optional<Float>   experienceMultiplier   = Optional.empty();
        private Optional<Integer> enchantabilityBonus    = Optional.empty();
        private Optional<Boolean> autoSmelt              = Optional.empty();

        BonusBuilder item(ResourceLocation value)  { this.item = Optional.of(value); return this; }
        BonusBuilder tag(ResourceLocation value)   { this.tag  = Optional.of(value); return this; }

        BonusBuilder miningSpeed(float value)            { this.miningSpeedBonus = Optional.of(value); return this; }
        BonusBuilder miningSpeedMultiplier(float value)  { this.miningSpeedMultiplier = Optional.of(value); return this; }
        BonusBuilder attackDamage(float value)           { this.attackDamageBonus = Optional.of(value); return this; }
        BonusBuilder attackDamageMultiplier(float value) { this.attackDamageMultiplier = Optional.of(value); return this; }
        BonusBuilder durability(int value)               { this.durabilityBonus = Optional.of(value); return this; }
        BonusBuilder durabilityMultiplier(float value)   { this.durabilityMultiplier = Optional.of(value); return this; }
        BonusBuilder fortune(int value)                  { this.fortuneBonus = Optional.of(value); return this; }
        BonusBuilder experienceMultiplier(float value)   { this.experienceMultiplier = Optional.of(value); return this; }
        BonusBuilder enchantability(int value)           { this.enchantabilityBonus = Optional.of(value); return this; }
        BonusBuilder autoSmelt(boolean value)            { this.autoSmelt = Optional.of(value); return this; }

        UpgradeBonus build() {
            return new UpgradeBonus(
                    item, tag,
                    miningSpeedBonus, miningSpeedMultiplier,
                    attackDamageBonus, attackDamageMultiplier,
                    durabilityBonus, durabilityMultiplier,
                    fortuneBonus, experienceMultiplier, enchantabilityBonus,
                    autoSmelt
            );
        }
    }
}

