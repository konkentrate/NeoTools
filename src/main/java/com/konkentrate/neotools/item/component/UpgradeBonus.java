package com.konkentrate.neotools.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

/**
 * Represents stat bonuses and special abilities for tool upgrades.
 * All fields are optional - only include what you need in the JSON.
 * Use "item" for a specific item ID, or "tag" for tag-based compat (e.g. "c:ingots/zinc").
 */
public record UpgradeBonus(
        // The item this bonus applies to (exact item match)
        Optional<ResourceLocation> item,

        // Tag-based match (e.g. "c:ingots/zinc") — matches any item with this tag
        Optional<ResourceLocation> tag,

        // Numeric stat bonuses
        Optional<Float> miningSpeedBonus,
        Optional<Float> miningSpeedMultiplier,
        Optional<Float> attackDamageBonus,
        Optional<Float> attackDamageMultiplier,
        Optional<Integer> durabilityBonus,
        Optional<Float> durabilityMultiplier,
        Optional<Integer> fortuneBonus,
        Optional<Float> experienceMultiplier,
        Optional<Integer> enchantabilityBonus,

        // Special abilities (boolean flags)
        Optional<Boolean> autoSmelt
) {

    public static final UpgradeBonus EMPTY = new UpgradeBonus(
            Optional.empty(), Optional.empty(),
            Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
            Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
            Optional.empty(), Optional.empty()
    );

    public static final Codec<UpgradeBonus> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    // Item (optional — use either "item" or "tag")
                    ResourceLocation.CODEC.optionalFieldOf("item").forGetter(UpgradeBonus::item),
                    ResourceLocation.CODEC.optionalFieldOf("tag").forGetter(UpgradeBonus::tag),

                    // Stat bonuses
                    Codec.FLOAT.optionalFieldOf("mining_speed_bonus").forGetter(UpgradeBonus::miningSpeedBonus),
                    Codec.FLOAT.optionalFieldOf("mining_speed_multiplier").forGetter(UpgradeBonus::miningSpeedMultiplier),
                    Codec.FLOAT.optionalFieldOf("attack_damage_bonus").forGetter(UpgradeBonus::attackDamageBonus),
                    Codec.FLOAT.optionalFieldOf("attack_damage_multiplier").forGetter(UpgradeBonus::attackDamageMultiplier),
                    Codec.INT.optionalFieldOf("durability_bonus").forGetter(UpgradeBonus::durabilityBonus),
                    Codec.FLOAT.optionalFieldOf("durability_multiplier").forGetter(UpgradeBonus::durabilityMultiplier),
                    Codec.INT.optionalFieldOf("fortune_bonus").forGetter(UpgradeBonus::fortuneBonus),
                    Codec.FLOAT.optionalFieldOf("experience_multiplier").forGetter(UpgradeBonus::experienceMultiplier),
                    Codec.INT.optionalFieldOf("enchantability_bonus").forGetter(UpgradeBonus::enchantabilityBonus),

                    // Special abilities
                    Codec.BOOL.optionalFieldOf("auto_smelt").forGetter(UpgradeBonus::autoSmelt)
            ).apply(instance, UpgradeBonus::new)
    );

    // Convenience getters with defaults
    public float getMiningSpeedBonus() { return miningSpeedBonus.orElse(0f); }
    public float getMiningSpeedMultiplier() { return miningSpeedMultiplier.orElse(1f); }
    public float getAttackDamageBonus() { return attackDamageBonus.orElse(0f); }
    public float getAttackDamageMultiplier() { return attackDamageMultiplier.orElse(1f); }
    public int getDurabilityBonus() { return durabilityBonus.orElse(0); }
    public float getDurabilityMultiplier() { return durabilityMultiplier.orElse(1f); }
    public int getFortuneBonus() { return fortuneBonus.orElse(0); }
    public float getExperienceMultiplier() { return experienceMultiplier.orElse(1f); }
    public int getEnchantabilityBonus() { return enchantabilityBonus.orElse(0); }
    public boolean hasAutoSmelt() { return autoSmelt.orElse(false); }

    /**
     * Combine two bonuses together.
     * Stats are added/multiplied, abilities are OR'd together.
     * Uses the first bonus's item/tag.
     */
    public UpgradeBonus combine(UpgradeBonus other) {
        return new UpgradeBonus(
                this.item,
                this.tag,
                combineFloat(this.miningSpeedBonus, other.miningSpeedBonus, true),
                combineFloat(this.miningSpeedMultiplier, other.miningSpeedMultiplier, false),
                combineFloat(this.attackDamageBonus, other.attackDamageBonus, true),
                combineFloat(this.attackDamageMultiplier, other.attackDamageMultiplier, false),
                combineInt(this.durabilityBonus, other.durabilityBonus, true),
                combineFloat(this.durabilityMultiplier, other.durabilityMultiplier, false),
                combineInt(this.fortuneBonus, other.fortuneBonus, true),
                combineFloat(this.experienceMultiplier, other.experienceMultiplier, false),
                combineInt(this.enchantabilityBonus, other.enchantabilityBonus, true),
                combineBoolean(this.autoSmelt, other.autoSmelt)
        );
    }

    private static Optional<Float> combineFloat(Optional<Float> a, Optional<Float> b, boolean add) {
        if (a.isEmpty()) return b;
        if (b.isEmpty()) return a;
        return Optional.of(add ? a.get() + b.get() : a.get() * b.get());
    }

    private static Optional<Integer> combineInt(Optional<Integer> a, Optional<Integer> b, boolean add) {
        if (a.isEmpty()) return b;
        if (b.isEmpty()) return a;
        return Optional.of(add ? a.get() + b.get() : a.get() * b.get());
    }

    private static Optional<Boolean> combineBoolean(Optional<Boolean> a, Optional<Boolean> b) {
        if (a.isEmpty()) return b;
        if (b.isEmpty()) return a;
        return Optional.of(a.get() || b.get());
    }

    public boolean isEmpty() {
        return this.equals(EMPTY);
    }
}