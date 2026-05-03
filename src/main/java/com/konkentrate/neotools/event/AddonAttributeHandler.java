package com.konkentrate.neotools.event;

import com.konkentrate.neotools.NeoTools;
import com.konkentrate.neotools.item.component.Addons;
import com.konkentrate.neotools.item.component.AddonBonus;
import com.konkentrate.neotools.registry.ModAddonRegistry;
import com.konkentrate.neotools.registry.ModDataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

@EventBusSubscriber(modid = NeoTools.MODID)
public class AddonAttributeHandler {

    private static final ResourceLocation ATK_BONUS_ID  =
            ResourceLocation.fromNamespaceAndPath(NeoTools.MODID, "addon_atk_bonus");
    private static final ResourceLocation ATK_MULT_ID   =
            ResourceLocation.fromNamespaceAndPath(NeoTools.MODID, "addon_atk_mult");
    private static final ResourceLocation SPD_BONUS_ID  =
            ResourceLocation.fromNamespaceAndPath(NeoTools.MODID, "addon_spd_bonus");

    @SubscribeEvent
    public static void onAttributeModifiers(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();

        // Only apply to items that carry our addon component
        if (!stack.has(ModDataComponents.ADDONS)) return;

        Addons addons = stack.getOrDefault(ModDataComponents.ADDONS, Addons.EMPTY);
        if (addons.isEmpty()) return;

        // Combine all addon bonuses
        AddonBonus combinedBonus = AddonBonus.EMPTY;
        for (var addon : addons.addons()) {
            var material = ModAddonRegistry.getInstance().getAddonMaterial(addon.material());
            if (material != null) {
                combinedBonus = combinedBonus.combine(material.toBonus());
            }
        }

        if (combinedBonus.isEmpty()) return;

        if (combinedBonus.getAttackDamageBonus() != 0f) {
            event.addModifier(
                    Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(ATK_BONUS_ID,
                            combinedBonus.getAttackDamageBonus(),
                            AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.MAINHAND
            );
        }

        if (combinedBonus.getAttackDamageMultiplier() != 1f) {
            event.addModifier(
                    Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(ATK_MULT_ID,
                            combinedBonus.getAttackDamageMultiplier() - 1f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                    EquipmentSlotGroup.MAINHAND
            );
        }

        if (combinedBonus.getAttackSpeedBonus() != 0f) {
            event.addModifier(
                    Attributes.ATTACK_SPEED,
                    new AttributeModifier(SPD_BONUS_ID,
                            combinedBonus.getAttackSpeedBonus(),
                            AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.MAINHAND
            );
        }
    }
}


