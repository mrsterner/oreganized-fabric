package dev.mrsterner.oreganized.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class CrystalGlassBase extends GlassBlock {
    public CrystalGlassBase(Settings settings) {
        super(settings
                .strength(0.3F)
                .sounds(BlockSoundGroup.GLASS)
                .nonOpaque()
                .allowsSpawning(CrystalGlassBase::never)
                .suffocates(CrystalGlassBase::never)
                .blockVision(CrystalGlassBase::never));
    }

    private static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos) {
        return false;
    }

    private static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }


}
