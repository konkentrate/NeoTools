# NeoTools Built-in Test Datapack - File Inventory

## Directory Structure

```
src/main/resources/
├── pack.mcmeta                                    (Datapack metadata)
└── data/
    └── neotools/
        ├── addon_types/
        │   ├── gemstone.json                     (Defines gemstone type)
        │   └── coating.json                      (Defines coating type)
        ├── addon_materials/
        │   ├── gemstone_diamond.json             (Diamond gemstone stats)
        │   ├── gemstone_amethyst.json            (Amethyst gemstone stats)
        │   ├── coating_copper.json               (Copper coating stats)
        │   ├── coating_steel.json                (Steel coating stats)
        │   └── README.md                         (Material documentation)
        └── tags/                                  (Existing vanilla tags)
```

## File Details

### pack.mcmeta
- Pack format: 48 (Minecraft 1.21)
- Description: Test addon pack
- **Purpose**: Makes this a valid datapack

### Addon Types

#### gemstone.json
- **ID**: `neotools:gemstone`
- **Max Stack**: 1 (only one gemstone per tool)
- **Purpose**: Define the gemstone addon type

#### coating.json
- **ID**: `neotools:coating`
- **Max Stack**: 1 (only one coating per tool)
- **Purpose**: Define the coating addon type

### Addon Materials

#### gemstone_diamond.json
- **ID**: `neotools:gemstone/diamond`
- **Bonuses**:
  - Mining Speed: +2.5 base, ×1.15 multiplier
  - Durability: +50 base, ×1.25 multiplier
  - Fortune: +1
  - Enchantability: +2
- **Use Case**: High-end mining bonus

#### gemstone_amethyst.json
- **ID**: `neotools:gemstone/amethyst`
- **Bonuses**:
  - Mining Speed: +1.5 base, ×1.08 multiplier
  - Experience: ×1.5 multiplier
  - Enchantability: +1
- **Use Case**: Balanced with XP focus

#### coating_copper.json
- **ID**: `neotools:coating/copper`
- **Bonuses**:
  - Attack Damage: +1.5
  - Attack Speed: +0.3
  - Durability: +25 base, ×1.1 multiplier
- **Use Case**: Light combat enhancement

#### coating_steel.json
- **ID**: `neotools:coating/steel`
- **Bonuses**:
  - Attack Damage: +2.5 base, ×1.2 multiplier
  - Durability: +75 base, ×1.5 multiplier
- **Use Case**: Heavy combat + durability

## Testing Matrix

### Scenario 1: Single Addon
```
Tool: Iron Pickaxe + Diamond Gemstone
→ +2.5 mining speed (immediately observable)
→ Tooltip shows addon
```

### Scenario 2: Multiple Addons
```
Tool: Iron Pickaxe + Diamond Gemstone + Copper Coating
→ Mining speed: +2.5 ×1.15
→ Attack damage: +1.5
→ Durability: (+50+25) ×(1.25×1.1)
→ All bonuses combine
```

### Scenario 3: Addon Replacement
```
Tool has: Diamond Gemstone
Add: Amethyst Gemstone (same type)
→ Diamond is replaced, not added
→ New bonus replaces old
```

## Quick Commands for Testing

```bash
# Diamond gemstone
/give @s iron_pickaxe[neotools:addons={addons:[{type:"neotools:gemstone",material:"neotools:gemstone/diamond"}]}]

# Amethyst gemstone
/give @s iron_pickaxe[neotools:addons={addons:[{type:"neotools:gemstone",material:"neotools:gemstone/amethyst"}]}]

# Copper coating
/give @s iron_sword[neotools:addons={addons:[{type:"neotools:coating",material:"neotools:coating/copper"}]}]

# Steel coating
/give @s diamond_sword[neotools:addons={addons:[{type:"neotools:coating",material:"neotools:coating/steel"}]}]

# Diamond + Copper (combined)
/give @s iron_pickaxe[neotools:addons={addons:[{type:"neotools:gemstone",material:"neotools:gemstone/diamond"},{type:"neotools:coating",material:"neotools:coating/copper"}]}]
```

## Expected Console Output

When the mod loads with this datapack, you should see:

```
[... other logs ...]
[NeoTools] Registered addon registry reload listeners
[NeoTools] Loaded 2 addon types
[NeoTools] Loaded 4 addon materials
[... continuing ...]
```

## Verification Steps

1. **Start Minecraft** with the mod
2. **Create/load a world**
3. **Check logs** for addon loading messages
4. **Use a test command** from above
5. **Hover over item** - should show addon in tooltip
6. **Equip tool** - should see bonuses apply
7. **Break blocks** - should feel faster/different
8. **Combat test** - sword should hit harder

## Adding New Materials

To add a new gemstone:

1. Create `addon_materials/gemstone_YOURNAME.json`:
```json
{
  "id": "neotools:gemstone/yourname",
  "mining_speed_bonus": 1.0,
  "attack_damage_bonus": 0.5
}
```

2. Reload world (or restart)
3. Use in commands with that ID

## Available Bonus Fields

All are optional. See addon_materials/* for examples:

- `mining_speed_bonus` (float)
- `mining_speed_multiplier` (float)
- `attack_damage_bonus` (float)
- `attack_damage_multiplier` (float)
- `attack_speed_bonus` (float)
- `durability_bonus` (int)
- `durability_multiplier` (float)
- `fortune_bonus` (int)
- `experience_multiplier` (float)
- `enchantability_bonus` (int)
- `auto_smelt` (boolean)

## Files Modified for Testing

These files reference the built-in datapack:
- `TESTING_GUIDE.md` - How to test
- `BUILTIN_TESTPACK_INFO.md` - Overview
- This file - Complete inventory

## Troubleshooting

### Addons not loading?
- Check `debug.log` for error messages
- Verify JSON syntax with a JSON validator
- Ensure files are in correct directories

### Bonuses not applying?
- Verify addon IDs match exactly
- Check that addon type exists
- Look at item tooltip to confirm addon is there

### Commands not working?
- Check spelling of addon IDs
- Use exact format from command examples
- Verify quotes and braces match

---

✅ **Test datapack is ready to use!**

See `TESTING_GUIDE.md` for detailed testing instructions.

