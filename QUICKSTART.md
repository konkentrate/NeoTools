# NeoTools Quick Start Guide

## TL;DR - Create Your First Addon in 5 Minutes

### Step 1: Create a Datapack

```bash
mkdir -p my_addons/data/mymodname/{addon_types,addon_materials}
```

### Step 2: Define an Addon Type

**File**: `my_addons/data/mymodname/addon_types/gemstone.json`

```json
{
  "id": "mymodname:gemstone",
  "max_stack_size": 1
}
```

### Step 3: Define an Addon Material

**File**: `my_addons/data/mymodname/addon_materials/ruby.json`

```json
{
  "id": "mymodname:gemstone/ruby",
  "attack_damage_bonus": 3.0,
  "durability_multiplier": 1.1
}
```

### Step 4: Apply to a Tool

In any item file or command:
```json
{
  "neotools:addons": {
    "addons": [
      {
        "type": "mymodname:gemstone",
        "material": "mymodname:gemstone/ruby"
      }
    ]
  }
}
```

**Done!** 🎉

---

## Common Patterns

### Single Addon Type with Multiple Materials

Define once:
```json
// addon_types/gemstone.json
{
  "id": "mymodname:gemstone",
  "max_stack_size": 1
}
```

Define many:
```json
// addon_materials/ruby.json
{ "id": "mymodname:gemstone/ruby", "attack_damage_bonus": 3.0 }

// addon_materials/sapphire.json
{ "id": "mymodname:gemstone/sapphire", "mining_speed_bonus": 2.0 }

// addon_materials/emerald.json
{ "id": "mymodname:gemstone/emerald", "durability_multiplier": 1.5 }
```

### Multiple Addon Types

```json
// addon_types/gemstone.json
{ "id": "mymodname:gemstone", "max_stack_size": 1 }

// addon_types/rune.json
{ "id": "mymodname:rune", "max_stack_size": 3 }

// addon_types/enchantment.json
{ "id": "mymodname:enchantment", "max_stack_size": 2 }
```

### Stacking Bonuses

Apply multiple addons to one tool:
```json
{
  "neotools:addons": {
    "addons": [
      {
        "type": "mymodname:gemstone",
        "material": "mymodname:gemstone/ruby"
      },
      {
        "type": "mymodname:rune",
        "material": "mymodname:rune/fire"
      }
    ]
  }
}
```

Result:
- Ruby: +3.0 attack damage
- Fire Rune: +1.5 attack damage, auto_smelt=true
- **Total**: +4.5 attack damage, auto_smelt enabled

---

## Available Bonus Stats

Add any or all of these to your addon material:

```json
{
  "id": "mymodname:example",
  "mining_speed_bonus": 1.0,           // + flat mining speed
  "mining_speed_multiplier": 1.25,     // × mining speed
  "attack_damage_bonus": 1.0,          // + flat damage
  "attack_damage_multiplier": 1.1,     // × damage
  "attack_speed_bonus": 0.5,           // + attack speed
  "durability_bonus": 50,              // + max durability
  "durability_multiplier": 1.2,        // × max durability
  "fortune_bonus": 1,                  // + Fortune level
  "experience_multiplier": 1.5,        // × dropped XP
  "enchantability_bonus": 5,           // + enchantment power
  "auto_smelt": true                   // Enable auto-smelting
}
```

---

## File Organization

Organize your addon materials logically:

```
data/mymodname/addon_materials/
├── gemstone_ruby.json
├── gemstone_sapphire.json
├── gemstone_diamond.json
├── rune_fire.json
├── rune_ice.json
├── rune_lightning.json
├── coating_copper.json
└── coating_iron.json
```

Or use subdirectories:

```
data/mymodname/addon_materials/
├── gemstones/
│   ├── ruby.json
│   ├── sapphire.json
│   └── diamond.json
├── runes/
│   ├── fire.json
│   ├── ice.json
│   └── lightning.json
└── coatings/
    ├── copper.json
    └── iron.json
```

---

## Common Issues

### "My addon isn't showing up"

1. Check the JSON syntax (use a JSON validator)
2. Verify the addon type ID matches: `type` in addon must exist in `addon_types/`
3. Verify the material ID matches: `material` must exist in `addon_materials/`
4. Make sure the `neotools:addons` component is present on the item

### "The bonuses aren't working"

1. Check the item has the `neotools:addons` component
2. Verify JSON is valid - check server logs for errors
3. Make sure you reloaded the world/datapack

### "Multipliers aren't stacking right"

Multipliers are **multiplied** together:
- 1.5 × 1.2 = 1.8 (not 2.7!)

Bonuses are **added** together:
- +3.0 + +2.0 = +5.0 ✓

---

## Testing Your Addon

1. **Create the datapack** with addon_types and addon_materials
2. **Place in world datapacks folder**: `world/datapacks/my_addon_datapack`
3. **Load the world** or use `/reload`
4. **Check logs** for: `Loaded X addon types` and `Loaded Y addon materials`
5. **Give yourself a tool** with the addon component:
   ```
   /give @s iron_pickaxe[neotools:addons={addons:[{type:"mymodname:gemstone",material:"mymodname:gemstone/ruby"}]}]
   ```
6. **Equip and verify** the bonuses appear in the item tooltip

---

## Next Steps

- Read `ADDON_SYSTEM_GUIDE.md` for complete documentation
- Check `examples/` folder for example JSON files
- Experiment with different bonus combinations
- Share your addon systems with other modpack creators!

