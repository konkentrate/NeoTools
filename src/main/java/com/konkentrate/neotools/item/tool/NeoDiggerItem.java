package com.konkentrate.neotools.item.tool;

import com.konkentrate.neotools.item.component.Coating;
import com.konkentrate.neotools.item.component.Gemstone;
import com.konkentrate.neotools.item.component.UpgradeBonus;
import com.konkentrate.neotools.registry.ModDataComponents;
import com.konkentrate.neotools.registry.ModUpgradeBonuses;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.function.Consumer;

/**
 * Base class for all Neo digging tools (pickaxe, axe, shovel, hoe).
 * Subclasses only need to supply the correct BlockTag in super().
 * All upgrade bonus logic lives here.
 */
public abstract class NeoDiggerItem extends DiggerItem {

    protected NeoDiggerItem(Tier tier, net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> blocks,
                            float attackDamage, float attackSpeed, Properties properties) {
        super(tier, blocks, properties.attributes(DiggerItem.createAttributes(tier, attackDamage, attackSpeed)));
    }

    // ─────────────────────────────────────────────────
    // Bonus helpers
    // ─────────────────────────────────────────────────

    protected UpgradeBonus getBonus(ItemStack stack) {
        Gemstone gem  = stack.getOrDefault(ModDataComponents.GEMSTONE, Gemstone.EMPTY);
        Coating  coat = stack.getOrDefault(ModDataComponents.COATING,  Coating.EMPTY);
        return ModUpgradeBonuses.getCombinedBonus(
                gem.hasGemstone()  ? gem.gemstone()  : null,
                coat.hasCoating()  ? coat.Coating()  : null
        );
    }

    // ─────────────────────────────────────────────────
    // Bonus applications
    // ─────────────────────────────────────────────────

    /** Durability: base + flat bonus, then × multiplier */
    @Override
    public int getMaxDamage(ItemStack stack) {
        UpgradeBonus bonus = getBonus(stack);
        int base = super.getMaxDamage(stack);
        return Math.round((base + bonus.getDurabilityBonus()) * bonus.getDurabilityMultiplier());
    }

    /** Durability loss reduction (e.g. a tough coating takes less wear) */
    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<net.minecraft.world.item.Item> onBroken) {
        UpgradeBonus bonus = getBonus(stack);
        float multiplier = bonus.getDurabilityMultiplier();
        // Reuse durability multiplier inversely: higher mult = less damage taken per use
        int reduced = Math.round(amount / multiplier);
        return Math.max(1, reduced); // always at least 1 so the item can still break
    }

    /** Mining speed: base × speed multiplier + flat bonus (only when the tool is effective) */
    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        float base = super.getDestroySpeed(stack, state);
        if (base <= 1.0f) return base; // not effective on this block, skip bonus
        UpgradeBonus bonus = getBonus(stack);
        return base * bonus.getMiningSpeedMultiplier() + bonus.getMiningSpeedBonus();
    }

    // Attack damage/speed bonuses are applied via ToolAttributeHandler (ItemAttributeModifiersEvent).
    // See: ToolAttributeHandler.java

    /** On-hit effects: e.g. auto-smelt (placeholder — wire up to server-side logic as needed) */
    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);
        // UpgradeBonus bonus = getBonus(stack);
        // if (bonus.hasAutoSmelt()) { ... }
    }

    /** Enchantability from gemstone bonus (no ItemStack available here — uses best-effort from default) */
    @Override
    public int getEnchantmentValue() {
        // NOTE: getEnchantmentValue() has no ItemStack parameter in vanilla.
        // For stack-sensitive enchantability, handle it in a separate event or
        // override getEnchantmentValue(ItemStack) via IItemExtension if/when added.
        return super.getEnchantmentValue();
    }

    /**
     * XP repair cost reduction: a coating/gemstone can make anvil repair cheaper.
     * Default is 2 XP per durability point restored; lower = cheaper.
     */
    @Override
    public float getXpRepairRatio(ItemStack stack) {
        // UpgradeBonus bonus = getBonus(stack);
        // return IItemExtension.super.getXpRepairRatio(stack) * bonus.getRepairCostMultiplier();
        return super.getXpRepairRatio(stack);
    }

    // ─────────────────────────────────────────────────
    // Tooltip
    // ─────────────────────────────────────────────────

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
                                List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        Gemstone gemstone = stack.getOrDefault(ModDataComponents.GEMSTONE, Gemstone.EMPTY);
        Coating  coating  = stack.getOrDefault(ModDataComponents.COATING,  Coating.EMPTY);

        if (gemstone.hasGemstone()) {
            UpgradeBonus bonus = ModUpgradeBonuses.getGemstoneBonus(gemstone.gemstone());
            var gemItem = BuiltInRegistries.ITEM.get(gemstone.gemstone());
            tooltip.add(Component.translatable("tooltip.neotools.gemstone", gemItem.getDescription())
                    .withStyle(ChatFormatting.AQUA));
            appendBonusLines(bonus, tooltip);
        }
        if (coating.hasCoating()) {
            UpgradeBonus bonus = ModUpgradeBonuses.getCoatingBonus(coating.Coating());
            tooltip.add(Component.translatable("tooltip.neotools.coating", coating.Coating().getPath())
                    .withStyle(ChatFormatting.GOLD));
            appendBonusLines(bonus, tooltip);
        }
    }

    /**
     * Appends all non-zero stat bonus lines from an UpgradeBonus to the tooltip.
     * Positive values are shown in green, negative in red.
     */
    public static void appendBonusLines(UpgradeBonus bonus, List<Component> tooltip) {
        // Flat attack damage
        if (bonus.getAttackDamageBonus() != 0f)
            tooltip.add(statLine("tooltip.neotools.stat.attack_damage_bonus",
                    formatFlat(bonus.getAttackDamageBonus()), bonus.getAttackDamageBonus() > 0));
        // Attack damage multiplier
        if (bonus.getAttackDamageMultiplier() != 1f)
            tooltip.add(statLine("tooltip.neotools.stat.attack_damage_mult",
                    formatPercent(bonus.getAttackDamageMultiplier()), bonus.getAttackDamageMultiplier() > 1f));
        // Attack speed
        if (bonus.getAttackSpeedBonus() != 0f)
            tooltip.add(statLine("tooltip.neotools.stat.attack_speed_bonus",
                    formatFlat(bonus.getAttackSpeedBonus()), bonus.getAttackSpeedBonus() > 0));

        // Flat mining speed
        if (bonus.getMiningSpeedBonus() != 0f)
            tooltip.add(statLine("tooltip.neotools.stat.mining_speed_bonus",
                    formatFlat(bonus.getMiningSpeedBonus()), bonus.getMiningSpeedBonus() > 0));
        // Mining speed multiplier
        if (bonus.getMiningSpeedMultiplier() != 1f)
            tooltip.add(statLine("tooltip.neotools.stat.mining_speed_mult",
                    formatPercent(bonus.getMiningSpeedMultiplier()), bonus.getMiningSpeedMultiplier() > 1f));

        // Flat durability
        if (bonus.getDurabilityBonus() != 0)
            tooltip.add(statLine("tooltip.neotools.stat.durability_bonus",
                    formatFlat(bonus.getDurabilityBonus()), bonus.getDurabilityBonus() > 0));
        // Durability multiplier
        if (bonus.getDurabilityMultiplier() != 1f)
            tooltip.add(statLine("tooltip.neotools.stat.durability_mult",
                    formatPercent(bonus.getDurabilityMultiplier()), bonus.getDurabilityMultiplier() > 1f));

        // Fortune
        if (bonus.getFortuneBonus() != 0)
            tooltip.add(statLine("tooltip.neotools.stat.fortune_bonus",
                    formatFlat(bonus.getFortuneBonus()), bonus.getFortuneBonus() > 0));

        // XP multiplier
        if (bonus.getExperienceMultiplier() != 1f)
            tooltip.add(statLine("tooltip.neotools.stat.xp_mult",
                    formatPercent(bonus.getExperienceMultiplier()), bonus.getExperienceMultiplier() > 1f));

        // Enchantability
        if (bonus.getEnchantabilityBonus() != 0)
            tooltip.add(statLine("tooltip.neotools.stat.enchantability_bonus",
                    formatFlat(bonus.getEnchantabilityBonus()), bonus.getEnchantabilityBonus() > 0));

        // Auto-smelt flag
        if (bonus.hasAutoSmelt())
            tooltip.add(Component.translatable("tooltip.neotools.stat.auto_smelt")
                    .withStyle(ChatFormatting.YELLOW));
    }

    private static Component statLine(String key, String value, boolean positive) {
        return Component.translatable(key, value)
                .withStyle(positive ? ChatFormatting.GREEN : ChatFormatting.RED);
    }

    /** Formats a flat numeric bonus, e.g. "+3" or "+1.5" */
    private static String formatFlat(float value) {
        String sign = value > 0 ? "+" : "";
        return value == Math.floor(value)
                ? sign + (int) value
                : sign + String.format("%.1f", value);
    }

    private static String formatFlat(int value) {
        return (value > 0 ? "+" : "") + value;
    }

    /** Formats a multiplier as a percentage change, e.g. 1.25f → "+25%" */
    private static String formatPercent(float multiplier) {
        float pct = (multiplier - 1f) * 100f;
        String sign = pct > 0 ? "+" : "";
        return pct == Math.floor(pct)
                ? sign + (int) pct + "%"
                : sign + String.format("%.1f", pct) + "%";
    }
}

