package dev.mrsterner.oreganized.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class EngravedWeatheringCopperBlock extends EngravedBlock implements OOxidizable {
    private final Oxidizable.OxidationLevel oxidationLevel;

    public EngravedWeatheringCopperBlock(Oxidizable.OxidationLevel oxidationLevel, AbstractBlock.Settings settings) {
        super(settings);
        this.oxidationLevel = oxidationLevel;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.tickDegradation(state, world, pos, random);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return Oxidizable.getIncreasedOxidationBlock(state.getBlock()).isPresent();
    }


    @Override
    public Oxidizable.OxidationLevel getDegradationLevel() {
        return oxidationLevel;
    }
}
