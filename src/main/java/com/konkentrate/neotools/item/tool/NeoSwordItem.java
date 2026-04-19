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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.function.Consumer;

public class NeoSwordItem extends SwordItem {

    public NeoSwordItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, properties.attributes(SwordItem.createAttributes(tier, attackDamage, attackSpeed)));
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

    /** On-hit effects hook — wire up auto-smelt, poison coating, etc. here */
    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);
        // UpgradeBonus bonus = getBonus(stack);
        // if (bonus.hasAutoSmelt()) { ... }
    }

    @Override
    public float getXpRepairRatio(ItemStack stack) {
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
            NeoDiggerItem.appendBonusLines(bonus, tooltip);
        }
        if (coating.hasCoating()) {
            UpgradeBonus bonus = ModUpgradeBonuses.getCoatingBonus(coating.Coating());
            tooltip.add(Component.translatable("tooltip.neotools.coating", coating.Coating().getPath())
                    .withStyle(ChatFormatting.GOLD));
            NeoDiggerItem.appendBonusLines(bonus, tooltip);
        }
    }
}

