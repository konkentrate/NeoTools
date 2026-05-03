package com.konkentrate.neotools.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

/**
 * Defines an addon type that can be applied to tools.
 * Examples: "neotools:gemstone", "neotools:coating", "custommodname:runestone", etc.
 *
 * Each addon type can have different rules (max stack, etc.)
 */
public record AddonType(
        ResourceLocation id,
        int maxStackSize
) {
    public static final Codec<AddonType> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(AddonType::id),
                    Codec.INT.optionalFieldOf("max_stack_size", 1).forGetter(AddonType::maxStackSize)
            ).apply(instance, AddonType::new)
    );
}

