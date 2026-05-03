package com.konkentrate.neotools.event;

import com.konkentrate.neotools.NeoTools;
import com.konkentrate.neotools.item.component.Addon;
import com.konkentrate.neotools.item.component.Addons;
import com.konkentrate.neotools.registry.ModAddonRegistry;
import com.konkentrate.neotools.registry.ModDataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = NeoTools.MODID)
public class AddonAnvilHandler {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();   // Tool
        ItemStack right = event.getRight(); // Addon material

        // Must have a tool on left side with ADDONS component
        if (left.isEmpty() || !left.has(ModDataComponents.ADDONS)) {
            return;
        }

        // Must have exactly 1 addon material on right side (prevent consuming full stacks)
        if (right.isEmpty() || right.getCount() != 1) {
            return;
        }

        // Try to match right item to an addon material
        var registry = ModAddonRegistry.getInstance();
        for (var material : registry.getAddonMaterials()) {
            // Check if material specifies a representative item
            if (material.materialItem().isEmpty()) {
                continue;
            }

            // Check if right item matches this material's item
            Item materialItem = BuiltInRegistries.ITEM.get(material.materialItem().get());
            if (right.getItem() == materialItem) {
                // Found matching addon material!
                // Extract addon type from material ID (e.g., "gemstone" from "neotools:gemstone/diamond")
                String materialPath = material.id().getPath();
                String typeName = materialPath.split("/")[0];
                ResourceLocation typeId = ResourceLocation.fromNamespaceAndPath(material.id().getNamespace(), typeName);

                // Create new addon
                Addon newAddon = new Addon(typeId, material.id());

                // Add to tool's addons, respecting maxStackSize for the type
                ItemStack result = left.copy();
                Addons currentAddons = result.getOrDefault(ModDataComponents.ADDONS, Addons.EMPTY);

                // Check current count of this addon type
                long countOfType = currentAddons.addons().stream().filter(a -> a.type().equals(typeId)).count();

                // Check max stack size for this type
                var addonType = registry.getAddonType(typeId);
                if (addonType == null || countOfType >= addonType.maxStackSize()) {
                    return;
                }

                List<Addon> newAddonsList = new ArrayList<>(currentAddons.addons());
                newAddonsList.add(newAddon);
                result.set(ModDataComponents.ADDONS, new Addons(newAddonsList));

                // Set output and cost
                event.setOutput(result);
                event.setCost(5);
                return;
            }
        }
    }
}
