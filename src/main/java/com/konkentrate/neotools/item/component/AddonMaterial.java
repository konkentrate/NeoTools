package com.konkentrate.neotools.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

/**
 * Defines a specific addon material and its bonuses.
 * Example: "neotools:gemstone/diamond" material gives +X mining speed
 *
 * To add new stat types:
 *   1. Add field to this record
 *   2. Add getter with default to UpgradeBonus
 *   3. Add to CODEC group
 *   4. That's it!
 */
public record AddonMaterial(
        ResourceLocation id,
        // Numeric bonuses
        Optional<Float>   miningSpeedBonus,
        Optional<Float>   miningSpeedMultiplier,
        Optional<Float>   attackDamageBonus,
        Optional<Float>   attackDamageMultiplier,
        Optional<Float>   attackSpeedBonus,
        Optional<Integer> durabilityBonus,
        Optional<Float>   durabilityMultiplier,
        Optional<Integer> fortuneBonus,
        Optional<Float>   experienceMultiplier,
        Optional<Integer> enchantabilityBonus,
        // Boolean flags
        Optional<Boolean> autoSmelt
) {
    public static final Codec<AddonMaterial> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(AddonMaterial::id),
                    Codec.FLOAT.optionalFieldOf("mining_speed_bonus").forGetter(AddonMaterial::miningSpeedBonus),
                    Codec.FLOAT.optionalFieldOf("mining_speed_multiplier").forGetter(AddonMaterial::miningSpeedMultiplier),
                    Codec.FLOAT.optionalFieldOf("attack_damage_bonus").forGetter(AddonMaterial::attackDamageBonus),
                    Codec.FLOAT.optionalFieldOf("attack_damage_multiplier").forGetter(AddonMaterial::attackDamageMultiplier),
                    Codec.FLOAT.optionalFieldOf("attack_speed_bonus").forGetter(AddonMaterial::attackSpeedBonus),
                    Codec.INT.optionalFieldOf("durability_bonus").forGetter(AddonMaterial::durabilityBonus),
                    Codec.FLOAT.optionalFieldOf("durability_multiplier").forGetter(AddonMaterial::durabilityMultiplier),
                    Codec.INT.optionalFieldOf("fortune_bonus").forGetter(AddonMaterial::fortuneBonus),
                    Codec.FLOAT.optionalFieldOf("experience_multiplier").forGetter(AddonMaterial::experienceMultiplier),
                    Codec.INT.optionalFieldOf("enchantability_bonus").forGetter(AddonMaterial::enchantabilityBonus),
                    Codec.BOOL.optionalFieldOf("auto_smelt").forGetter(AddonMaterial::autoSmelt)
            ).apply(instance, AddonMaterial::new)
    );

    /**
     * Convert this material's bonuses to an UpgradeBonus for easy combination
     */
    public UpgradeBonus toBonus() {
        return UpgradeBonus.builder()
                .miningSpeedOpt(miningSpeedBonus)
                .miningSpeedMultiplierOpt(miningSpeedMultiplier)
                .attackDamageOpt(attackDamageBonus)
                .attackDamageMultiplierOpt(attackDamageMultiplier)
                .attackSpeedOpt(attackSpeedBonus)
                .durabilityOpt(durabilityBonus)
                .durabilityMultiplierOpt(durabilityMultiplier)
                .fortuneOpt(fortuneBonus)
                .experienceMultiplierOpt(experienceMultiplier)
                .enchantabilityOpt(enchantabilityBonus)
                .autoSmeltOpt(autoSmelt)
                .build();
    }
}

