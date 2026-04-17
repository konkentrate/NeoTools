package com.konkentrate.neotools.item;

import com.konkentrate.neotools.NeoTools;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NeoTools.MODID);

    public static final Supplier<PickaxeItem> COPPER_PICKAXE = ITEMS.register("copper_pickaxe",
            () -> new PickaxeItem(
                    ModTiers.COPPER,
                    new Item.Properties()
                            .attributes(PickaxeItem.createAttributes(
                                    ModTiers.COPPER,
                                    1.0f,
                                    -2.8f
                            ))
            )
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}