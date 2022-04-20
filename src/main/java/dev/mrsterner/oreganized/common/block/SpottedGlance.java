package dev.mrsterner.oreganized.common.block;

import dev.mrsterner.oreganized.common.registry.OObjects;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class SpottedGlance extends Block {
    public SpottedGlance(FabricBlockSettings fabricBlockSettings) {
        super(fabricBlockSettings);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighbour, WorldAccess world, BlockPos pos, BlockPos neighbourPos) {
        if (world.isWater(neighbourPos)) {
            ItemStack stack = new ItemStack(OObjects.LEAD_NUGGET, world.getRandom().nextInt(2) + 1);
            ItemScatterer.spawn((World) world, pos.getX(), pos.getY(), pos.getZ(), stack);
            return OObjects.GLANCE.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighbour, world, pos, neighbourPos);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isWater(fromPos)) {
            world.setBlockState(pos, OObjects.GLANCE.getDefaultState());
            ItemStack stack = new ItemStack(OObjects.LEAD_NUGGET, world.getRandom().nextInt(2) + 1);
            ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), stack);
        }
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }


}
