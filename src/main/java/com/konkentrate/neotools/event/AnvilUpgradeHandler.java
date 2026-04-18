package com.konkentrate.neotools.event;

import com.konkentrate.neotools.NeoTools;
import com.konkentrate.neotools.item.component.Coating;
import com.konkentrate.neotools.item.component.Gemstone;
import com.konkentrate.neotools.item.component.UpgradeBonus;
import com.konkentrate.neotools.registry.ModDataComponents;
import com.konkentrate.neotools.registry.ModUpgradeBonuses;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

@EventBusSubscriber(modid = NeoTools.MODID)
public class AnvilUpgradeHandler {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack base     = event.getLeft();
        ItemStack material = event.getRight();

        if (base.isEmpty() || material.isEmpty()) return;

        // Only act on tools that have our upgrade slots
        if (!base.has(ModDataComponents.GEMSTONE) && !base.has(ModDataComponents.COATING)) return;

        ResourceLocation materialId = BuiltInRegistries.ITEM.getKey(material.getItem());

        // ── Try gemstone ──────────────────────────────────────────
        UpgradeBonus gemBonus = ModUpgradeBonuses.getGemstoneBonus(materialId);
        if (!gemBonus.isEmpty()) {
            ItemStack result = base.copy();
            result.set(ModDataComponents.GEMSTONE.get(), new Gemstone(materialId));
            event.setOutput(result);
            event.setCost(1);           // XP levels
            event.setMaterialCost(1);   // consumes 1 material
            return;
        }

        // ── Try coating ───────────────────────────────────────────
        UpgradeBonus coatBonus = ModUpgradeBonuses.getCoatingBonus(materialId);
        if (!coatBonus.isEmpty()) {
            ItemStack result = base.copy();
            result.set(ModDataComponents.COATING.get(), new Coating(materialId));
            event.setOutput(result);
            event.setCost(1);
            event.setMaterialCost(1);
        }
    }
}


