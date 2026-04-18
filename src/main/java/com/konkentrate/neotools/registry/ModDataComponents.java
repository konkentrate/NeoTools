package com.konkentrate.neotools.registry;

import com.konkentrate.neotools.NeoTools;
import com.konkentrate.neotools.item.component.Gemstone;
import com.konkentrate.neotools.item.component.Coating;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModDataComponents {
    private ModDataComponents() {}

    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, NeoTools.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Gemstone>> GEMSTONE =
            DATA_COMPONENTS.registerComponentType(
                    "gemstone",
                    builder -> builder
                            .persistent(Gemstone.CODEC)
                            .networkSynchronized(Gemstone.STREAM_CODEC)
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Coating>> COATING =
            DATA_COMPONENTS.registerComponentType(
                    "coating",
                    builder -> builder
                            .persistent(Coating.CODEC)
                            .networkSynchronized(Coating.STREAM_CODEC)
            );


    public static void register(IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);
    }
}