# NeoTools Tool Classes Migration - Complete

## Summary

Successfully migrated all NeoTools tool item classes to use the new **Addon system** instead of the hardcoded Gemstone/Coating components.

## Files Updated

### Tool Item Classes

**1. NeoDiggerItem.java** (Base class for pickaxes, axes, shovels, hoes)
- ✅ Changed `getBonus()` method to read from ADDONS component
- ✅ Iterates through all addons and combines their bonuses
- ✅ Updated tooltip to display all addons and their stats
- ✅ All durability, mining speed, and other mechanics now use the new system

**2. NeoSwordItem.java**
- ✅ Changed `getBonus()` method to read from ADDONS component
- ✅ Updated durability and bonus logic
- ✅ Updated tooltip display

**3. NeoPickaxeItem.java, NeoAxeItem.java, NeoShovelItem.java, NeoHoeItem.java**
- ✅ These all extend NeoDiggerItem, so they automatically inherit the new behavior
- ✅ No changes needed to these files

### Event Handlers

**4. AnvilUpgradeHandler.java**
- ✅ Completely refactored to use the new Addon system
- ✅ Now correctly identifies registered addon materials
- ✅ Infers addon type from material path (format: `type/material`)
- ✅ Adds new addon to existing addons list on the tool
- ✅ Replaces existing addon of same type instead of duplicating

## How It Works Now

### Before (Hardcoded Components)
```java
Gemstone gem = stack.get(ModDataComponents.GEMSTONE);
Coating coat = stack.get(ModDataComponents.COATING);
UpgradeBonus bonus = ModUpgradeBonuses.getCombinedBonus(gem.gemstone(), coat.Coating());
```

### After (Dynamic Addon System)
```java
Addons addons = stack.get(ModDataComponents.ADDONS);
UpgradeBonus combined = UpgradeBonus.EMPTY;
for (var addon : addons.addons()) {
    var material = ModAddonRegistry.getInstance().getAddonMaterial(addon.material());
    if (material != null) {
        combined = combined.combine(material.toBonus());
    }
}
```

## Key Improvements

1. **Unlimited Addon Types** - No longer limited to just Gemstone and Coating
2. **Dynamic Bonuses** - All bonuses come from registered datapacks
3. **Flexible Composition** - Multiple addons of different types can be stacked
4. **Registry-based** - Uses ModAddonRegistry to look up addon materials at runtime
5. **Anvil Support** - Anvil recipes automatically work with any registered addon type

## Anvil Material Resolution

The AnvilUpgradeHandler now:
1. Takes a tool with ADDONS component
2. Takes an addon material item (e.g., iron_ingot)
3. Looks up if that item is a registered addon material in ModAddonRegistry
4. Infers the addon type from the material path:
   - Format: `namespace:type/material`
   - Example: `neotools:gemstone/diamond` → type is `neotools:gemstone`
5. Creates an Addon(type, material) and adds it to the tool
6. If that addon type already exists, replaces it instead of duplicating

## Example Scenarios

### Single Addon
```
Tool: iron_pickaxe (no addons)
Material: neotools:gemstone/diamond (registered addon material)
Result: iron_pickaxe with [gemstone/diamond addon]
```

### Multiple Addons
```
Tool: iron_pickaxe with [gemstone/diamond]
Material: neotools:coating/copper (registered addon material)
Result: iron_pickaxe with [gemstone/diamond, coating/copper]
```

### Addon Replacement
```
Tool: iron_pickaxe with [gemstone/diamond]
Material: neotools:gemstone/emerald (same type, different material)
Result: iron_pickaxe with [gemstone/emerald] (diamond replaced)
```

## Related Files (Not Changed)

The following files were already using the generic approach and work correctly with the new system:

- `ToolAttributeHandler.java` - Already uses ADDONS component and applies all bonuses
- `ModDataComponents.java` - Already registers ADDONS component
- `ModItems.java` - Empty (all items via datapack)
- `ModCreativeTabs.java` - Minimal (no items to show)

## Testing

To verify everything works:

1. **Create addon types and materials** in a datapack:
   ```
   data/your_namespace/addon_types/your_type.json
   data/your_namespace/addon_materials/your_type/your_material.json
   ```

2. **Give a tool with addons**:
   ```
   /give @s iron_pickaxe[neotools:addons={addons:[{type:"your_namespace:your_type",material:"your_namespace:your_type/your_material"}]}]
   ```

3. **Use anvil to apply addons** (if materials are item aliases)

4. **Verify tooltips** show the addon types and bonuses

## Architecture Notes

- **NeoDiggerItem** = Base class for all mining/utility tools
- **NeoSwordItem** = Combat-focused tool
- All tool classes now support unlimited stackable addons
- Bonus application happens via:
  - `getBonus()` → queries registry and combines
  - `getMaxDamage()` / `damageItem()` → uses bonus for durability
  - `getDestroySpeed()` → uses bonus for mining speed
  - Tooltips → displays all active addons and stats

## What's Next

The tool system is now fully integrated with the datapack-driven addon system. To use it:

1. Define addon types and materials in datapacks
2. Apply addons to tools via the `addons` component
3. Tools automatically get all bonuses without any code changes
4. Anvil recipes work for any registered addon type

✅ **Migration Complete** - All tool classes now use the universal Addon system!

