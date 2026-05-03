package com.konkentrate.neotools.registry;

import com.konkentrate.neotools.NeoTools;
import com.konkentrate.neotools.item.component.Addons;
import com.konkentrate.neotools.item.tool.*;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * NeoTools replaces ALL vanilla tools with addon-capable versions.
 *
 * Each tool:
 * - Has full addon support (durability, mining speed, attack, etc.)
 * - Pre-initialized with ADDONS component
 * - Same stats as vanilla equivalents
 * - Same crafting recipes (overridden in datapacks)
 *
 * Later: KubeJS integration will allow modpack devs to add custom tool tiers
 */
public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NeoTools.MODID);

    // Helper to create default properties with empty addons component
    private static Item.Properties neoToolProps() {
        return new Item.Properties()
                .component(ModDataComponents.ADDONS.get(), Addons.EMPTY);
    }

    // VANILLA TOOL REPLACEMENTS

    // Wooden tools
    public static final DeferredItem<NeoPickaxeItem> WOODEN_PICKAXE = ITEMS.register("wooden_pickaxe",
            () -> new NeoPickaxeItem(Tiers.WOOD, 1.0f, -2.8f, neoToolProps()));
    public static final DeferredItem<NeoAxeItem> WOODEN_AXE = ITEMS.register("wooden_axe",
            () -> new NeoAxeItem(Tiers.WOOD, 6.0f, -3.2f, neoToolProps()));
    public static final DeferredItem<NeoShovelItem> WOODEN_SHOVEL = ITEMS.register("wooden_shovel",
            () -> new NeoShovelItem(Tiers.WOOD, 1.5f, -3.0f, neoToolProps()));
    public static final DeferredItem<NeoHoeItem> WOODEN_HOE = ITEMS.register("wooden_hoe",
            () -> new NeoHoeItem(Tiers.WOOD, 0.0f, -3.0f, neoToolProps()));
    public static final DeferredItem<NeoSwordItem> WOODEN_SWORD = ITEMS.register("wooden_sword",
            () -> new NeoSwordItem(Tiers.WOOD, 3.0f, -2.4f, neoToolProps()));

    // Stone tools
    public static final DeferredItem<NeoPickaxeItem> STONE_PICKAXE = ITEMS.register("stone_pickaxe",
            () -> new NeoPickaxeItem(Tiers.STONE, 1.0f, -2.8f, neoToolProps()));
    public static final DeferredItem<NeoAxeItem> STONE_AXE = ITEMS.register("stone_axe",
            () -> new NeoAxeItem(Tiers.STONE, 7.0f, -3.2f, neoToolProps()));
    public static final DeferredItem<NeoShovelItem> STONE_SHOVEL = ITEMS.register("stone_shovel",
            () -> new NeoShovelItem(Tiers.STONE, 1.5f, -3.0f, neoToolProps()));
    public static final DeferredItem<NeoHoeItem> STONE_HOE = ITEMS.register("stone_hoe",
            () -> new NeoHoeItem(Tiers.STONE, -1.0f, -2.0f, neoToolProps()));
    public static final DeferredItem<NeoSwordItem> STONE_SWORD = ITEMS.register("stone_sword",
            () -> new NeoSwordItem(Tiers.STONE, 3.0f, -2.4f, neoToolProps()));

    // Iron tools
    public static final DeferredItem<NeoPickaxeItem> IRON_PICKAXE = ITEMS.register("iron_pickaxe",
            () -> new NeoPickaxeItem(Tiers.IRON, 1.0f, -2.8f, neoToolProps()));
    public static final DeferredItem<NeoAxeItem> IRON_AXE = ITEMS.register("iron_axe",
            () -> new NeoAxeItem(Tiers.IRON, 6.0f, -3.1f, neoToolProps()));
    public static final DeferredItem<NeoShovelItem> IRON_SHOVEL = ITEMS.register("iron_shovel",
            () -> new NeoShovelItem(Tiers.IRON, 1.5f, -3.0f, neoToolProps()));
    public static final DeferredItem<NeoHoeItem> IRON_HOE = ITEMS.register("iron_hoe",
            () -> new NeoHoeItem(Tiers.IRON, -2.0f, -1.0f, neoToolProps()));
    public static final DeferredItem<NeoSwordItem> IRON_SWORD = ITEMS.register("iron_sword",
            () -> new NeoSwordItem(Tiers.IRON, 3.0f, -2.4f, neoToolProps()));

    // Golden tools
    public static final DeferredItem<NeoPickaxeItem> GOLDEN_PICKAXE = ITEMS.register("golden_pickaxe",
            () -> new NeoPickaxeItem(Tiers.GOLD, 1.0f, -2.8f, neoToolProps()));
    public static final DeferredItem<NeoAxeItem> GOLDEN_AXE = ITEMS.register("golden_axe",
            () -> new NeoAxeItem(Tiers.GOLD, 6.0f, -3.0f, neoToolProps()));
    public static final DeferredItem<NeoShovelItem> GOLDEN_SHOVEL = ITEMS.register("golden_shovel",
            () -> new NeoShovelItem(Tiers.GOLD, 1.5f, -3.0f, neoToolProps()));
    public static final DeferredItem<NeoHoeItem> GOLDEN_HOE = ITEMS.register("golden_hoe",
            () -> new NeoHoeItem(Tiers.GOLD, 0.0f, -3.0f, neoToolProps()));
    public static final DeferredItem<NeoSwordItem> GOLDEN_SWORD = ITEMS.register("golden_sword",
            () -> new NeoSwordItem(Tiers.GOLD, 3.0f, -2.4f, neoToolProps()));

    // Diamond tools
    public static final DeferredItem<NeoPickaxeItem> DIAMOND_PICKAXE = ITEMS.register("diamond_pickaxe",
            () -> new NeoPickaxeItem(Tiers.DIAMOND, 1.0f, -2.8f, neoToolProps()));
    public static final DeferredItem<NeoAxeItem> DIAMOND_AXE = ITEMS.register("diamond_axe",
            () -> new NeoAxeItem(Tiers.DIAMOND, 5.0f, -3.0f, neoToolProps()));
    public static final DeferredItem<NeoShovelItem> DIAMOND_SHOVEL = ITEMS.register("diamond_shovel",
            () -> new NeoShovelItem(Tiers.DIAMOND, 1.5f, -3.0f, neoToolProps()));
    public static final DeferredItem<NeoHoeItem> DIAMOND_HOE = ITEMS.register("diamond_hoe",
            () -> new NeoHoeItem(Tiers.DIAMOND, -3.0f, 0.0f, neoToolProps()));
    public static final DeferredItem<NeoSwordItem> DIAMOND_SWORD = ITEMS.register("diamond_sword",
            () -> new NeoSwordItem(Tiers.DIAMOND, 3.0f, -2.4f, neoToolProps()));

    // Netherite tools
    public static final DeferredItem<NeoPickaxeItem> NETHERITE_PICKAXE = ITEMS.register("netherite_pickaxe",
            () -> new NeoPickaxeItem(Tiers.NETHERITE, 1.0f, -2.8f, neoToolProps().fireResistant()));
    public static final DeferredItem<NeoAxeItem> NETHERITE_AXE = ITEMS.register("netherite_axe",
            () -> new NeoAxeItem(Tiers.NETHERITE, 5.0f, -3.0f, neoToolProps().fireResistant()));
    public static final DeferredItem<NeoShovelItem> NETHERITE_SHOVEL = ITEMS.register("netherite_shovel",
            () -> new NeoShovelItem(Tiers.NETHERITE, 1.5f, -3.0f, neoToolProps().fireResistant()));
    public static final DeferredItem<NeoHoeItem> NETHERITE_HOE = ITEMS.register("netherite_hoe",
            () -> new NeoHoeItem(Tiers.NETHERITE, -4.0f, 0.0f, neoToolProps().fireResistant()));
    public static final DeferredItem<NeoSwordItem> NETHERITE_SWORD = ITEMS.register("netherite_sword",
            () -> new NeoSwordItem(Tiers.NETHERITE, 3.0f, -2.4f, neoToolProps().fireResistant()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        NeoTools.LOGGER.info("Registered {} NeoTools items (vanilla tool replacements)", ITEMS.getEntries().size());
    }
}

