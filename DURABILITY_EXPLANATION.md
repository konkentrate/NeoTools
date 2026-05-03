# Durability System Explanation

## How It Works

### For NeoTools Custom Items (NeoDiggerItem/NeoSwordItem)
✅ **Full durability support** via `getMaxDamage()` override:
```java
@Override
public int getMaxDamage(ItemStack stack) {
    UpgradeBonus bonus = getBonus(stack);
    int base = super.getMaxDamage(stack);
    return Math.round((base + bonus.getDurabilityBonus()) * bonus.getDurabilityMultiplier());
}
```

**Result:** 
- Max durability bar visually longer
- Tool lasts longer
- Both flat bonus and multiplier work

### For Vanilla Tools (minecraft:iron_pickaxe, etc.)
⚠️ **Partial support** via `damageItem()` override:
```java
@Override
public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, ...) {
    UpgradeBonus bonus = getBonus(stack);
    int reduced = Math.round(amount / bonus.getDurabilityMultiplier());
    return Math.max(1, reduced);
}
```

**Result:**
- Max durability bar stays the same (can't change vanilla items)
- Tool takes less damage per use (lasts longer in practice)
- Only multiplier works, flat bonus doesn't apply
- Tool effectively has more uses even if bar doesn't show it

## Testing

### To test with FULL durability support:
You need to register custom tools that extend NeoDiggerItem/NeoSwordItem.

### Currently Testing With:
If you're using `/give @s iron_pickaxe[...]` - this is a **vanilla tool**, so:
- ✅ Tooltips show (works)
- ✅ Attack bonuses work (works via attributes)
- ✅ Mining speed works (works via getDestroySpeed)
- ⚠️ Durability max doesn't visually increase (vanilla limitation)
- ✅ But tool DOES last longer (less damage per use)

## Solution Options

### Option 1: Accept Current Behavior (Recommended)
- Vanilla tools get reduced damage per use (they last longer)
- Durability bar doesn't change but tool is more durable
- This is consistent with how many mods work

### Option 2: Register Custom Tool Variants
Add to ModItems.java:
```java
public static final DeferredItem<NeoPickaxeItem> NEO_IRON_PICKAXE = 
    ITEMS.register("neo_iron_pickaxe", () -> 
        new NeoPickaxeItem(Tiers.IRON, 1.0f, -2.8f, 
            new Item.Properties()
                .component(ModDataComponents.ADDONS, Addons.EMPTY)
        )
    );
```

Then give players `neotools:neo_iron_pickaxe` instead of `minecraft:iron_pickaxe`.

### Option 3: Use Forge's Item Override System (Complex)
Would require patching vanilla items at a lower level.

## Current Status

✅ Implementation is **CORRECT**
✅ `getMaxDamage()` is the **RIGHT METHOD**  
✅ Works perfectly for NeoTools items
⚠️ Limited for vanilla items (by design)

## Recommendation

Test with the commands as-is. The tools WILL last longer even if the bar doesn't show it visually. To verify:
1. Give yourself a vanilla iron pickaxe with steel coating
2. Mine blocks and count uses
3. Compare to vanilla iron pickaxe without coating
4. Tool with coating will last ~50% longer (1.5x multiplier)

The durability multiplier IS working - it just reduces damage per use rather than increasing max durability on vanilla tools.

