package com.konkentrate.neotools.item.tool;

import com.konkentrate.neotools.item.component.Addons;
import com.konkentrate.neotools.item.component.UpgradeBonus;
import com.konkentrate.neotools.registry.ModAddonRegistry;
import com.konkentrate.neotools.registry.ModDataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

import java.util.function.Consumer;

/**
 * Neo sword item - uses the new Addon system.
 * Tooltips are handled by UniversalAddonTooltipHandler.
 */
public class NeoSwordItem extends SwordItem {

    public NeoSwordItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, properties.attributes(SwordItem.createAttributes(tier, attackDamage, attackSpeed)));
    }

    /**
     * Get combined bonuses from all addons on this tool
     */
    protected UpgradeBonus getBonus(ItemStack stack) {
        Addons addons = stack.getOrDefault(ModDataComponents.ADDONS, Addons.EMPTY);
        if (addons.isEmpty()) return UpgradeBonus.EMPTY;

        UpgradeBonus combined = UpgradeBonus.EMPTY;
        for (var addon : addons.addons()) {
            var material = ModAddonRegistry.getInstance().getAddonMaterial(addon.material());
            if (material != null) {
                combined = combined.combine(material.toBonus());
            }
        }
        return combined;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        UpgradeBonus bonus = getBonus(stack);
        int base = super.getMaxDamage(stack);
        return Math.round((base + bonus.getDurabilityBonus()) * bonus.getDurabilityMultiplier());
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<net.minecraft.world.item.Item> onBroken) {
        UpgradeBonus bonus = getBonus(stack);
        int reduced = Math.round(amount / bonus.getDurabilityMultiplier());
        return Math.max(1, reduced);
    }

    // Attack damage/speed bonuses are applied via ToolAttributeHandler
}

