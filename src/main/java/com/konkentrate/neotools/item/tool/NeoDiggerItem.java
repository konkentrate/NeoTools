package com.konkentrate.neotools.item.tool;

import com.konkentrate.neotools.item.component.AddonBonus;
import com.konkentrate.neotools.item.component.Addons;
import com.konkentrate.neotools.registry.ModAddonRegistry;
import com.konkentrate.neotools.registry.ModDataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

/**
 * Base class for all Neo digging tools (pickaxe, axe, shovel, hoe).
 * Uses the new Addon system - reads from ADDONS component.
 * Tooltips are handled by UniversalAddonTooltipHandler.
 */
public abstract class NeoDiggerItem extends DiggerItem {

    protected NeoDiggerItem(Tier tier, net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> blocks,
                            float attackDamage, float attackSpeed, Properties properties) {
        super(tier, blocks, properties.attributes(DiggerItem.createAttributes(tier, attackDamage, attackSpeed)));
    }

    /**
     * Get combined bonuses from all addons on this tool
     */
    protected AddonBonus getBonus(ItemStack stack) {
        Addons addons = stack.getOrDefault(ModDataComponents.ADDONS, Addons.EMPTY);
        if (addons.isEmpty()) return AddonBonus.EMPTY;

        AddonBonus combined = AddonBonus.EMPTY;
        for (var addon : addons.addons()) {
            var material = ModAddonRegistry.getInstance().getAddonMaterial(addon.material());
            if (material != null) {
                combined = combined.combine(material.toBonus());
            }
        }
        return combined;
    }

    /** Durability: base + flat bonus, then × multiplier */
    @Override
    public int getMaxDamage(ItemStack stack) {
        AddonBonus bonus = getBonus(stack);
        int base = super.getMaxDamage(stack);
        return Math.round((base + bonus.getDurabilityBonus()) * bonus.getDurabilityMultiplier());
    }

    /** Durability loss reduction */
    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<net.minecraft.world.item.Item> onBroken) {
        AddonBonus bonus = getBonus(stack);
        float multiplier = bonus.getDurabilityMultiplier();
        int reduced = Math.round(amount / multiplier);
        return Math.max(1, reduced);
    }

    /** Mining speed: base × speed multiplier + flat bonus */
    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        float base = super.getDestroySpeed(stack, state);
        if (base <= 1.0f) return base; // not effective on this block
        AddonBonus bonus = getBonus(stack);
        return base * bonus.getMiningSpeedMultiplier() + bonus.getMiningSpeedBonus();
    }

    // Attack damage/speed bonuses are applied via ToolAttributeHandler
}

