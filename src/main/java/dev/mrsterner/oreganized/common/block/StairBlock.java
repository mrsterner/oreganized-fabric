package dev.mrsterner.oreganized.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;

public class StairBlock extends StairsBlock {
    public StairBlock(Block base, Settings settings) {
        super(base.getDefaultState(), settings);
    }
}
