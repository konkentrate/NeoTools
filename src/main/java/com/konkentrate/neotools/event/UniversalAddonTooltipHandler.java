package com.konkentrate.neotools.event;

import com.konkentrate.neotools.NeoTools;
import com.konkentrate.neotools.item.component.Addons;
import com.konkentrate.neotools.item.component.UpgradeBonus;
import com.konkentrate.neotools.registry.ModAddonRegistry;
import com.konkentrate.neotools.registry.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

/**
 * Universal tooltip handler that works for ANY item with the ADDONS component,
 * including vanilla tools.
 */
@EventBusSubscriber(modid = NeoTools.MODID)
public class UniversalAddonTooltipHandler {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        var stack = event.getItemStack();

        // Check if this item has addons
        if (!stack.has(ModDataComponents.ADDONS)) return;

        Addons addons = stack.getOrDefault(ModDataComponents.ADDONS, Addons.EMPTY);
        if (addons.isEmpty()) return;

        var tooltip = event.getToolTip();

        // Add a blank line before addon info
        tooltip.add(Component.empty());

        // Add each addon and its bonuses
        for (var addon : addons.addons()) {
            var material = ModAddonRegistry.getInstance().getAddonMaterial(addon.material());
            if (material != null) {
                // Show addon header
                String typeName = addon.type().getPath(); // Just the path part
                String materialName = addon.material().getPath(); // Just the path part

                tooltip.add(Component.literal("⬥ " + capitalize(typeName) + ": " + capitalize(materialName))
                        .withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD));

                // Show bonuses from this addon
                appendBonusLines(material.toBonus(), tooltip);
            }
        }
    }

    private static void appendBonusLines(UpgradeBonus bonus, java.util.List<Component> tooltip) {
        // Attack bonuses
        if (bonus.getAttackDamageBonus() != 0f)
            tooltip.add(statLine(" ├ Attack Damage", formatFlat(bonus.getAttackDamageBonus()),
                    bonus.getAttackDamageBonus() > 0));
        if (bonus.getAttackDamageMultiplier() != 1f)
            tooltip.add(statLine(" ├ Attack Damage", formatPercent(bonus.getAttackDamageMultiplier()),
                    bonus.getAttackDamageMultiplier() > 1f));
        if (bonus.getAttackSpeedBonus() != 0f)
            tooltip.add(statLine(" ├ Attack Speed", formatFlat(bonus.getAttackSpeedBonus()),
                    bonus.getAttackSpeedBonus() > 0));

        // Mining bonuses
        if (bonus.getMiningSpeedBonus() != 0f)
            tooltip.add(statLine(" ├ Mining Speed", formatFlat(bonus.getMiningSpeedBonus()),
                    bonus.getMiningSpeedBonus() > 0));
        if (bonus.getMiningSpeedMultiplier() != 1f)
            tooltip.add(statLine(" ├ Mining Speed", formatPercent(bonus.getMiningSpeedMultiplier()),
                    bonus.getMiningSpeedMultiplier() > 1f));

        // Durability bonuses
        if (bonus.getDurabilityBonus() != 0)
            tooltip.add(statLine(" ├ Durability", formatFlat(bonus.getDurabilityBonus()),
                    bonus.getDurabilityBonus() > 0));
        if (bonus.getDurabilityMultiplier() != 1f)
            tooltip.add(statLine(" ├ Durability", formatPercent(bonus.getDurabilityMultiplier()),
                    bonus.getDurabilityMultiplier() > 1f));

        // Special bonuses
        if (bonus.getFortuneBonus() != 0)
            tooltip.add(statLine(" ├ Fortune", formatFlat(bonus.getFortuneBonus()),
                    bonus.getFortuneBonus() > 0));
        if (bonus.getExperienceMultiplier() != 1f)
            tooltip.add(statLine(" ├ Experience", formatPercent(bonus.getExperienceMultiplier()),
                    bonus.getExperienceMultiplier() > 1f));
        if (bonus.getEnchantabilityBonus() != 0)
            tooltip.add(statLine(" ├ Enchantability", formatFlat(bonus.getEnchantabilityBonus()),
                    bonus.getEnchantabilityBonus() > 0));

        // Flags
        if (bonus.hasAutoSmelt())
            tooltip.add(Component.literal(" └ Auto-Smelt")
                    .withStyle(ChatFormatting.YELLOW, ChatFormatting.ITALIC));
    }

    private static Component statLine(String prefix, String value, boolean positive) {
        return Component.literal(prefix + ": " + value)
                .withStyle(positive ? ChatFormatting.GREEN : ChatFormatting.RED);
    }

    private static String formatFlat(float value) {
        String sign = value > 0 ? "+" : "";
        return value == Math.floor(value)
                ? sign + (int) value
                : sign + String.format("%.1f", value);
    }

    private static String formatFlat(int value) {
        return (value > 0 ? "+" : "") + value;
    }

    private static String formatPercent(float multiplier) {
        float pct = (multiplier - 1f) * 100f;
        String sign = pct > 0 ? "+" : "";
        return pct == Math.floor(pct)
                ? sign + (int) pct + "%"
                : sign + String.format("%.1f", pct) + "%";
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        // Replace underscores with spaces and capitalize each word
        String[] words = str.replace("_", " ").split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!result.isEmpty()) result.append(" ");
            result.append(Character.toUpperCase(word.charAt(0)))
                  .append(word.substring(1).toLowerCase());
        }
        return result.toString();
    }
}

