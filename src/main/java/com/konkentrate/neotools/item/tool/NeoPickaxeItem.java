package com.konkentrate.neotools.item.tool;

import com.konkentrate.neotools.component.ModDataComponents;
import com.konkentrate.neotools.item.component.Gemstone;
import com.konkentrate.neotools.item.component.Coating;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Tier;

import java.util.List;

public class NeoPickaxeItem extends PickaxeItem {
    public NeoPickaxeItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

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