package com.konkentrate.neotools.registry;

import com.konkentrate.neotools.NeoTools;
import com.konkentrate.neotools.item.component.Coating;
import com.konkentrate.neotools.item.component.Gemstone;
import com.konkentrate.neotools.item.tool.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NeoTools.MODID);

    // Helper to avoid repeating .component(COATING)/.component(GEMSTONE)...
    private static Item.Properties neoToolProps() {
        return new Item.Properties()
                .component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
                .component(ModDataComponents.COATING.get(), Coating.EMPTY)
                .component(ModDataComponents.GEMSTONE.get(), Gemstone.EMPTY);
    }

    // ── Copper Toolset ────────────────────────────────────────────────────────

    public static final Supplier<NeoPickaxeItem> COPPER_PICKAXE = ITEMS.register("copper_pickaxe",
            () -> new NeoPickaxeItem(ModTiers.COPPER,
                    neoToolProps().attributes(PickaxeItem.createAttributes(ModTiers.COPPER, 1.0f, -2.8f))));

    public static final Supplier<NeoAxeItem> COPPER_AXE = ITEMS.register("copper_axe",
            () -> new NeoAxeItem(ModTiers.COPPER,
                    neoToolProps().attributes(AxeItem.createAttributes(ModTiers.COPPER, 6.0f, -3.1f))));

    public static final Supplier<NeoShovelItem> COPPER_SHOVEL = ITEMS.register("copper_shovel",
            () -> new NeoShovelItem(ModTiers.COPPER,
                    neoToolProps().attributes(ShovelItem.createAttributes(ModTiers.COPPER, 1.5f, -3.0f))));

    public static final Supplier<NeoHoeItem> COPPER_HOE = ITEMS.register("copper_hoe",
            () -> new NeoHoeItem(ModTiers.COPPER,
                    neoToolProps().attributes(HoeItem.createAttributes(ModTiers.COPPER, -1.0f, -2.0f))));

    public static final Supplier<NeoSwordItem> COPPER_SWORD = ITEMS.register("copper_sword",
            () -> new NeoSwordItem(ModTiers.COPPER,
                    neoToolProps().attributes(SwordItem.createAttributes(ModTiers.COPPER, 3, -2.4f))));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}