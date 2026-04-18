package com.konkentrate.neotools.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import java.util.Optional;

public record Coating(
        @Nullable ResourceLocation Coating
) {
    public static final Coating EMPTY = new Coating(null);

    public static final Codec<Coating> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.optionalFieldOf("Coating")
                            .forGetter(value -> Optional.ofNullable(value.Coating()))
            ).apply(instance, Coating ->
                    new Coating(Coating.orElse(null))
            )
    );

    public static final StreamCodec<ByteBuf, Coating> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC),
            value -> Optional.ofNullable(value.Coating()),
            Coating -> new Coating(Coating.orElse(null))
    );

    public boolean hasCoating() { return Coating != null; }

}
