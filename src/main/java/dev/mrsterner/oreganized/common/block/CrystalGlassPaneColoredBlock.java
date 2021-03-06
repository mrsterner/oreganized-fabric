package dev.mrsterner.oreganized.common.block;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class CrystalGlassPaneColoredBlock extends PaneBlock implements Stainable {
    public static final IntProperty TYPE = IntProperty.of("type", 0, 3);
    public static final int NORMAL = 0, ROTATED = 1, INNER = 2, OUTER = 3;
    final DyeColor color;

    public CrystalGlassPaneColoredBlock(DyeColor color) {
        super(AbstractBlock.Settings.of(Material.GLASS)
                .strength(0.3F)
                .sounds(BlockSoundGroup.GLASS)
                .nonOpaque());
        this.color = color;
        this.setDefaultState(getStateManager().getDefaultState()
                .with(TYPE, NORMAL));
    }

    @Override
    public DyeColor getColor() {
        return null;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(TYPE);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(TYPE, ctx.getPlayer().isSneaking() ? ROTATED : NORMAL);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN || direction == Direction.UP) {
            if (state.getBlock() == world.getBlockState(pos.up()).getBlock()
                    && state.getBlock() == world.getBlockState(pos.down()).getBlock()) {
                updatePattern(pos, world);
            } else if (state.getBlock() == world.getBlockState(pos.up(2)).getBlock()
                    && state.getBlock() == world.getBlockState(pos.up()).getBlock()
                    && state.getBlock() != world.getBlockState(pos.down()).getBlock()) {
                updatePattern(pos.up(), world);
            } else if (state.getBlock() == world.getBlockState(pos.down(2)).getBlock()
                    && state.getBlock() == world.getBlockState(pos.down()).getBlock()
                    && state.getBlock() != world.getBlockState(pos.up()).getBlock()) {
                updatePattern(pos.down(), world);
            }
            if (state.get(TYPE) == OUTER || state.get(TYPE) == INNER) {
                if (state.getBlock() != neighborState.getBlock()) {
                    return state.with(TYPE, ROTATED);
                }
            }
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }


    private void updatePattern(BlockPos centerBlockPos, WorldAccess worldAccess) {
        BlockState aboveBlock = worldAccess.getBlockState(centerBlockPos.up());
        BlockState centerBlock = worldAccess.getBlockState(centerBlockPos);
        BlockState belowBlock = worldAccess.getBlockState(centerBlockPos.down());
        if (centerBlock.getBlock() == aboveBlock.getBlock() && centerBlock.getBlock() == belowBlock.getBlock()) {
            if (aboveBlock.get(TYPE) == ROTATED && centerBlock.get(TYPE) == ROTATED && belowBlock.get(TYPE) == NORMAL) {
                worldAccess.setBlockState(centerBlockPos, centerBlock.with(TYPE, OUTER), 3);
            } else if (aboveBlock.get(TYPE) == NORMAL && centerBlock.get(TYPE) == NORMAL && belowBlock.get(TYPE) == ROTATED) {
                worldAccess.setBlockState(centerBlockPos, centerBlock.with(TYPE, INNER), 3);
            }
        }
    }


}
