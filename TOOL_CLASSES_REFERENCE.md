# NeoTools Tool Classes - Migration Quick Reference

## Changes at a Glance

### What Changed

| Class | Before | After |
|-------|--------|-------|
| **NeoDiggerItem** | Used GEMSTONE + COATING | Uses ADDONS (unlimited) |
| **NeoSwordItem** | Used GEMSTONE + COATING | Uses ADDONS (unlimited) |
| **NeoAxeItem** | Inherited old system | Inherits new system |
| **NeoPickaxeItem** | Inherited old system | Inherits new system |
| **NeoShovelItem** | Inherited old system | Inherits new system |
| **NeoHoeItem** | Inherited old system | Inherits new system |
| **AnvilUpgradeHandler** | Hardcoded gems/coats | Dynamic addon types |

### Code Pattern Change

**Old:**
```java
// Only works with 2 addon types (hardcoded)
Gemstone gem = stack.get(GEMSTONE);
Coating coat = stack.get(COATING);
bonus = ModUpgradeBonuses.getCombinedBonus(gem.gemstone(), coat.Coating());
```

**New:**
```java
// Works with ANY registered addon type (dynamic)
Addons addons = stack.get(ADDONS);
UpgradeBonus bonus = UpgradeBonus.EMPTY;
for (var addon : addons.addons()) {
    var material = ModAddonRegistry.getInstance().getAddonMaterial(addon.material());
    if (material != null) {
        bonus = bonus.combine(material.toBonus());
    }
}
```

## Key Benefits

✅ **Unlimited Addon Types** - No longer hardcoded  
✅ **Flexible Stacking** - Any number of addons can be applied  
✅ **Clean Integration** - All bonuses applied consistently  
✅ **Datapack-First** - Tools adapt based on available addons  
✅ **Anvil Compatibility** - Automatically works with any registered addon type  

## Files Modified

```
src/main/java/com/konkentrate/neotools/item/tool/
├── NeoDiggerItem.java          ← UPDATED (main logic)
├── NeoSwordItem.java           ← UPDATED (sword-specific)
├── NeoAxeItem.java             ← No changes (inherits from NeoDiggerItem)
├── NeoPickaxeItem.java         ← No changes (inherits from NeoDiggerItem)
├── NeoShovelItem.java          ← No changes (inherits from NeoDiggerItem)
└── NeoHoeItem.java             ← No changes (inherits from NeoDiggerItem)

src/main/java/com/konkentrate/neotools/event/
└── AnvilUpgradeHandler.java    ← UPDATED (now dynamic)
```

## How Bonus Combination Works

```
Tool with: [gemstone/diamond] + [coating/copper]

    gemstone/diamond bonus:
    +2.5 mining speed
    +1.0 durability

    + coating/copper bonus:
    +1.5 attack damage
    +0.3 attack speed

    = Combined:
    +2.5 mining speed
    +1.0 durability
    +1.5 attack damage
    +0.3 attack speed
```

## Tooltip Display

**Before:**
```
[GEMSTONE] diamond
  +2.5 Mining Speed
  +1.0 Durability

[COATING] copper
  +1.5 Attack Damage
  +0.3 Attack Speed
```

**After:**
```
neotools:gemstone: neotools:gemstone/diamond
  +2.5 Mining Speed
  +1.0 Durability

neotools:coating: neotools:coating/copper
  +1.5 Attack Damage
  +0.3 Attack Speed
```

## Anvil Behavior

The AnvilUpgradeHandler now:

1. **Checks** if tool has ADDONS component
2. **Looks up** the material in ModAddonRegistry
3. **Infers** addon type from material path:
   - `neotools:gemstone/diamond` → type `neotools:gemstone`
   - `custom:rune/fire` → type `custom:rune`
4. **Applies** the addon to the tool
5. **Replaces** if addon type already exists

## Migration Status

| Component | Status |
|-----------|--------|
| Tool items | ✅ Complete |
| Event handlers | ✅ Complete |
| Tooltip display | ✅ Complete |
| Bonus calculation | ✅ Complete |
| Anvil recipes | ✅ Complete |
| **Overall** | **✅ COMPLETE** |

---

All tool classes now seamlessly integrate with the new datapack-driven Addon system! 🎉

