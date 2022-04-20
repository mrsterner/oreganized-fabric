package dev.mrsterner.oreganized.common.block;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import dev.mrsterner.oreganized.common.registry.OObjects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Degradable;
import net.minecraft.block.Oxidizable;

import java.util.Optional;

public interface OOxidizable extends Degradable<Oxidizable.OxidationLevel> {

    Supplier<BiMap<Block, Block>> OXIDATION_LEVEL_INCREASES = Suppliers.memoize(() -> {
        return ImmutableBiMap.<Block, Block>builder()
                .put(OObjects.ENGRAVED_CUT_COPPER, OObjects.ENGRAVED_EXPOSED_CUT_COPPER)
                .put(OObjects.ENGRAVED_EXPOSED_CUT_COPPER, OObjects.ENGRAVED_WEATHERED_CUT_COPPER)
                .put(OObjects.ENGRAVED_WEATHERED_CUT_COPPER, OObjects.ENGRAVED_OXIDIZED_CUT_COPPER).build();
    });


    Supplier<BiMap<Block, Block>> OXIDATION_LEVEL_DECREASES = Suppliers.memoize(() -> {
        return OXIDATION_LEVEL_INCREASES.get().inverse();
    });

    static Optional<Block> getDecreasedOxidationBlock(Block block) {
        return Optional.ofNullable((Block)((BiMap)OXIDATION_LEVEL_DECREASES.get()).get(block));
    }

    static Block getUnaffectedOxidationBlock(Block block) {
        Block block2 = block;

        for(Block block3 = (Block)((BiMap)OXIDATION_LEVEL_DECREASES.get()).get(block);
            block3 != null;
            block3 = (Block)((BiMap)OXIDATION_LEVEL_DECREASES.get()).get(block3)
        ) {
            block2 = block3;
        }

        return block2;
    }

    static Optional<BlockState> getDecreasedOxidationState(BlockState state) {
        return getDecreasedOxidationBlock(state.getBlock()).map(block -> block.getStateWithProperties(state));
    }

    static Optional<Block> getIncreasedOxidationBlock(Block block) {
        return Optional.ofNullable((Block)((BiMap)OXIDATION_LEVEL_INCREASES.get()).get(block));
    }

    static BlockState getUnaffectedOxidationState(BlockState state) {
        return getUnaffectedOxidationBlock(state.getBlock()).getStateWithProperties(state);
    }

    @Override
    default Optional<BlockState> getDegradationResult(BlockState state) {
        return getIncreasedOxidationBlock(state.getBlock()).map(block -> block.getStateWithProperties(state));
    }

    @Override
    default float getDegradationChanceMultiplier() {
        return this.getDegradationLevel() == Oxidizable.OxidationLevel.UNAFFECTED ? 0.75F : 1.0F;
    }


}
