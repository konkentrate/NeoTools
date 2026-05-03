# NeoTools Refactoring Summary

## What Changed

### Removed
- ✅ All custom tool registrations (Flint, Bronze, Steel, Tungsten Steel, Titanium)
- ✅ ModTiers references in ModItems
- ✅ ModDataComponents.GEMSTONE and ModDataComponents.COATING (hardcoded components)
- ✅ ModUpgradeBonuses JSON loading (old system)
- ✅ All tool items from ModCreativeTabs
- ✅ Item tag definitions for custom tools

### Added
- ✅ **Addon** - Single addon with type + material
- ✅ **AddonMaterial** - Material definition with bonuses
- ✅ **AddonType** - Type definition with max stack size
- ✅ **Addons** - List component holding multiple addons on a tool
- ✅ **ModAddonRegistry** - Singleton registry for addon types and materials
- ✅ **AddonTypeReloadListener** - JSON reload listener for addon types
- ✅ **AddonMaterialReloadListener** - JSON reload listener for addon materials
- ✅ **ModBlocks** - Placeholder for block registry (for potential future use)

### Updated
- ✅ **ModDataComponents** - Replaced GEMSTONE/COATING with generic ADDONS component
- ✅ **ModItems** - Cleaned to have no registered items (all via datapacks)
- ✅ **ModCreativeTabs** - Cleaned to be minimal (no items to display)
- ✅ **ModItemTagProvider** - Cleaned to have no tag definitions (all via datapacks)
- ✅ **ToolAttributeHandler** - Refactored to work with ADDONS component instead of GEMSTONE/COATING
- ✅ **NeoTools.java** - Updated to register addon reload listeners

## New Files Created

```
src/main/java/com/konkentrate/neotools/item/component/
  ├── Addon.java                  # Single addon (type + material)
  ├── AddonMaterial.java          # Material with bonuses
  ├── AddonType.java              # Type definition
  ├── Addons.java                 # List of addons (data component)

src/main/java/com/konkentrate/neotools/registry/
  ├── ModAddonRegistry.java                    # Registry singleton
  ├── AddonTypeReloadListener.java             # JSON loader for types
  ├── AddonMaterialReloadListener.java         # JSON loader for materials
  └── ModBlocks.java                           # Block registry placeholder

examples/
  ├── addon_types/
  │   ├── gemstone.json                        # Example type
  │   └── coating.json                         # Example type
  └── addon_materials/
      ├── gemstone_diamond.json                # Example material
      ├── gemstone_amethyst.json               # Example material
      ├── gemstone_emerald.json                # Example material
      ├── coating_copper.json                  # Example material
      └── coating_steel.json                   # Example material

ADDON_SYSTEM_GUIDE.md                          # Complete documentation
```

## How to Use

### For Modpack Developers

1. Create a datapack
2. Define addon types in `data/yournamespace/addon_types/*.json`
3. Define addon materials in `data/yournamespace/addon_materials/*.json`
4. Apply addons to items via the `addons` component

See `ADDON_SYSTEM_GUIDE.md` for complete documentation.

### Addon Type Definition

```json
{
  "id": "namespace:typename",
  "max_stack_size": 1
}
```

### Addon Material Definition

```json
{
  "id": "namespace:typename/materialname",
  "mining_speed_bonus": 2.5,
  "durability_multiplier": 1.25,
  "attack_damage_bonus": 1.5,
  "auto_smelt": true
}
```

## Architecture Benefits

1. **Fully Modular** - Define any number of addon types
2. **Datapack Native** - No need to recompile mods
3. **Easy to Extend** - Add new stat types by updating AddonMaterial class
4. **Flexible Bonuses** - Mix additive and multiplicative bonuses
5. **Zero Hardcoding** - No default tools, all via datapacks
6. **Type Safe** - Uses registries with validation

## Implementation Details

### Data Loading

When the server/client loads:
1. `AddonTypeReloadListener` loads addon types from `addon_types/` folders
2. `AddonMaterialReloadListener` loads materials from `addon_materials/` folders
3. Both populate `ModAddonRegistry` singleton

### Bonus Application

When an item with addons is equipped:
1. `ItemAttributeModifierEvent` triggers
2. `ToolAttributeHandler` checks for ADDONS component
3. Iterates through all addons, fetching materials from registry
4. Combines all bonuses via `UpgradeBonus.combine()`
5. Applies as attribute modifiers to the item

### Bonus Combination Rules

- **Additive fields** (bonuses): Sum all values
  - E.g., +2.5 + +1.5 = +4.0
- **Multiplicative fields** (multipliers): Multiply all values
  - E.g., 1.25 × 1.15 = 1.4375
- **Boolean fields**: OR together
  - E.g., true OR false = true

## Testing

To verify the system works:

1. Place the example addon type/material JSONs in a datapack
2. Apply addons to a tool via the component
3. Verify bonuses appear in item tooltips
4. Check server/client logs for:
   - "Loaded X addon types"
   - "Loaded Y addon materials"

## Future Enhancements

Possible extensions (no core changes needed):

1. Add new stat types to AddonMaterial
2. Implement addon type validation
3. Add conditional bonuses (if gemstone + if coating)
4. Create custom addon display in tooltips
5. Add max_stack_size enforcement
6. Integrate with crafting/anvil recipes

---

**Last Updated**: May 3, 2026
**Status**: ✅ Implementation Complete

