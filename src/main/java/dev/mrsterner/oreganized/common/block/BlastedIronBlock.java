package dev.mrsterner.oreganized.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class BlastedIronBlock extends Block {
    public static final BooleanProperty UP = BooleanProperty.of("up");
    public static final BooleanProperty DOWN = BooleanProperty.of("down");
    public static final BooleanProperty NORTH = BooleanProperty.of("north");
    public static final BooleanProperty WEST = BooleanProperty.of("west");
    public static final BooleanProperty SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty EAST = BooleanProperty.of("east");
    public static final BooleanProperty UPDATE = BooleanProperty.of("update");
    public BlastedIronBlock(Settings settings) {
        super(settings.strength(6,3).requiresTool().sounds(BlockSoundGroup.NETHERITE));
        setDefaultState(this.stateManager.getDefaultState()
                .with(UP, false)
                .with(DOWN, false)
                .with(NORTH, false)
                .with(SOUTH, false)
                .with(WEST, false)
                .with(EAST, false));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        if(ctx.getPlayer() != null){
            if(ctx.getPlayer().isSneaking()){
                return this.getDefaultState()
                    .with(UP, canConnect(ctx.getBlockPos(), ctx.getWorld(), Direction.UP))
                    .with(DOWN, canConnect(ctx.getBlockPos(), ctx.getWorld(), Direction.DOWN))
                    .with(WEST, canConnect(ctx.getBlockPos(), ctx.getWorld(), Direction.WEST))
                    .with(SOUTH, canConnect(ctx.getBlockPos(), ctx.getWorld(), Direction.SOUTH))
                    .with(NORTH, canConnect(ctx.getBlockPos(), ctx.getWorld(), Direction.NORTH))
                    .with(EAST, canConnect(ctx.getBlockPos(), ctx.getWorld(), Direction.EAST))
                    .with(UPDATE, true);
            }
        }
        return this.getDefaultState();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST, UPDATE);
        super.appendProperties(builder);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BooleanProperty BLOCKPROPERTY = null;
        switch (direction) {
            case UP:
                BLOCKPROPERTY = UP;
                break;
            case DOWN:
                BLOCKPROPERTY = DOWN;
                break;
            case EAST:
                BLOCKPROPERTY = EAST;
                break;
            case WEST:
                BLOCKPROPERTY = WEST;
                break;
            case NORTH:
                BLOCKPROPERTY = NORTH;
                break;
            case SOUTH:
                BLOCKPROPERTY = SOUTH;
                break;
        }

        if (BLOCKPROPERTY != null) {
            if (canConnect(pos, world, direction))
                state = state.with(BLOCKPROPERTY, true);
            else if (state.get(BLOCKPROPERTY))
                state = state.with(BLOCKPROPERTY, false);
        }
        return state;
    }

    private boolean canConnect(BlockPos pos, World world, Direction dir) {
        if (world.getBlockState(pos.offset(dir)).getBlock() instanceof BlastedIronBlock) {
            return world.getBlockState(pos.offset(dir)).get(UPDATE);
        }
        return false;
    }

    private boolean canConnect(BlockPos pos, WorldAccess world, Direction dir) {
        if (world.getBlockState(pos.offset(dir)).getBlock() instanceof BlastedIronBlock) {
            return world.getBlockState(pos.offset(dir)).get(UPDATE) && world.getBlockState(pos).get(UPDATE);
        }
        return false;
    }
}
