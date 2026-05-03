package com.konkentrate.neotools.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

/**
 * Extended addon material definition that includes a mapping to source item(s).
 * This allows the anvil/smithing to know which items can be used to apply this addon.
 */
public record AddonMaterialWithSource(
        ResourceLocation id,
        Optional<List<ResourceLocation>> sourceItems, // Items that can be used to apply this addon
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
    public static final Codec<AddonMaterialWithSource> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(AddonMaterialWithSource::id),
                    ResourceLocation.CODEC.listOf().optionalFieldOf("source_items").forGetter(AddonMaterialWithSource::sourceItems),
                    Codec.FLOAT.optionalFieldOf("mining_speed_bonus").forGetter(AddonMaterialWithSource::miningSpeedBonus),
                    Codec.FLOAT.optionalFieldOf("mining_speed_multiplier").forGetter(AddonMaterialWithSource::miningSpeedMultiplier),
                    Codec.FLOAT.optionalFieldOf("attack_damage_bonus").forGetter(AddonMaterialWithSource::attackDamageBonus),
                    Codec.FLOAT.optionalFieldOf("attack_damage_multiplier").forGetter(AddonMaterialWithSource::attackDamageMultiplier),
                    Codec.FLOAT.optionalFieldOf("attack_speed_bonus").forGetter(AddonMaterialWithSource::attackSpeedBonus),
                    Codec.INT.optionalFieldOf("durability_bonus").forGetter(AddonMaterialWithSource::durabilityBonus),
                    Codec.FLOAT.optionalFieldOf("durability_multiplier").forGetter(AddonMaterialWithSource::durabilityMultiplier),
                    Codec.INT.optionalFieldOf("fortune_bonus").forGetter(AddonMaterialWithSource::fortuneBonus),
                    Codec.FLOAT.optionalFieldOf("experience_multiplier").forGetter(AddonMaterialWithSource::experienceMultiplier),
                    Codec.INT.optionalFieldOf("enchantability_bonus").forGetter(AddonMaterialWithSource::enchantabilityBonus),
                    Codec.BOOL.optionalFieldOf("auto_smelt").forGetter(AddonMaterialWithSource::autoSmelt)
            ).apply(instance, AddonMaterialWithSource::new)
    );

    /**
     * Convert to basic AddonMaterial
     */
    public AddonMaterial toAddonMaterial() {
        return new AddonMaterial(id, miningSpeedBonus, miningSpeedMultiplier, attackDamageBonus,
                attackDamageMultiplier, attackSpeedBonus, durabilityBonus, durabilityMultiplier,
                fortuneBonus, experienceMultiplier, enchantabilityBonus, autoSmelt);
    }

    /**
     * Convert this material's bonuses to an UpgradeBonus for easy combination
     */
    public UpgradeBonus toBonus() {
        return toAddonMaterial().toBonus();
    }
}

