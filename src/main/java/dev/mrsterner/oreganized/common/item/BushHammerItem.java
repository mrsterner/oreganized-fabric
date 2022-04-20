package dev.mrsterner.oreganized.common.item;

import com.google.common.collect.ImmutableMap;
import dev.mrsterner.oreganized.common.registry.OMaterials;
import dev.mrsterner.oreganized.common.registry.OTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;

import java.util.Map;

public class BushHammerItem extends MiningToolItem {
    public static final Map<Block, Block> EFFECTIVE_ON = ImmutableMap.of(
            Blocks.STONE, Blocks.COBBLESTONE,
            Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS,
            Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS,
            Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS
    );

    public BushHammerItem() {
        super(2.5F, -2.8F, OMaterials.LEAD, OTags.BUSH_HAMMER_BREAKABLE_BLOCKTAG,
                new Settings().group(ItemGroup.TOOLS).maxCount(1));
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (EFFECTIVE_ON.containsKey(state.getBlock())) return this.miningSpeed;
        return super.getMiningSpeedMultiplier(stack, state);
    }
}
