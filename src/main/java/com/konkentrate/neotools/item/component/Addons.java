package com.konkentrate.neotools.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

/**
 * Holds a list of addons applied to a tool.
 * Example: A tool might have [gemstone/diamond, coating/copper]
 */
public record Addons(List<Addon> addons) {
    public static final Addons EMPTY = new Addons(List.of());

    public static final Codec<Addons> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Addon.CODEC.listOf().fieldOf("addons").forGetter(Addons::addons)
            ).apply(instance, Addons::new)
    );

    public static final StreamCodec<ByteBuf, Addons> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(java.util.ArrayList::new, Addon.STREAM_CODEC),
            Addons::addons,
            Addons::new
    );

    public boolean isEmpty() {
        return addons.isEmpty();
    }
}


