package com.konkentrate.neotools.datagen;

import com.konkentrate.neotools.NeoTools;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, NeoTools.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Generate simple item models that reuse vanilla models/textures by setting the
        // parent to the corresponding minecraft:item/<name> model.
        String[] toolNames = new String[] {
                "wooden_pickaxe", "wooden_axe", "wooden_shovel", "wooden_hoe", "wooden_sword",
                "stone_pickaxe", "stone_axe", "stone_shovel", "stone_hoe", "stone_sword",
                "iron_pickaxe", "iron_axe", "iron_shovel", "iron_hoe", "iron_sword",
                "golden_pickaxe", "golden_axe", "golden_shovel", "golden_hoe", "golden_sword",
                "diamond_pickaxe", "diamond_axe", "diamond_shovel", "diamond_hoe", "diamond_sword",
                "netherite_pickaxe", "netherite_axe", "netherite_shovel", "netherite_hoe", "netherite_sword"
        };

        for (String name : toolNames) {
            // withExistingParent will create assets/neotools/models/item/<name>.json with parent
            // pointing to the vanilla model, causing the mod item to use the vanilla model+textures.
            withExistingParent(name, ResourceLocation.parse("minecraft:item/" + name));
        }
    }
}
