package com.konkentrate.neotools.item.tool;

import com.konkentrate.neotools.item.component.Coating;
import com.konkentrate.neotools.item.component.Gemstone;
import com.konkentrate.neotools.item.component.UpgradeBonus;
import com.konkentrate.neotools.registry.ModDataComponents;
import com.konkentrate.neotools.registry.ModUpgradeBonuses;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;
import java.util.function.Consumer;

public class NeoSwordItem extends SwordItem {

    public NeoSwordItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    // ─────────────────────────────────────────────────
    // Bonus helpers (mirrored from NeoDiggerItem)
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

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        ItemAttributeModifiers base = super.getDefaultAttributeModifiers(stack);
        UpgradeBonus bonus = getBonus(stack);

        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
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

    /** On-hit effects hook — wire up auto-smelt, poison coating, etc. here */
    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker); // hurtAndBreak(1)
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
            var gemItem = BuiltInRegistries.ITEM.get(gemstone.gemstone());
            tooltip.add(Component.translatable("tooltip.neotools.gemstone", gemItem.getDescription()));
        }
        if (coating.hasCoating()) {
            tooltip.add(Component.translatable("tooltip.neotools.coating", coating.Coating().getPath()));
        }
    }
}

