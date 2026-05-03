# NeoTools Testing Guide

## Quick Test Setup

The built-in test datapack includes:
- **Addon Types**: Gemstone, Coating
- **Gemstone Materials**: Diamond, Amethyst
- **Coating Materials**: Copper, Steel

## Testing Commands

### 1. Give yourself a tool with Diamond Gemstone
```
/give @s iron_pickaxe[neotools:addons={addons:[{type:"neotools:gemstone",material:"neotools:gemstone/diamond"}]}]
```

**Expected bonuses:**
- +2.5 Mining Speed
- ×1.15 Mining Speed Multiplier
- +50 Durability
- ×1.25 Durability Multiplier
- +1 Fortune Level
- +2 Enchantability

### 2. Give yourself a tool with Amethyst Gemstone
```
/give @s iron_axe[neotools:addons={addons:[{type:"neotools:gemstone",material:"neotools:gemstone/amethyst"}]}]
```

**Expected bonuses:**
- +1.5 Mining Speed
- ×1.08 Mining Speed Multiplier
- ×1.5 Experience Multiplier
- +1 Enchantability

### 3. Give yourself a tool with Copper Coating
```
/give @s iron_sword[neotools:addons={addons:[{type:"neotools:coating",material:"neotools:coating/copper"}]}]
```

**Expected bonuses:**
- +1.5 Attack Damage
- +0.3 Attack Speed
- +25 Durability
- ×1.1 Durability Multiplier

### 4. Give yourself a tool with Steel Coating
```
/give @s diamond_pickaxe[neotools:addons={addons:[{type:"neotools:coating",material:"neotools:coating/steel"}]}]
```

**Expected bonuses:**
- +2.5 Attack Damage
- ×1.2 Attack Damage Multiplier
- ×1.5 Durability Multiplier
- +75 Durability

### 5. Give yourself a tool with BOTH Diamond + Copper (Stacking Test)
```
/give @s iron_pickaxe[neotools:addons={addons:[{type:"neotools:gemstone",material:"neotools:gemstone/diamond"},{type:"neotools:coating",material:"neotools:coating/copper"}]}]
```

**Expected combined bonuses:**
- Mining: +2.5 ×1.15
- Durability: (+50 +25) = +75 total, ×1.25 ×1.1 = ×1.375 multiplier
- Attack Damage: +1.5
- Attack Speed: +0.3
- Fortune: +1
- Experience: ×1.0 (no multiplier on copper)
- Enchantability: +2

### 6. Give yourself a tool with Diamond + Steel (Maximum bonuses)
```
/give @s diamond_sword[neotools:addons={addons:[{type:"neotools:gemstone",material:"neotools:gemstone/diamond"},{type:"neotools:coating",material:"neotools:coating/steel"}]}]
```

**Expected combined bonuses:**
- Mining: +2.5 ×1.15
- Attack Damage: +2.5 ×1.2
- Durability: (+50 +75) = +125 total, ×1.25 ×1.5 = ×1.875 multiplier
- Fortune: +1
- Enchantability: +2

## Testing Workflow

1. **Load the world** - The addon types and materials are auto-loaded from resources
2. **Use a test command** above to give yourself a tool
3. **Check the tooltip** - Hover over the item and verify bonuses are displayed
4. **Equip the tool** - Verify bonuses apply (use `/attributes` commands if needed)
5. **Test stacking** - Give yourself multiple addons on one tool

## Tooltip Format

When you hover over a tool with addons, you should see:

```
Iron Pickaxe with Diamond Gemstone

neotools:gemstone: neotools:gemstone/diamond
  +2.5 Mining Speed
  +15.0% Mining Speed Multiplier
  +50 Durability
  +25.0% Durability Multiplier
  +1 Fortune Level
  +2 Enchantability
```

## Bonus Application Points

### Mining Speed
- Visible in: `/attributes @s query generic.block_interaction_range`
- Tested by: Breaking blocks at different speeds
- With multiplier: `base × multiplier + bonus`

### Attack Damage/Speed
- Visible in: Item info in inventory
- Attribute modifiers appear in combat
- Sword shows stats in tooltip

### Durability
- Affects max durability of tool
- Calculated as: `(base + bonus) × multiplier`
- Diamond gemstone + Steel coating = very durable

### Fortune
- Adds to "Fortune" enchantment level
- Only work on picking up drops

### Experience
- Multiplies XP dropped from blocks
- Only visible when breaking blocks that drop XP

## Smithing Table Test

To test smithing table recipes, you need recipe files. The test pack doesn't include them, but you can verify via:

1. Place a tool (iron_pickaxe) in smithing table
2. Place no template (empty slot)
3. Try adding a material item (diamond ingot)
4. Result: Tool should get the addon

## Anvil Test

Similar to smithing:
1. Place tool on left
2. Place addon material on right
3. Verify addon is added

## Troubleshooting

### Addons not showing up
- Check server logs for: `Loaded X addon types` and `Loaded Y addon materials`
- Verify JSON syntax is valid
- Make sure the mod is installed

### Bonuses not applying
- Verify addon types match exactly (case-sensitive)
- Check that addon materials are registered
- Look at tooltips to see if addon is recognized

### Smithing/Anvil not working
- Verify the addon material is registered in the registry
- Check that the tool has the ADDONS component
- Look for error logs

## Datapack Location

Built-in test pack is at:
```
src/main/resources/data/neotools/addon_types/
src/main/resources/data/neotools/addon_materials/
```

Files:
- `addon_types/gemstone.json` - Gemstone type definition
- `addon_types/coating.json` - Coating type definition
- `addon_materials/gemstone_diamond.json` - Diamond material
- `addon_materials/gemstone_amethyst.json` - Amethyst material
- `addon_materials/coating_copper.json` - Copper material
- `addon_materials/coating_steel.json` - Steel material

## What to Test

- [ ] Addon types load successfully
- [ ] Addon materials load successfully
- [ ] Single addons show in tooltip
- [ ] Multiple addons stack correctly
- [ ] Bonuses apply to tools
- [ ] Mining speed affects block breaking
- [ ] Attack damage affects combat
- [ ] Durability affects tool wear
- [ ] Fortune affects drops
- [ ] Enchantability allows more enchants
- [ ] Smithing table works (with recipes)
- [ ] Anvil upgrades work (with items)

---

Happy testing! 🎉

