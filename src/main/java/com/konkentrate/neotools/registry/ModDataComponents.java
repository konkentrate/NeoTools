package com.konkentrate.neotools.registry;

import com.konkentrate.neotools.NeoTools;
import com.konkentrate.neotools.item.component.Addons;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModDataComponents {
    private ModDataComponents() {}

    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, NeoTools.MODID);

    /**
     * Generic addon component that holds a list of addons applied to a tool.
     * Each addon has a type (e.g., "neotools:gemstone") and material (e.g., "neotools:gemstone/diamond").
     */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Addons>> ADDONS =
            DATA_COMPONENTS.registerComponentType(
                    "addons",
                    builder -> builder
                            .persistent(Addons.CODEC)
                            .networkSynchronized(Addons.STREAM_CODEC)
            );


    public static void register(IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);
    }
}