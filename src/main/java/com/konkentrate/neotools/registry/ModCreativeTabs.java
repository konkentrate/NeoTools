package com.konkentrate.neotools.registry;

import com.konkentrate.neotools.NeoTools;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NeoTools.MODID);

    public static final Supplier<CreativeModeTab> NEOTOOLS_TAB = CREATIVE_TABS.register("neotools_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.neotools"))
                    .icon(() -> new ItemStack(ModItems.DIAMOND_PICKAXE.get()))
                    .displayItems((parameters, output) -> {
                        // All vanilla tools replaced with NeoTools versions
                        // Organized by tier for easy access

                        // Wooden Tier
                        output.accept(ModItems.WOODEN_PICKAXE.get());
                        output.accept(ModItems.WOODEN_AXE.get());
                        output.accept(ModItems.WOODEN_SHOVEL.get());
                        output.accept(ModItems.WOODEN_HOE.get());
                        output.accept(ModItems.WOODEN_SWORD.get());

                        // Stone Tier
                        output.accept(ModItems.STONE_PICKAXE.get());
                        output.accept(ModItems.STONE_AXE.get());
                        output.accept(ModItems.STONE_SHOVEL.get());
                        output.accept(ModItems.STONE_HOE.get());
                        output.accept(ModItems.STONE_SWORD.get());

                        // Iron Tier
                        output.accept(ModItems.IRON_PICKAXE.get());
                        output.accept(ModItems.IRON_AXE.get());
                        output.accept(ModItems.IRON_SHOVEL.get());
                        output.accept(ModItems.IRON_HOE.get());
                        output.accept(ModItems.IRON_SWORD.get());

                        // Golden Tier
                        output.accept(ModItems.GOLDEN_PICKAXE.get());
                        output.accept(ModItems.GOLDEN_AXE.get());
                        output.accept(ModItems.GOLDEN_SHOVEL.get());
                        output.accept(ModItems.GOLDEN_HOE.get());
                        output.accept(ModItems.GOLDEN_SWORD.get());

                        // Diamond Tier
                        output.accept(ModItems.DIAMOND_PICKAXE.get());
                        output.accept(ModItems.DIAMOND_AXE.get());
                        output.accept(ModItems.DIAMOND_SHOVEL.get());
                        output.accept(ModItems.DIAMOND_HOE.get());
                        output.accept(ModItems.DIAMOND_SWORD.get());

                        // Netherite Tier
                        output.accept(ModItems.NETHERITE_PICKAXE.get());
                        output.accept(ModItems.NETHERITE_AXE.get());
                        output.accept(ModItems.NETHERITE_SHOVEL.get());
                        output.accept(ModItems.NETHERITE_HOE.get());
                        output.accept(ModItems.NETHERITE_SWORD.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_TABS.register(eventBus);
    }
}

