# UpgradeSmithingRecipe Migration - Complete

## Summary

Successfully migrated `UpgradeSmithingRecipe.java` to use the new **Addon system** instead of the hardcoded `GEMSTONE` and `COATING` components.

## What Changed

### Before (Hardcoded Types)
```java
public enum UpgradeType { GEMSTONE, COATING }

private final UpgradeType upgradeType;
private final ResourceLocation upgradeId;

// In assemble():
return switch (upgradeType) {
    case GEMSTONE -> {
        result.set(ModDataComponents.GEMSTONE.get(), new Gemstone(upgradeId));
        yield result;
    }
    case COATING -> {
        result.set(ModDataComponents.COATING.get(), new Coating(upgradeId));
        yield result;
    }
};
```

### After (Dynamic Addon System)
```java
private final ResourceLocation addonType;       // e.g., "neotools:gemstone"
private final ResourceLocation addonMaterial;   // e.g., "neotools:gemstone/diamond"

// In assemble():
Addon newAddon = new Addon(addonType, addonMaterial);
Addons existing = result.getOrDefault(ModDataComponents.ADDONS, Addons.EMPTY);
List<Addon> addonList = new ArrayList<>(existing.addons());

// Replace or add addon
for (int i = 0; i < addonList.size(); i++) {
    if (addonList.get(i).type().equals(addonType)) {
        addonList.set(i, newAddon);
        break;
    }
}
if (!replaced) {
    addonList.add(newAddon);
}

result.set(ModDataComponents.ADDONS, new Addons(addonList));
return result;
```

## Key Improvements

✅ **Unlimited Addon Types** - No longer limited to 2 types  
✅ **Dynamic Recipes** - Works with any registered addon type  
✅ **Smart Replacement** - Replaces addon of same type instead of duplicating  
✅ **Multiple Addons** - Can apply different addon types in sequence  

## Recipe JSON Format Change

### Before
```json
{
  "type": "neotools:tool_upgrade",
  "base": { "item": "minecraft:iron_pickaxe" },
  "addition": { "item": "minecraft:diamond" },
  "upgrade_type": "GEMSTONE",
  "upgrade_id": "neotools:gemstone/diamond"
}
```

### After
```json
{
  "type": "neotools:tool_upgrade",
  "base": { "item": "minecraft:iron_pickaxe" },
  "addition": { "item": "minecraft:diamond" },
  "addon_type": "neotools:gemstone",
  "addon_material": "neotools:gemstone/diamond"
}
```

## Codec Changes

| Field | Before | After |
|-------|--------|-------|
| Type | `UpgradeType` enum | `ResourceLocation` |
| Material | Single `upgradeId` | Separate `addonType` + `addonMaterial` |
| Flexibility | 2 hardcoded types | Any registered addon type |

## Recipe Behavior

The recipe now:

1. **Takes** a tool with or without addons
2. **Takes** an addon material item
3. **Creates** an Addon with the specified type and material
4. **Adds** it to the tool's addons list
5. **Replaces** if an addon of the same type exists
6. **Returns** the upgraded tool

### Example Scenarios

**Scenario 1: Fresh tool**
```
Input:  Iron Pickaxe (no addons) + Diamond gem material
Output: Iron Pickaxe with [gemstone/diamond]
```

**Scenario 2: Add second addon type**
```
Input:  Iron Pickaxe with [gemstone/diamond] + Copper coating material
Output: Iron Pickaxe with [gemstone/diamond, coating/copper]
```

**Scenario 3: Replace addon**
```
Input:  Iron Pickaxe with [gemstone/diamond] + Emerald gem material
Output: Iron Pickaxe with [gemstone/emerald] (diamond replaced)
```

## Recipe JSON Examples

### Gemstone Recipe
```json
{
  "type": "neotools:tool_upgrade",
  "base": { "tag": "minecraft:tools" },
  "addition": { "item": "minecraft:diamond" },
  "addon_type": "neotools:gemstone",
  "addon_material": "neotools:gemstone/diamond"
}
```

### Coating Recipe
```json
{
  "type": "neotools:tool_upgrade",
  "base": { "tag": "minecraft:tools" },
  "addition": { "item": "minecraft:copper_ingot" },
  "addon_type": "neotools:coating",
  "addon_material": "neotools:coating/copper"
}
```

### Custom Addon Recipe
```json
{
  "type": "neotools:tool_upgrade",
  "base": { "tag": "minecraft:tools" },
  "addition": { "item": "mymod:fire_essence" },
  "addon_type": "mymod:rune",
  "addon_material": "mymod:rune/fire"
}
```

## Migration Checklist

| Item | Status |
|------|--------|
| Remove UpgradeType enum | ✅ Complete |
| Change to ResourceLocation fields | ✅ Complete |
| Update assembly logic | ✅ Complete |
| Fix CODEC | ✅ Complete |
| Fix STREAM_CODEC | ✅ Complete |
| Verify compilation | ✅ Complete |

## Related Files

The following were already updated to work with this recipe:
- `AnvilUpgradeHandler.java` - Handles item-based addon application
- `NeoDiggerItem.java` / `NeoSwordItem.java` - Apply all addon bonuses
- `ToolAttributeHandler.java` - Handles stat application

## What's Next

Datapacks can now define custom smithing recipes for any addon type:

1. **Define addon types**: `data/namespace/addon_types/typename.json`
2. **Define addon materials**: `data/namespace/addon_materials/typename/materialname.json`
3. **Create smithing recipes**: `data/namespace/recipes/upgrade_my_addon.json` with `addon_type` and `addon_material` fields
4. The recipes will automatically work!

---

✅ **Migration Complete** - UpgradeSmithingRecipe now uses the universal Addon system!

