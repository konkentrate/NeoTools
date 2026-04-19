package com.konkentrate.neotools.registry;

import com.konkentrate.neotools.NeoTools;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NeoTools.MODID);

    public static final Supplier<CreativeModeTab> NEOTOOLS_TAB = CREATIVE_TABS.register("neotools_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.neotools"))
                    .icon(() -> new ItemStack(ModItems.COPPER_PICKAXE.get()))
                    .displayItems((parameters, output) -> {
                        // ── Flint ──────────────────────────────────────────
                        output.accept(ModItems.FLINT_PICKAXE.get());
                        output.accept(ModItems.FLINT_AXE.get());
                        output.accept(ModItems.FLINT_SHOVEL.get());
                        output.accept(ModItems.FLINT_HOE.get());
                        output.accept(ModItems.FLINT_SWORD.get());

                        // ── Copper ─────────────────────────────────────────
                        output.accept(ModItems.COPPER_PICKAXE.get());
                        output.accept(ModItems.COPPER_AXE.get());
                        output.accept(ModItems.COPPER_SHOVEL.get());
                        output.accept(ModItems.COPPER_HOE.get());
                        output.accept(ModItems.COPPER_SWORD.get());

                        // ── Bronze ─────────────────────────────────────────
                        output.accept(ModItems.BRONZE_PICKAXE.get());
                        output.accept(ModItems.BRONZE_AXE.get());
                        output.accept(ModItems.BRONZE_SHOVEL.get());
                        output.accept(ModItems.BRONZE_HOE.get());
                        output.accept(ModItems.BRONZE_SWORD.get());

                        // ── Iron ───────────────────────────────────────────
                        output.accept(ModItems.IRON_PICKAXE.get());
                        output.accept(ModItems.IRON_AXE.get());
                        output.accept(ModItems.IRON_SHOVEL.get());
                        output.accept(ModItems.IRON_HOE.get());
                        output.accept(ModItems.IRON_SWORD.get());

                        // ── Steel ──────────────────────────────────────────
                        output.accept(ModItems.STEEL_PICKAXE.get());
                        output.accept(ModItems.STEEL_AXE.get());
                        output.accept(ModItems.STEEL_SHOVEL.get());
                        output.accept(ModItems.STEEL_HOE.get());
                        output.accept(ModItems.STEEL_SWORD.get());

                        // ── Tungsten Steel ─────────────────────────────────
                        output.accept(ModItems.TUNGSTEN_STEEL_PICKAXE.get());
                        output.accept(ModItems.TUNGSTEN_STEEL_AXE.get());
                        output.accept(ModItems.TUNGSTEN_STEEL_SHOVEL.get());
                        output.accept(ModItems.TUNGSTEN_STEEL_HOE.get());
                        output.accept(ModItems.TUNGSTEN_STEEL_SWORD.get());

                        // ── Titanium ───────────────────────────────────────
                        output.accept(ModItems.TITANIUM_PICKAXE.get());
                        output.accept(ModItems.TITANIUM_AXE.get());
                        output.accept(ModItems.TITANIUM_SHOVEL.get());
                        output.accept(ModItems.TITANIUM_HOE.get());
                        output.accept(ModItems.TITANIUM_SWORD.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_TABS.register(eventBus);
    }
}

