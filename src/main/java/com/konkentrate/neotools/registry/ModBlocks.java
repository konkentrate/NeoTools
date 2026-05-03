package com.konkentrate.neotools.registry;

import com.konkentrate.neotools.NeoTools;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.level.block.Block;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NeoTools.MODID);

    /**
     * All blocks are now defined via datapacks.
     * The NeoTools mod provides only core infrastructure.
     */

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}


