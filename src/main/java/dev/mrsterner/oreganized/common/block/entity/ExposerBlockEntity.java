package dev.mrsterner.oreganized.common.block.entity;

import dev.mrsterner.oreganized.common.registry.OBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class ExposerBlockEntity extends BlockEntity {
    public ExposerBlockEntity(BlockPos pos, BlockState state) {
        super(OBlockEntityTypes.EXPOSER_BLOCK_ENTITY, pos, state);
    }
}
