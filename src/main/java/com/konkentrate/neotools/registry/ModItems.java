package com.konkentrate.neotools.registry;

import com.konkentrate.neotools.NeoTools;
import com.konkentrate.neotools.item.component.Coating;
import com.konkentrate.neotools.item.component.Gemstone;
import com.konkentrate.neotools.item.tool.*;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NeoTools.MODID);

    // Helper to avoid repeating .component(COATING)/.component(GEMSTONE)...
    private static Item.Properties neoToolProps() {
        return new Item.Properties()
                .component(ModDataComponents.COATING.get(), Coating.EMPTY)
                .component(ModDataComponents.GEMSTONE.get(), Gemstone.EMPTY);
    }

    // ── Flint Toolset ─────────────────────────────────────────────────────────

    public static final Supplier<NeoPickaxeItem> FLINT_PICKAXE = ITEMS.register("flint_pickaxe",
            () -> new NeoPickaxeItem(ModTiers.FLINT, 1.0f, -2.8f, neoToolProps()));

    public static final Supplier<NeoAxeItem> FLINT_AXE = ITEMS.register("flint_axe",
            () -> new NeoAxeItem(ModTiers.FLINT, 6.0f, -3.1f, neoToolProps()));

    public static final Supplier<NeoShovelItem> FLINT_SHOVEL = ITEMS.register("flint_shovel",
            () -> new NeoShovelItem(ModTiers.FLINT, 1.5f, -3.0f, neoToolProps()));

    public static final Supplier<NeoHoeItem> FLINT_HOE = ITEMS.register("flint_hoe",
            () -> new NeoHoeItem(ModTiers.FLINT, -1.0f, -2.0f, neoToolProps()));

    public static final Supplier<NeoSwordItem> FLINT_SWORD = ITEMS.register("flint_sword",
            () -> new NeoSwordItem(ModTiers.FLINT, 2.0f, -2.6f, neoToolProps()));   // weak & slow

    // ── NeoTools Iron Toolset (replaces vanilla iron) ─────────────────────────

    public static final Supplier<NeoPickaxeItem> IRON_PICKAXE = ITEMS.register("iron_pickaxe",
            () -> new NeoPickaxeItem(ModTiers.NEO_IRON, 1.0f, -2.8f, neoToolProps()));

    public static final Supplier<NeoAxeItem> IRON_AXE = ITEMS.register("iron_axe",
            () -> new NeoAxeItem(ModTiers.NEO_IRON, 6.0f, -3.1f, neoToolProps()));

    public static final Supplier<NeoShovelItem> IRON_SHOVEL = ITEMS.register("iron_shovel",
            () -> new NeoShovelItem(ModTiers.NEO_IRON, 1.5f, -3.0f, neoToolProps()));

    public static final Supplier<NeoHoeItem> IRON_HOE = ITEMS.register("iron_hoe",
            () -> new NeoHoeItem(ModTiers.NEO_IRON, -1.0f, -2.0f, neoToolProps()));

    public static final Supplier<NeoSwordItem> IRON_SWORD = ITEMS.register("iron_sword",
            () -> new NeoSwordItem(ModTiers.NEO_IRON, 3.0f, -2.4f, neoToolProps())); // vanilla iron feel (1.6/s)

    // ── Bronze Toolset ────────────────────────────────────────────────────────

    public static final Supplier<NeoPickaxeItem> BRONZE_PICKAXE = ITEMS.register("bronze_pickaxe",
            () -> new NeoPickaxeItem(ModTiers.BRONZE, 1.0f, -2.8f, neoToolProps()));

    public static final Supplier<NeoAxeItem> BRONZE_AXE = ITEMS.register("bronze_axe",
            () -> new NeoAxeItem(ModTiers.BRONZE, 6.0f, -3.1f, neoToolProps()));

    public static final Supplier<NeoShovelItem> BRONZE_SHOVEL = ITEMS.register("bronze_shovel",
            () -> new NeoShovelItem(ModTiers.BRONZE, 1.5f, -3.0f, neoToolProps()));

    public static final Supplier<NeoHoeItem> BRONZE_HOE = ITEMS.register("bronze_hoe",
            () -> new NeoHoeItem(ModTiers.BRONZE, -1.0f, -2.0f, neoToolProps()));

    public static final Supplier<NeoSwordItem> BRONZE_SWORD = ITEMS.register("bronze_sword",
            () -> new NeoSwordItem(ModTiers.BRONZE, 3.0f, -2.4f, neoToolProps()));  // standard (1.6/s)

    // ── Copper Toolset ────────────────────────────────────────────────────────

    public static final Supplier<NeoPickaxeItem> COPPER_PICKAXE = ITEMS.register("copper_pickaxe",
            () -> new NeoPickaxeItem(ModTiers.COPPER, 1.0f, -2.8f, neoToolProps()));

    public static final Supplier<NeoAxeItem> COPPER_AXE = ITEMS.register("copper_axe",
            () -> new NeoAxeItem(ModTiers.COPPER, 6.0f, -3.1f, neoToolProps()));

    public static final Supplier<NeoShovelItem> COPPER_SHOVEL = ITEMS.register("copper_shovel",
            () -> new NeoShovelItem(ModTiers.COPPER, 1.5f, -3.0f, neoToolProps()));

    public static final Supplier<NeoHoeItem> COPPER_HOE = ITEMS.register("copper_hoe",
            () -> new NeoHoeItem(ModTiers.COPPER, -1.0f, -2.0f, neoToolProps()));

    public static final Supplier<NeoSwordItem> COPPER_SWORD = ITEMS.register("copper_sword",
            () -> new NeoSwordItem(ModTiers.COPPER, 2.5f, -2.5f, neoToolProps()));  // light, decent speed

    // ── Steel Toolset ─────────────────────────────────────────────────────────

    public static final Supplier<NeoPickaxeItem> STEEL_PICKAXE = ITEMS.register("steel_pickaxe",
            () -> new NeoPickaxeItem(ModTiers.STEEL, 1.0f, -2.8f, neoToolProps()));

    public static final Supplier<NeoAxeItem> STEEL_AXE = ITEMS.register("steel_axe",
            () -> new NeoAxeItem(ModTiers.STEEL, 6.0f, -3.1f, neoToolProps()));

    public static final Supplier<NeoShovelItem> STEEL_SHOVEL = ITEMS.register("steel_shovel",
            () -> new NeoShovelItem(ModTiers.STEEL, 1.5f, -3.0f, neoToolProps()));

    public static final Supplier<NeoHoeItem> STEEL_HOE = ITEMS.register("steel_hoe",
            () -> new NeoHoeItem(ModTiers.STEEL, -1.0f, -2.0f, neoToolProps()));

    public static final Supplier<NeoSwordItem> STEEL_SWORD = ITEMS.register("steel_sword",
            () -> new NeoSwordItem(ModTiers.STEEL, 3.5f, -2.4f, neoToolProps()));   // more damage, same speed (1.6/s)

    // ── Tungsten Steel Toolset ────────────────────────────────────────────────

    public static final Supplier<NeoPickaxeItem> TUNGSTEN_STEEL_PICKAXE = ITEMS.register("tungsten_steel_pickaxe",
            () -> new NeoPickaxeItem(ModTiers.TUNGSTEN_STEEL, 1.0f, -2.8f, neoToolProps()));

    public static final Supplier<NeoAxeItem> TUNGSTEN_STEEL_AXE = ITEMS.register("tungsten_steel_axe",
            () -> new NeoAxeItem(ModTiers.TUNGSTEN_STEEL, 6.0f, -3.1f, neoToolProps()));

    public static final Supplier<NeoShovelItem> TUNGSTEN_STEEL_SHOVEL = ITEMS.register("tungsten_steel_shovel",
            () -> new NeoShovelItem(ModTiers.TUNGSTEN_STEEL, 1.5f, -3.0f, neoToolProps()));

    public static final Supplier<NeoHoeItem> TUNGSTEN_STEEL_HOE = ITEMS.register("tungsten_steel_hoe",
            () -> new NeoHoeItem(ModTiers.TUNGSTEN_STEEL, -1.0f, -2.0f, neoToolProps()));

    public static final Supplier<NeoSwordItem> TUNGSTEN_STEEL_SWORD = ITEMS.register("tungsten_steel_sword",
            () -> new NeoSwordItem(ModTiers.TUNGSTEN_STEEL, 5.0f, -2.8f, neoToolProps())); // heavy & hard-hitting (1.2/s)

    // ── Titanium Toolset ──────────────────────────────────────────────────────

    public static final Supplier<NeoPickaxeItem> TITANIUM_PICKAXE = ITEMS.register("titanium_pickaxe",
            () -> new NeoPickaxeItem(ModTiers.TITANIUM, 1.0f, -2.8f, neoToolProps()));

    public static final Supplier<NeoAxeItem> TITANIUM_AXE = ITEMS.register("titanium_axe",
            () -> new NeoAxeItem(ModTiers.TITANIUM, 6.0f, -3.1f, neoToolProps()));

    public static final Supplier<NeoShovelItem> TITANIUM_SHOVEL = ITEMS.register("titanium_shovel",
            () -> new NeoShovelItem(ModTiers.TITANIUM, 1.5f, -3.0f, neoToolProps()));

    public static final Supplier<NeoHoeItem> TITANIUM_HOE = ITEMS.register("titanium_hoe",
            () -> new NeoHoeItem(ModTiers.TITANIUM, -1.0f, -2.0f, neoToolProps()));

    public static final Supplier<NeoSwordItem> TITANIUM_SWORD = ITEMS.register("titanium_sword",
            () -> new NeoSwordItem(ModTiers.TITANIUM, 2.0f, -1.8f, neoToolProps()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}