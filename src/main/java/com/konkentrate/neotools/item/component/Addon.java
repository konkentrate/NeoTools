package com.konkentrate.neotools.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Represents a single addon on a tool.
 * An addon has:
 *   - type: the addon type identifier (e.g., "neotools:gemstone", "neotools:coating", custom mod addons)
 *   - material: the specific material of this addon (e.g., "neotools:diamond", "neotools:copper_coating")
 */
public record Addon(
        ResourceLocation type,
        ResourceLocation material
) {
    public static final Codec<Addon> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("type").forGetter(Addon::type),
                    ResourceLocation.CODEC.fieldOf("material").forGetter(Addon::material)
            ).apply(instance, Addon::new)
    );

    public static final StreamCodec<ByteBuf, Addon> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC,
            Addon::type,
            ResourceLocation.STREAM_CODEC,
            Addon::material,
            Addon::new
    );

    /**
     * Convenience constructor for quick creation
     */
    public Addon(String type, String material) {
        this(ResourceLocation.parse(type), ResourceLocation.parse(material));
    }
}

