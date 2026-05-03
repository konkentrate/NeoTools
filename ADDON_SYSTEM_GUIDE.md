# NeoTools Addon System - Datapack Guide

## Overview

NeoTools now provides a **fully datapack-configurable addon system** for tools. The mod no longer ships with built-in tools - everything is defined via datapacks, making it easy for modpack creators to customize or create entirely new tool types and addon systems.

## Architecture

### Core Concepts

- **Addon Type**: A category of addon (e.g., "neotools:gemstone", "neotools:coating", custom addon types)
- **Addon Material**: A specific addon within a type with defined bonuses (e.g., "neotools:gemstone/diamond")
- **Addon**: An instance of a material applied to a tool (type + material)
- **Tool**: Any item with the `addons` data component containing a list of applied addons

### Data Component

Tools use the `addons` data component to store applied addons:

```json
{
  "addons": [
    {
      "type": "neotools:gemstone",
      "material": "neotools:gemstone/diamond"
    },
    {
      "type": "neotools:coating",
      "material": "neotools:coating/copper"
    }
  ]
}
```

## Datapack Structure

### 1. Define Addon Types

**Location**: `data/namespace/addon_types/type_name.json`

```json
{
  "id": "neotools:gemstone",
  "max_stack_size": 1
}
```

```json
{
  "id": "neotools:coating",
  "max_stack_size": 1
}
```

```json
{
  "id": "custom:rune",
  "max_stack_size": 3
}
```

**Fields**:
- `id`: The addon type ID (ResourceLocation)
- `max_stack_size`: Maximum number of this addon type that can be applied (default: 1)

### 2. Define Addon Materials

**Location**: `data/namespace/addon_materials/path_to_material.json`

Each material defines the bonuses it gives. The JSON filename doesn't have to match any pattern - use whatever you prefer.

**Example Gemstone**:
```json
{
  "id": "neotools:gemstone/diamond",
  "mining_speed_bonus": 2.5,
  "durability_multiplier": 1.25,
  "fortune_bonus": 1,
  "enchantability_bonus": 2
}
```

**Example Coating**:
```json
{
  "id": "neotools:coating/copper",
  "attack_damage_bonus": 1.5,
  "attack_speed_bonus": 0.3
}
```

**Example Custom Addon**:
```json
{
  "id": "custom:rune/fire",
  "attack_damage_bonus": 5.0,
  "attack_damage_multiplier": 1.15,
  "auto_smelt": true
}
```

**Available Fields** (all optional):
- `mining_speed_bonus`: Float, added to mining speed
- `mining_speed_multiplier`: Float, multiplies base mining speed
- `attack_damage_bonus`: Float, added to attack damage
- `attack_damage_multiplier`: Float, multiplies attack damage
- `attack_speed_bonus`: Float, added to attack speed (positive is slower, negative is faster in Minecraft)
- `durability_bonus`: Integer, added to max durability
- `durability_multiplier`: Float, multiplies max durability
- `fortune_bonus`: Integer, added to Fortune level
- `experience_multiplier`: Float, multiplies dropped XP
- `enchantability_bonus`: Integer, makes item more enchantable
- `auto_smelt`: Boolean, enables auto-smelting (if the tool supports it)

### 3. Create Tools

Since tools are vanilla items in default configuration, you can either:

**Option A: Use Vanilla Tools**
```json
{
  "type": "minecraft:pickaxe",
  "item": "minecraft:iron_pickaxe",
  "components": {
    "neotools:addons": {
      "addons": [
        {
          "type": "neotools:gemstone",
          "material": "neotools:gemstone/diamond"
        }
      ]
    }
  }
}
```

**Option B: Create Custom Tool Items** (requires item definition in your mod/datapack)

## Example Datapack Structure

```
datapack/
  pack.mcmeta
  data/
    neotools/
      addon_types/
        gemstone.json
        coating.json
      addon_materials/
        gemstone_diamond.json
        gemstone_amethyst.json
        coating_copper.json
        coating_steel.json
    custommodname/
      addon_types/
        rune.json
      addon_materials/
        rune_fire.json
        rune_ice.json
```

## Combining Addons

When multiple addons are applied to a tool:
- **Numeric bonuses** are added together (e.g., +2.5 from gemstone + +1.5 from coating = +4.0 total)
- **Multipliers** are multiplied together (e.g., 1.25 × 1.15 = 1.4375)
- **Boolean flags** are OR'd together (e.g., if any addon has auto-smelt=true, the tool auto-smelts)

### Example

Tool with `neotools:gemstone/diamond` + `neotools:coating/copper`:
- Mining speed: 0 (base) + 2.5 (gemstone) = 2.5
- Attack damage: 0 (base) + 2.0 (tool) + 1.5 (coating) = 3.5
- Attack speed: 0 (base) + 0.3 (coating) = 0.3

## Using the System

### For Modpack Creators

1. Create a datapack
2. Define your addon types in `data/yournamespace/addon_types/`
3. Define your addon materials in `data/yournamespace/addon_materials/`
4. Add the `addons` component to items (tools) with your addons
5. Distribute the datapack!

### For Mod Developers

To create custom addon support in your mod:

1. **Extend the addon system**:
   ```java
   // Your custom material class can hold additional properties
   var material = ModAddonRegistry.getInstance().getAddonMaterial(
       ResourceLocation.parse("yourmod:custom_addon/example")
   );
   
   if (material != null) {
       UpgradeBonus bonus = material.toBonus();
       // Apply bonus to your item
   }
   ```

2. **Hook into the attribute system**:
   - The `ToolAttributeHandler` automatically applies all addon bonuses
   - Just ensure your tool items have the `addons` component

## Example Datapacks

### Minimal Vanilla Replacements

A datapack that adds a gemstone to vanilla iron tools:

```json
{
  "id": "neotools:gemstone",
  "max_stack_size": 1
}
```

```json
{
  "id": "neotools:gemstone/iron_boost",
  "mining_speed_bonus": 1.0,
  "attack_damage_bonus": 0.5
}
```

### Complete Custom Tool System

A datapack defining from scratch:
- 3 addon types (gemstones, coatings, runes)
- 8 addon materials total
- Configured to work with vanilla tools or custom items

## Troubleshooting

### Changes Not Appearing

1. Ensure addon types are loaded before addon materials
2. Check `debug.log` for error messages
3. Verify JSON syntax is correct
4. Material IDs must exactly match in the addon component

### Bonuses Not Applying

1. Verify the addon type and material IDs in your JSON
2. Check that valid materials are registered
3. Ensure the tool has the `addons` component
4. Look for error logs in `debug.log`

### MaxStackSize Not Working

This field is informational - enforcement must be done by the mod/datapack creator when applying addons.

## Future Extensions

The addon system is designed to be extensible. You can:
- Add new stat types (requires code changes)
- Create nested addon types
- Implement addon type-specific validation
- Add conditional bonuses based on tool properties

## Implementation Details

- Reload listeners: `AddonTypeReloadListener`, `AddonMaterialReloadListener`
- Registry: `ModAddonRegistry` (singleton)
- Component: `ModDataComponents.ADDONS`
- Event handler: `ToolAttributeHandler` (applies bonuses as attribute modifiers)

---

**For examples and test datapacks, see the `examples/` folder.**

