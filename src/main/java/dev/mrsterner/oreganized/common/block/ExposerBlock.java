package dev.mrsterner.oreganized.common.block;

import dev.mrsterner.oreganized.common.block.entity.ExposerBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class ExposerBlock extends HorizontalFacingBlock implements BlockEntityProvider {
    public static final float RANGE = 16.0f;
    public static final IntProperty LEVEL = Properties.AGE_3;
    public static final int[] POWER_STATES = new int[] {0, 1, 2, 3};
    boolean isUndeadNearby = false;
    public ExposerBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.SOUTH).with(LEVEL, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, LEVEL);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }


    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState state) {
        return new ExposerBlockEntity(blockPos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (tickerWorld, pos, tickerState, blockEntity) -> ExposerBlockEntity.tick(tickerWorld, pos, tickerState, (ExposerBlockEntity) blockEntity);
    }

    @Override
    public boolean emitsRedstonePower(BlockState blockState) {
        return true;
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return super.getStrongRedstonePower(state, world, pos, direction);
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return POWER_STATES[state.get(LEVEL)];
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(FACING) == direction && state.get(LEVEL) == 0) {
            this.startSignal(world, pos);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }



    private void startSignal(WorldAccess world, BlockPos pos) {
        if (!world.isClient() && !world.getBlockTickScheduler().isTicking(pos, this)) {
            world.createAndScheduleBlockTick(pos, this, 2);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
            int dist = 4;

            List<Entity> list = world.getOtherEntities((Entity) null,
                    new Box(pos.getX() + RANGE, pos.getY() + RANGE, pos.getZ() + RANGE,
                            pos.getX() - RANGE, pos.getY() - RANGE, pos.getZ() - RANGE),
                    (Entity entity) -> entity instanceof LivingEntity
            );

            for (Entity e : list) {
                LivingEntity living = (LivingEntity) e;
                if (living.isUndead()) {
                    isUndeadNearby = true;
                    double distance = MathHelper.sqrt((float) living.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ()));
                    if (distance < RANGE && ((int) Math.ceil(distance / (RANGE / 4))) < dist) {
                        if (distance <= 6) {
                            dist = 1;
                        } else dist = Math.max((int) Math.ceil(distance / (RANGE / 4)), 2);

                        if (dist > 3) {
                            dist = 3;
                        }
                    }
                }
            }

            if (!isUndeadNearby) {
                dist = 4;
            }
            world.setBlockState(pos, state.with(LEVEL, 3 - (dist - 1)));

            this.updateNeighborsInFront(world, pos, state);
        }


    protected void updateNeighborsInFront(World worldIn, BlockPos pos, BlockState state) {
        Direction direction = state.get(FACING);
        BlockPos blockpos = pos.offset(direction.getOpposite());
        worldIn.updateNeighbor(blockpos, this, pos);
        worldIn.updateNeighborsExcept(blockpos, this, direction);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean p_60519_) {
        if (!state.isOf(newState.getBlock())) {
            if (!world.isClient() && state.get(LEVEL) > 0 && world.getBlockTickScheduler().isTicking(pos, this)) {
                this.updateNeighborsInFront(world, pos, state.with(LEVEL, 0));
            }
        }

        super.onStateReplaced(state, world, pos, newState, p_60519_);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection());
    }
}
