package com.konkentrate.neotools.item.component;  // FIXED: was .upgrade

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Optional;

/**
 * Represents stat bonuses and special abilities for tool upgrades.
 * All fields are optional - only include what you need in the JSON.
 */
public record UpgradeBonus(
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
        Optional<Boolean> lightsUp,
        Optional<Boolean> autoSmelt,
        Optional<Boolean> silkTouch,
        Optional<Boolean> veinMiner,
        Optional<Boolean> waterBreathing,
        Optional<Boolean> fireResistance
) {

    public static final UpgradeBonus EMPTY = new UpgradeBonus(
            Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
            Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
            Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
            Optional.empty(), Optional.empty(), Optional.empty()
    );

    public static final Codec<UpgradeBonus> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
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
                    Codec.BOOL.optionalFieldOf("lights_up").forGetter(UpgradeBonus::lightsUp),
                    Codec.BOOL.optionalFieldOf("auto_smelt").forGetter(UpgradeBonus::autoSmelt),
                    Codec.BOOL.optionalFieldOf("silk_touch").forGetter(UpgradeBonus::silkTouch),
                    Codec.BOOL.optionalFieldOf("vein_miner").forGetter(UpgradeBonus::veinMiner),
                    Codec.BOOL.optionalFieldOf("water_breathing").forGetter(UpgradeBonus::waterBreathing),
                    Codec.BOOL.optionalFieldOf("fire_resistance").forGetter(UpgradeBonus::fireResistance)
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

    public boolean hasLightsUp() { return lightsUp.orElse(false); }
    public boolean hasAutoSmelt() { return autoSmelt.orElse(false); }
    public boolean hasSilkTouch() { return silkTouch.orElse(false); }
    public boolean hasVeinMiner() { return veinMiner.orElse(false); }
    public boolean hasWaterBreathing() { return waterBreathing.orElse(false); }
    public boolean hasFireResistance() { return fireResistance.orElse(false); }

    /**
     * Combine two bonuses together.
     * Stats are added/multiplied, abilities are OR'd together.
     */
    public UpgradeBonus combine(UpgradeBonus other) {
        return new UpgradeBonus(
                // Combine stats
                combineFloat(this.miningSpeedBonus, other.miningSpeedBonus, true),
                combineFloat(this.miningSpeedMultiplier, other.miningSpeedMultiplier, false),
                combineFloat(this.attackDamageBonus, other.attackDamageBonus, true),
                combineFloat(this.attackDamageMultiplier, other.attackDamageMultiplier, false),
                combineInt(this.durabilityBonus, other.durabilityBonus, true),
                combineFloat(this.durabilityMultiplier, other.durabilityMultiplier, false),
                combineInt(this.fortuneBonus, other.fortuneBonus, true),
                combineFloat(this.experienceMultiplier, other.experienceMultiplier, false),
                combineInt(this.enchantabilityBonus, other.enchantabilityBonus, true),

                // Combine abilities (OR)
                combineBoolean(this.lightsUp, other.lightsUp),
                combineBoolean(this.autoSmelt, other.autoSmelt),
                combineBoolean(this.silkTouch, other.silkTouch),
                combineBoolean(this.veinMiner, other.veinMiner),
                combineBoolean(this.waterBreathing, other.waterBreathing),
                combineBoolean(this.fireResistance, other.fireResistance)
        );
    }

    private Optional<Float> combineFloat(Optional<Float> a, Optional<Float> b, boolean add) {
        if (a.isEmpty()) return b;
        if (b.isEmpty()) return a;
        return Optional.of(add ? a.get() + b.get() : a.get() * b.get());
    }

    private Optional<Integer> combineInt(Optional<Integer> a, Optional<Integer> b, boolean add) {
        if (a.isEmpty()) return b;
        if (b.isEmpty()) return a;
        return Optional.of(add ? a.get() + b.get() : a.get() * b.get());
    }

    private Optional<Boolean> combineBoolean(Optional<Boolean> a, Optional<Boolean> b) {
        if (a.isEmpty()) return b;
        if (b.isEmpty()) return a;
        return Optional.of(a.get() || b.get());
    }

    public boolean isEmpty() {
        return this.equals(EMPTY);
    }
}