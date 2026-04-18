package com.konkentrate.neotools.item.tool;

import com.konkentrate.neotools.item.component.UpgradeBonus;
import com.konkentrate.neotools.registry.ModDataComponents;
import com.konkentrate.neotools.item.component.Gemstone;
import com.konkentrate.neotools.item.component.Coating;
import com.konkentrate.neotools.registry.ModUpgradeBonuses;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class NeoPickaxeItem extends PickaxeItem {
    public NeoPickaxeItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    // ===============
    // Bonuses calculation and application
    // ===============

    /**
     * Get the combined upgrade bonus from gemstone and coating
     */
    private UpgradeBonus getBonus(ItemStack stack) {
        Gemstone gem = stack.getOrDefault(ModDataComponents.GEMSTONE, Gemstone.EMPTY);
        Coating coat = stack.getOrDefault(ModDataComponents.COATING, Coating.EMPTY);

        return ModUpgradeBonuses.getCombinedBonus(
                gem.hasGemstone() ? gem.gemstone() : null,
                coat.hasCoating() ? coat.Coating() : null
        );
    }

    /**
     * Apply durability bonus and multiplier from upgrades on top of the base tier durability.
     */
    @Override
    public int getMaxDamage(ItemStack stack) {
        UpgradeBonus bonus = getBonus(stack);
        int base = super.getMaxDamage(stack);
        return Math.round((base + bonus.getDurabilityBonus()) * bonus.getDurabilityMultiplier());
    }

    // Example for bool-type
//    public int getAutoSmelt(ItemStack stack) {
//        UpgradeBonus bonus = getBonus(stack);
//        return bonus.hasAutoSmelt() ? 1 : 0;
//    }



    // ===============
    // Tooltip section
    // ===============

    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            List<Component> tooltipComponents,
            TooltipFlag tooltipFlag
    ) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        Gemstone gemstone = stack.getOrDefault(ModDataComponents.GEMSTONE, Gemstone.EMPTY);
        Coating coating = stack.getOrDefault(ModDataComponents.COATING, Coating.EMPTY);



        if (gemstone.hasGemstone()) {
            var gemstoneItem = BuiltInRegistries.ITEM.get(gemstone.gemstone());
            tooltipComponents.add(Component.translatable("tooltip.neotools.gemstone",
                    gemstoneItem.getDescription()));
        }

        if (coating.hasCoating()) {
            tooltipComponents.add(Component.translatable("tooltip.neotools.coating",
                    coating.Coating().getPath()));
        }
    }
}