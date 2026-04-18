package com.konkentrate.neotools.registry;

import com.konkentrate.neotools.NeoTools;
import com.konkentrate.neotools.recipe.UpgradeSmithingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    private static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, NeoTools.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<UpgradeSmithingRecipe>> TOOL_UPGRADE_SERIALIZER =
            SERIALIZERS.register("tool_upgrade", () -> new RecipeSerializer<>() {
                @Override
                public com.mojang.serialization.MapCodec<UpgradeSmithingRecipe> codec() {
                    return UpgradeSmithingRecipe.CODEC;
                }

                @Override
                public net.minecraft.network.codec.StreamCodec<net.minecraft.network.RegistryFriendlyByteBuf, UpgradeSmithingRecipe> streamCodec() {
                    return UpgradeSmithingRecipe.STREAM_CODEC;
                }
            });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}

