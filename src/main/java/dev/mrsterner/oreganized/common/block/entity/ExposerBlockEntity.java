package dev.mrsterner.oreganized.common.block.entity;

import dev.mrsterner.oreganized.common.registry.OBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExposerBlockEntity extends BlockEntity {
    public ExposerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ExposerBlockEntity(BlockPos pos, BlockState state) {
        super(OBlockEntityTypes.EXPOSER_BLOCK_ENTITY, pos, state);
    }

    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState state, T t) {
        if (world == null) return;
        if (world.isClient()) return;
    }


}
