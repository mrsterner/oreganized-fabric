package dev.mrsterner.oreganized.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class SilverBlock extends Block {
    public static final IntProperty LEVEL = Properties.AGE_7;
    public static final float RANGE = 24.0f;
    boolean isUndeadNearby = false;
    public SilverBlock(Settings settings) {
        super(settings.strength(5.0f, 6.0f).requiresTool().sounds(BlockSoundGroup.METAL));
        this.setDefaultState(this.getDefaultState().with(LEVEL, 7));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }


    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving){
        world.createAndScheduleBlockTick(pos, state.getBlock(), 1);
        super.onBlockAdded(state , world , pos , oldState , isMoving);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int dist = 8;

        List<Entity> list = world.getOtherEntities((Entity) null,
                new Box(pos.getX() + RANGE, pos.getY() + RANGE, pos.getZ() + RANGE,
                        pos.getX() - RANGE, pos.getY() - RANGE, pos.getZ() - RANGE), (Entity entity) -> entity instanceof LivingEntity);

        for (Entity e : list) {
            LivingEntity living = (LivingEntity) e;
            if (living.isUndead()) {
                isUndeadNearby = true;
                double distance = Math.sqrt(living.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ()));
                if (((int) Math.ceil(distance / (RANGE / 8))) < dist) {
                    dist = (int) Math.ceil(distance / (RANGE / 8));

                    /*if (dist > 7) {
                        dist = 7;
                    }*/
                }
            }
        }

        if (!isUndeadNearby) {
            dist = 8;
        }
        world.setBlockState(pos, state.with(LEVEL, dist - 1));
        world.createAndScheduleBlockTick(pos, state.getBlock(), 1);
    }


}
