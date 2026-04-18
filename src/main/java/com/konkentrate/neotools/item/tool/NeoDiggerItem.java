package com.konkentrate.neotools.item.tool;

import com.konkentrate.neotools.item.component.Coating;
import com.konkentrate.neotools.item.component.Gemstone;
import com.konkentrate.neotools.item.component.UpgradeBonus;
import com.konkentrate.neotools.registry.ModDataComponents;
import com.konkentrate.neotools.registry.ModUpgradeBonuses;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.function.Consumer;

/**
 * Base class for all Neo digging tools (pickaxe, axe, shovel, hoe).
 * Subclasses only need to supply the correct BlockTag in super().
 * All upgrade bonus logic lives here.
 */
public abstract class NeoDiggerItem extends DiggerItem {

    protected NeoDiggerItem(Tier tier, net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> blocks, Properties properties) {
        super(tier, blocks, properties);
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

    /**
     * Attack damage + speed via stack-sensitive attribute modifiers.
     * Only adds modifiers when there is actually a bonus to avoid polluting the attribute list.
     */
    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        ItemAttributeModifiers base = super.getDefaultAttributeModifiers(stack);
        UpgradeBonus bonus = getBonus(stack);

        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        // Copy existing modifiers
        base.modifiers().forEach(e -> builder.add(e.attribute(), e.modifier(), e.slot()));

        if (bonus.getAttackDamageBonus() != 0f) {
            builder.add(Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath("neotools", "upgrade_atk_bonus"),
                            bonus.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.MAINHAND);
        }
        if (bonus.getAttackDamageMultiplier() != 1f) {
            builder.add(Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath("neotools", "upgrade_atk_mult"),
                            bonus.getAttackDamageMultiplier() - 1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                    EquipmentSlotGroup.MAINHAND);
        }

        return builder.build();
    }

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
            var gemItem = BuiltInRegistries.ITEM.get(gemstone.gemstone());
            tooltip.add(Component.translatable("tooltip.neotools.gemstone", gemItem.getDescription()));
        }
        if (coating.hasCoating()) {
            tooltip.add(Component.translatable("tooltip.neotools.coating", coating.Coating().getPath()));
        }
    }
}

