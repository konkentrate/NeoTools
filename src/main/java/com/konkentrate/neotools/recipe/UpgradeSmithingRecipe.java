package com.konkentrate.neotools.recipe;

import com.konkentrate.neotools.item.component.Coating;
import com.konkentrate.neotools.item.component.Gemstone;
import com.konkentrate.neotools.registry.ModDataComponents;
import com.konkentrate.neotools.registry.ModRecipes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;

/**
 * A smithing table recipe that upgrades a tool with a gemstone or coating,
 * requiring no template item — just base tool + upgrade material.
 */
public class UpgradeSmithingRecipe implements SmithingRecipe {

    public enum UpgradeType { GEMSTONE, COATING }

    private final Ingredient base;
    private final Ingredient addition;
    private final UpgradeType upgradeType;
    private final ResourceLocation upgradeId;

    public UpgradeSmithingRecipe(Ingredient base, Ingredient addition, UpgradeType upgradeType, ResourceLocation upgradeId) {
        this.base = base;
        this.addition = addition;
        this.upgradeType = upgradeType;
        this.upgradeId = upgradeId;
    }

    /** Template slot must be empty — no template required */
    @Override
    public boolean isTemplateIngredient(ItemStack stack) {
        return stack.isEmpty();
    }

    @Override
    public boolean isBaseIngredient(ItemStack stack) {
        return base.test(stack);
    }

    @Override
    public boolean isAdditionIngredient(ItemStack stack) {
        return addition.test(stack);
    }

    @Override
    public boolean matches(SmithingRecipeInput input, Level level) {
        return input.template().isEmpty()
                && base.test(input.base())
                && addition.test(input.addition());
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput input, HolderLookup.Provider registries) {
        ItemStack result = input.base().copy();
        return switch (upgradeType) {
            case GEMSTONE -> {
                result.set(ModDataComponents.GEMSTONE.get(), new Gemstone(upgradeId));
                yield result;
            }
            case COATING -> {
                result.set(ModDataComponents.COATING.get(), new Coating(upgradeId));
                yield result;
            }
        };
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.TOOL_UPGRADE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeType.SMITHING;
    }

    // =====================
    // Codec / Serializer
    // =====================

    private static final com.mojang.serialization.Codec<UpgradeType> UPGRADE_TYPE_CODEC =
            com.mojang.serialization.Codec.STRING.comapFlatMap(
                    s -> {
                        try { return com.mojang.serialization.DataResult.success(UpgradeType.valueOf(s.toUpperCase())); }
                        catch (IllegalArgumentException e) { return com.mojang.serialization.DataResult.error(() -> "Unknown upgrade_type: " + s); }
                    },
                    t -> t.name().toLowerCase()
            );

    public static final MapCodec<UpgradeSmithingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Ingredient.CODEC.fieldOf("base").forGetter(r -> r.base),
                    Ingredient.CODEC.fieldOf("addition").forGetter(r -> r.addition),
                    UPGRADE_TYPE_CODEC.fieldOf("upgrade_type").forGetter(r -> r.upgradeType),
                    ResourceLocation.CODEC.fieldOf("upgrade_id").forGetter(r -> r.upgradeId)
            ).apply(instance, UpgradeSmithingRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, UpgradeSmithingRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, r -> r.base,
                    Ingredient.CONTENTS_STREAM_CODEC, r -> r.addition,
                    net.minecraft.network.codec.ByteBufCodecs.STRING_UTF8.map(
                            s -> UpgradeType.valueOf(s.toUpperCase()),
                            t -> t.name().toLowerCase()
                    ), r -> r.upgradeType,
                    ResourceLocation.STREAM_CODEC, r -> r.upgradeId,
                    UpgradeSmithingRecipe::new
            );
}
