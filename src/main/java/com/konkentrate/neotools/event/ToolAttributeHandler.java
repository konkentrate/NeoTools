package com.konkentrate.neotools.event;

import com.konkentrate.neotools.NeoTools;
import com.konkentrate.neotools.item.component.Coating;
import com.konkentrate.neotools.item.component.Gemstone;
import com.konkentrate.neotools.item.component.UpgradeBonus;
import com.konkentrate.neotools.registry.ModDataComponents;
import com.konkentrate.neotools.registry.ModUpgradeBonuses;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

@EventBusSubscriber(modid = NeoTools.MODID)
public class ToolAttributeHandler {

    private static final ResourceLocation ATK_BONUS_ID  =
            ResourceLocation.fromNamespaceAndPath(NeoTools.MODID, "upgrade_atk_bonus");
    private static final ResourceLocation ATK_MULT_ID   =
            ResourceLocation.fromNamespaceAndPath(NeoTools.MODID, "upgrade_atk_mult");
    private static final ResourceLocation SPD_BONUS_ID  =
            ResourceLocation.fromNamespaceAndPath(NeoTools.MODID, "upgrade_spd_bonus");

    @SubscribeEvent
    public static void onAttributeModifiers(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();

        // Only apply to items that carry our upgrade components
        if (!stack.has(ModDataComponents.GEMSTONE) && !stack.has(ModDataComponents.COATING)) return;

        Gemstone gem  = stack.getOrDefault(ModDataComponents.GEMSTONE, Gemstone.EMPTY);
        Coating  coat = stack.getOrDefault(ModDataComponents.COATING,  Coating.EMPTY);
        UpgradeBonus bonus = ModUpgradeBonuses.getCombinedBonus(
                gem.hasGemstone() ? gem.gemstone() : null,
                coat.hasCoating() ? coat.Coating() : null
        );

        if (bonus.isEmpty()) return;

        if (bonus.getAttackDamageBonus() != 0f) {
            event.addModifier(
                    Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(ATK_BONUS_ID,
                            bonus.getAttackDamageBonus(),
                            AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.MAINHAND
            );
        }

        if (bonus.getAttackDamageMultiplier() != 1f) {
            event.addModifier(
                    Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(ATK_MULT_ID,
                            bonus.getAttackDamageMultiplier() - 1f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                    EquipmentSlotGroup.MAINHAND
            );
        }

        if (bonus.getAttackSpeedBonus() != 0f) {
            event.addModifier(
                    Attributes.ATTACK_SPEED,
                    new AttributeModifier(SPD_BONUS_ID,
                            bonus.getAttackSpeedBonus(),
                            AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.MAINHAND
            );
        }
    }
}


