package com.konkentrate.neotools.item.tool;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class NeoPickaxeItem extends NeoDiggerItem {
    public NeoPickaxeItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, BlockTags.MINEABLE_WITH_PICKAXE, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.neoforged.neoforge.common.ItemAbility itemAbility) {
        return net.neoforged.neoforge.common.ItemAbilities.DEFAULT_PICKAXE_ACTIONS.contains(itemAbility);
    }

}