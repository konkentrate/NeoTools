package com.konkentrate.neotools.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import java.util.Optional;

public record Gemstone(
        @Nullable ResourceLocation gemstone
        ) {
    public static final Gemstone EMPTY = new Gemstone(null);

    public static final Codec<Gemstone> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.optionalFieldOf("gemstone")
                            .forGetter(value -> Optional.ofNullable(value.gemstone()))
            ).apply(instance, gemstone ->
                    new Gemstone(gemstone.orElse(null))
            )
    );

    public static final StreamCodec<ByteBuf, Gemstone> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC),
            value -> Optional.ofNullable(value.gemstone()),
            gemstone -> new Gemstone(gemstone.orElse(null))
    );

    public boolean hasGemstone() { return gemstone != null; }

}
