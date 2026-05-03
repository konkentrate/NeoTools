# Built-in Test Datapack - Ready to Use!

## 📦 What's Included

A complete test datapack built into the mod resources with:

### Addon Types (2)
- **Gemstone** - Mining/utility focused
- **Coating** - Combat/durability focused

### Addon Materials (4)

#### Gemstones:
1. **Diamond** - High mining speed, fortune, durability
   - +2.5 mining speed
   - ×1.15 mining speed multiplier
   - +50 durability
   - ×1.25 durability multiplier
   - +1 fortune
   - +2 enchantability

2. **Amethyst** - XP focused
   - +1.5 mining speed
   - ×1.08 mining speed multiplier
   - ×1.5 experience multiplier
   - +1 enchantability

#### Coatings:
1. **Copper** - Light combat focus
   - +1.5 attack damage
   - +0.3 attack speed
   - +25 durability
   - ×1.1 durability multiplier

2. **Steel** - Heavy combat focus
   - +2.5 attack damage
   - ×1.2 attack damage multiplier
   - +75 durability
   - ×1.5 durability multiplier

## 📍 File Location

```
src/main/resources/
├── pack.mcmeta
└── data/neotools/
    ├── addon_types/
    │   ├── gemstone.json       ← Gemstone type definition
    │   └── coating.json        ← Coating type definition
    └── addon_materials/
        ├── gemstone_diamond.json   ← Diamond stats
        ├── gemstone_amethyst.json  ← Amethyst stats
        ├── coating_copper.json     ← Copper stats
        └── coating_steel.json      ← Steel stats
```

## 🧪 Quick Test

When you run the mod, the addon types and materials are automatically loaded!

### Test in-game with commands:

**Single addon test:**
```
/give @s iron_pickaxe[neotools:addons={addons:[{type:"neotools:gemstone",material:"neotools:gemstone/diamond"}]}]
```

**Multiple addons test:**
```
/give @s iron_pickaxe[neotools:addons={addons:[{type:"neotools:gemstone",material:"neotools:gemstone/diamond"},{type:"neotools:coating",material:"neotools:coating/copper"}]}]
```

**Replace addon test:**
```
/give @s iron_pickaxe[neotools:addons={addons:[{type:"neotools:gemstone",material:"neotools:gemstone/amethyst"}]}]
```

See `TESTING_GUIDE.md` for detailed testing instructions.

## ✅ How It Works

1. **Mod starts** → Reload listeners are registered
2. **World loads** → Resource pack is applied
3. **AddonTypeReloadListener** → Loads `addon_types/*.json`
4. **AddonMaterialReloadListener** → Loads `addon_materials/*.json`
5. **ModAddonRegistry** → Stores loaded types and materials
6. **Tools check registry** → Apply bonuses automatically

## 📊 Test Coverage

The built-in test pack covers:

- ✅ Addon type definition
- ✅ Addon material definition
- ✅ Multiple addon types
- ✅ Multiple materials per type
- ✅ Numeric bonuses (all types)
- ✅ Multiplier bonuses
- ✅ Boolean flags (none in this set, but framework is there)
- ✅ Stacking addons
- ✅ Replacing addons
- ✅ Tooltip display

## 🔧 Modifying the Test Pack

To customize, edit the JSON files:

### addon_types/gemstone.json
```json
{
  "id": "neotools:gemstone",
  "max_stack_size": 1
}
```

### addon_materials/gemstone_diamond.json
```json
{
  "id": "neotools:gemstone/diamond",
  "mining_speed_bonus": 2.5,
  "mining_speed_multiplier": 1.15,
  "durability_bonus": 50,
  "durability_multiplier": 1.25,
  "fortune_bonus": 1,
  "enchantability_bonus": 2
}
```

Add new stats as needed - all fields are optional!

## 📖 Reference Guide

See `TESTING_GUIDE.md` for:
- Detailed test commands
- Expected outcomes
- Troubleshooting
- Bonus calculation examples

See `ADDON_SYSTEM_GUIDE.md` for:
- Complete system documentation
- Creating custom datapacks
- Available bonus fields
- Advanced usage

## 🎯 Testing Checklist

- [ ] Addon types load on startup
- [ ] Addon materials load on startup
- [ ] Single addon displays in tooltip
- [ ] Multiple addons display in tooltip
- [ ] Bonuses apply to tools
- [ ] Stacking addons combines bonuses
- [ ] Replacing addon updates correctly
- [ ] Mining speed affects block breaking
- [ ] Attack damage affects combat
- [ ] Durability extends tool life
- [ ] Check logs for no errors

## 📝 Notes

- All JSON files are in correct datapack format
- pack.mcmeta declares format 48 (1.21)
- Files are auto-loaded on world load/reload
- No special commands needed to enable
- Safe to distribute with mod

---

🚀 **Ready to test the addon system!**

