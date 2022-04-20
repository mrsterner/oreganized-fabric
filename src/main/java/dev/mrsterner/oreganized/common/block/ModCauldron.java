package dev.mrsterner.oreganized.common.block;

import dev.mrsterner.oreganized.common.registry.OObjects;
import dev.mrsterner.oreganized.common.registry.OTags;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;


import java.util.Random;


public class ModCauldron extends Block {
    public static final IntProperty LEVEL = Properties.AGE_3;
    private static final VoxelShape INSIDE = createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    protected static final VoxelShape SHAPE = VoxelShapes.union(VoxelShapes.fullCube(),
            VoxelShapes.union(createCuboidShape(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D),
                    createCuboidShape(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D),
                    createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D),
                    INSIDE));
    public ModCauldron(FabricBlockSettings fabricBlockSettings) {
        super(fabricBlockSettings.requiresTool().strength(2.0F).nonOpaque());
        setDefaultState(this.getDefaultState().with(LEVEL, 1));
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(Blocks.CAULDRON);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }


    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return INSIDE;
    }

    @Override
    public ActionResult onUse(BlockState blockState, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult p_60508_) {
        ItemStack itemstack = player.getStackInHand(hand);
        if (itemstack.isEmpty()) {
            if (blockState.get(LEVEL) == 1) {
                world.removeBlock(pos, false);
                world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                //TODO world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY() + 0.5D, pos.getZ(), OObjects.LEAD_BLOCK.asItem().getDefaultStack()));
                return ActionResult.success(world.isClient());
            } else return ActionResult.PASS;
        } else {
            int i = blockState.get(LEVEL);
            Item item = itemstack.getItem();
            if (item == Items.BUCKET) {
                if (i == 3 && !world.isClient()) {
                    if (!player.getAbilities().creativeMode) {
                        itemstack.decrement(1);
                        if (itemstack.isEmpty()) {
                            player.setStackInHand(hand, new ItemStack(OObjects.MOLTEN_LEAD_BUCKET));
                        } else if (!player.getInventory().insertStack(new ItemStack(OObjects.MOLTEN_LEAD_BUCKET))) {
                            player.dropItem(new ItemStack(OObjects.MOLTEN_LEAD_BUCKET), false);
                        }
                    }
                    player.incrementStat(Stats.USE_CAULDRON);
                    world.removeBlock(pos, false);
                    world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                    world.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_FILL_LAVA, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                return ActionResult.success(world.isClient());

            } else return ActionResult.PASS;
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        this.tick(state, worldIn, pos, random);
    }


    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (state.get(LEVEL) > 0 && state.get(LEVEL) != 3) {
            if (!worldIn.isClient()) {
                BlockPos newPos = new BlockPos(pos.getX(), pos.getY() - 1.0D, pos.getZ());
                BlockState block = worldIn.getBlockState(newPos);
                if (block.isIn(OTags.FIRE_SOURCE_BLOCKTAG)) {
                    this.setLeadLevel(worldIn, pos, state, state.get(LEVEL) + 1);
                } else {
                    this.setLeadLevel(worldIn, pos, state, 1);
                }
            }
        }
    }

    public void setLeadLevel(World worldIn, BlockPos pos, BlockState state, int level) {
        worldIn.setBlockState(pos, state.with(LEVEL, MathHelper.clamp(level, 0, 3)), 2);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }
}
