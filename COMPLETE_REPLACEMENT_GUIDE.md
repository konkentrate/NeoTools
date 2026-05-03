# Complete Vanilla Tool Replacement Guide

## ✅ What's Implemented

NeoTools registers 30 tools that replace all vanilla tools:
- All IDs: `neotools:iron_pickaxe`, `neotools:diamond_sword`, etc.
- Full addon support (durability, mining speed, attack, all bonuses)
- Same stats as vanilla equivalents
- Organized in creative tab by tier

## 🎯 Complete Replacement Strategy

### Current State
- ✅ NeoTools versions exist: `neotools:iron_pickaxe`
- ⚠️ Vanilla versions still exist: `minecraft:iron_pickaxe`
- Players can use both (but NeoTools versions are better)

### To COMPLETELY replace vanilla tools:

#### Option 1: Recipe Override (Recommended for Survival)
Create recipe files that override vanilla crafting:

`data/minecraft/recipes/iron_pickaxe.json`:
```json
{
  "type": "minecraft:crafting_shaped",
  "category": "equipment",
  "pattern": [
    "XXX",
    " # ",
    " # "
  ],
  "key": {
    "X": {
      "item": "minecraft:iron_ingot"
    },
    "#": {
      "item": "minecraft:stick"
    }
  },
  "result": {
    "id": "neotools:iron_pickaxe"
  }
}
```

This makes vanilla recipes craft NeoTools versions instead!

#### Option 2: Hide Vanilla Tools (Creative/JEI)
Create a datapack to remove vanilla tools from creative/JEI:

`data/neotools/tags/items/hidden_from_creative.json`:
```json
{
  "replace": false,
  "values": [
    "minecraft:wooden_pickaxe",
    "minecraft:stone_pickaxe",
    "minecraft:iron_pickaxe",
    "minecraft:golden_pickaxe",
    "minecraft:diamond_pickaxe",
    "minecraft:netherite_pickaxe"
    // ... add all 30 vanilla tools
  ]
}
```

#### Option 3: Loot Table Replacement
Override loot tables to drop NeoTools versions:

`data/minecraft/loot_tables/chests/village/village_weaponsmith.json`

Replace any `minecraft:iron_sword` with `neotools:iron_sword`.

## 🔧 Implementation Steps

### 1. Create Recipe Overrides (Highest Priority)

Create a file structure in your mod's resources:
```
src/main/resources/
└── data/
    └── minecraft/
        └── recipes/
            ├── wooden_pickaxe.json
            ├── wooden_axe.json
            ├── wooden_shovel.json
            ├── wooden_hoe.json
            ├── wooden_sword.json
            ├── stone_pickaxe.json
            ... (all 30 tools)
```

Each file overrides vanilla recipe to craft `neotools:` version.

### 2. Test Commands

Both work, but NeoTools version is superior:

```bash
# Vanilla (limited addon support)
/give @s minecraft:iron_pickaxe

# NeoTools (full addon support)
/give @s neotools:iron_pickaxe

# NeoTools with addon (FULLY functional)
/give @s neotools:iron_pickaxe[neotools:addons={addons:[{type:"neotools:gemstone",material:"neotools:gemstone/diamond"}]}]
```

### 3. In Modpacks

Modpack creators can:
- Add datapack to hide vanilla tools from JEI
- Override all recipes via CraftTweaker/KubeJS
- Use NeoTools versions exclusively

## 📊 Comparison

| Feature | minecraft:iron_pickaxe | neotools:iron_pickaxe |
|---------|----------------------|---------------------|
| Basic function | ✅ | ✅ |
| Vanilla stats | ✅ | ✅ |
| Addon support | ❌ | ✅ FULL |
| Durability bonus | ❌ | ✅ Visual + Functional |
| Mining speed bonus | ❌ | ✅ |
| Attack bonuses | ❌ | ✅ |
| Fortune bonus | ❌ | ✅ |
| XP multiplier | ❌ | ✅ |
| Stacking addons | ❌ | ✅ |

## 🎮 Testing

### Verify NeoTools Tools Work:
```bash
/give @s neotools:iron_pickaxe
```
Should behave exactly like vanilla iron pickaxe.

### Verify Addons Work:
```bash
/give @s neotools:iron_pickaxe[neotools:addons={addons:[{type:"neotools:coating",material:"neotools:coating/steel"}]}]
```
Should show +75 durability, +50% multiplier in tooltip.

### Verify Creative Tab:
- Open creative inventory
- Go to NeoTools tab
- Should see all 30 tools organized by tier

## 🚀 KubeJS Integration (Future)

When KubeJS integration is added, modpack devs can:

```javascript
// Create custom tier
NeoToolsEvents.registerTier('mythril', event => {
  event.uses = 3000
  event.speed = 12.0
  event.attackDamageBonus = 5.0
  event.harvestLevel = 5
  event.enchantmentValue = 25
})

// Create tool using NeoTools classes
ItemEvents.registry('item', event => {
  event.create('mypack:mythril_pickaxe', 'neotools:pickaxe')
    .tier('mythril')
    .attackDamageBaseline(6.0)
    .attackSpeedModifier(-2.8)
})
```

## 📝 Recipe Override Template

For each tool, create this structure (example for iron pickaxe):

**File**: `data/minecraft/recipes/iron_pickaxe.json`
```json
{
  "type": "minecraft:crafting_shaped",
  "category": "equipment",
  "pattern": ["XXX", " # ", " # "],
  "key": {
    "X": {"item": "minecraft:iron_ingot"},
    "#": {"item": "minecraft:stick"}
  },
  "result": {
    "id": "neotools:iron_pickaxe",
    "count": 1
  }
}
```

Copy this pattern for all 30 tools, changing:
- File name
- Pattern (for different tool shapes)
- Materials (wood, stone, iron, gold, diamond, netherite)
- Result ID

## ✅ Current Status

- ✅ All 30 tools registered
- ✅ Full addon support
- ✅ Creative tab populated
- ✅ Same stats as vanilla
- ⚠️ Recipe overrides needed (create datapacks)
- ⚠️ Loot table overrides needed (for chests/structures)

## 🎯 Recommendation

**Phase 1 (Current)**: 
- NeoTools and vanilla tools coexist
- Players can use either
- NeoTools versions clearly superior with addons

**Phase 2 (Recipe Datapacks)**:
- Add built-in recipe overrides
- Vanilla recipes craft NeoTools versions
- Seamless replacement for new worlds

**Phase 3 (KubeJS Integration)**:
- Modpack devs add custom tiers
- Custom tools with addon support
- Full customization

---

**Ready to use!** Players can start using `neotools:` tools immediately with full addon support. 🚀

