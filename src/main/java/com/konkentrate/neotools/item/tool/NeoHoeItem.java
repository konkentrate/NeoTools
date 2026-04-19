package com.konkentrate.neotools.item.tool;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class NeoHoeItem extends NeoDiggerItem {
    public NeoHoeItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, BlockTags.MINEABLE_WITH_HOE, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.neoforged.neoforge.common.ItemAbility itemAbility) {
        return net.neoforged.neoforge.common.ItemAbilities.DEFAULT_HOE_ACTIONS.contains(itemAbility);
    }
}

