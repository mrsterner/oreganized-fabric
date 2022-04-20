package dev.mrsterner.oreganized.common.block;

import dev.mrsterner.oreganized.common.registry.ODamageSource;
import dev.mrsterner.oreganized.common.registry.OObjects;
import dev.mrsterner.oreganized.common.registry.OParticleTypes;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Optional;
import java.util.Random;

public class MoltenLeadBlock extends Block implements FluidDrainable {
    private static final BooleanProperty MOVING = BooleanProperty.of("ismoving");
    public MoltenLeadBlock() {
        super(AbstractBlock.Settings.of((new Material.Builder(MapColor.PURPLE )).build()).strength( -1.0F, 3600000.0F).requiresTool().dropsNothing());
        setDefaultState(getStateManager().getDefaultState().with(MOVING, false));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if(world.isWater(neighborPos)){
            world.syncWorldEvent( 1501 , pos , 0);
            return OObjects.LEAD_BLOCK.getDefaultState();
        }
        if(direction == Direction.DOWN){
            world.createAndScheduleBlockTick(pos , this , 30);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if(world.getFluidState(fromPos).isIn( FluidTags.WATER )){
            world.setBlockState(pos ,OObjects.LEAD_BLOCK.getDefaultState());
            world.syncWorldEvent(1501 , pos , 0 );
        }
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 8;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(((EntityShapeContext) context).getEntity() != null){
            if(!(((EntityShapeContext) context).getEntity() instanceof LivingEntity)) return VoxelShapes.empty();
            LivingEntity entity = (LivingEntity) ((EntityShapeContext) context).getEntity();
            for(ItemStack item : entity.getArmorItems()){
                if(item.getItem().equals( Items.IRON_BOOTS )) return VoxelShapes.fullCube();
            }
        }
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(((EntityShapeContext) context).getEntity() != null){
            return context.isHolding(Items.BUCKET) || context.isHolding(OObjects.MOLTEN_LEAD_BUCKET) ? VoxelShapes.fullCube() : VoxelShapes.empty();
        }
        return VoxelShapes.fullCube();
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if(entity instanceof LivingEntity){
            entity.setOnFireFor(10);
            entity.damage(ODamageSource.MOLTEN_LEAD , 3.0F );
            entity.slowMovement(state , new Vec3d( (double) 0.7F , 1.0D , (double) 0.7F ) );
        }

        super.onEntityCollision(state, world, pos, entity);
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(MOVING);
    }


    @Override
    public ItemStack tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11 );
        if(!world.isClient()){
            world.syncWorldEvent(2001 , pos , Block.getRawIdFromState(state));
        }

        return new ItemStack(OObjects.MOLTEN_LEAD_BUCKET, 1);
    }

    @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(SoundEvents.ITEM_BUCKET_FILL_LAVA);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if(notify){
            if(!oldState.getFluidState().isIn( FluidTags.WATER )){
                scheduleFallingTick( world , pos , 30 );
                world.setBlockState(pos , state.with(MOVING , true ) , 3 );
            }else{
                world.syncWorldEvent( 1501 , pos , 0 );
                world.setBlockState(pos , OObjects.LEAD_BLOCK.getDefaultState() , 3 );
            }
        }else{
            if(!oldState.getFluidState().isIn(FluidTags.WATER )){
                world.setBlockState( pos , state.with( MOVING , false ) , 3 );
                world.createAndScheduleBlockTick( pos , this , 300 );
            }else{
                world.syncWorldEvent( 1501 , pos , 0 );
                world.setBlockState( pos , OObjects.LEAD_BLOCK.getDefaultState() , 3 );
            }
        }
    }

    private void spawnParticle(World pLevel , BlockPos pPos , VoxelShape pShape , double pY){
        this.spawnFluidParticle(pLevel, (double) pPos.getX() + pShape.getMin(Direction.Axis.X ), (double) pPos.getX() + pShape.getMax(Direction.Axis.X), (double) pPos.getZ() + pShape.getMin( Direction.Axis.Z ), (double) pPos.getZ() + pShape.getMax( Direction.Axis.Z ) , pY );
    }

    public void animateTick( BlockState pState , World world , BlockPos pPos , Random pRand ){
        if(!pState.get(MOVING)){
            for(int i = 0; i < pRand.nextInt( 7 ) + 1; ++i){
                this.trySpawnDripParticles(world ,pPos ,pState );
            }
        }
    }

    private void trySpawnDripParticles(World pLevel , BlockPos pPos , BlockState pState ){
        if(pState.getFluidState().isEmpty() && !(pLevel.random.nextFloat() < 0.5F)){
            VoxelShape voxelshape = VoxelShapes.fullCube();//OObjects.LEAD_BLOCK.getDefaultState().getCollisionShape(pLevel , pPos);
            double d0 = voxelshape.getMax( Direction.Axis.Y );
            if(d0 >= 1.0D){
                double d1 = voxelshape.getMin( Direction.Axis.Y );
                if(d1 > 0.0D){
                    this.spawnParticle( pLevel , pPos , voxelshape , (double) pPos.getY() + d1 - 0.05D );
                }else{
                    BlockPos blockpos = pPos.down();
                    BlockState blockstate = pLevel.getBlockState( blockpos );
                    VoxelShape voxelshape1 = blockstate.getCollisionShape( pLevel , blockpos );
                    double d2 = voxelshape1.getMax( Direction.Axis.Y );
                    if((d2 < 1.0D || !blockstate.isFullCube(pLevel , blockpos )) && blockstate.getFluidState().isEmpty()){
                        this.spawnParticle( pLevel , pPos , voxelshape , (double) pPos.getY() - 0.05D );
                    }
                }
            }

        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBlockState(pos.down()).getBlock() == Blocks.AIR || world.getBlockState(pos.down()).isIn(BlockTags.REPLACEABLE_PLANTS)
                || world.getBlockState(pos.down()).getFluidState().isIn(FluidTags.WATER)
                || world.getBlockState(pos.down()).isIn(BlockTags.SMALL_FLOWERS)
                || world.getBlockState(pos.down()).isIn(BlockTags.TALL_FLOWERS)){
            world.setBlockState( pos , Blocks.AIR.getDefaultState() , 67 );
            world.setBlockState( pos.down() , OObjects.MOLTEN_LEAD_BLOCK.getDefaultState() , 67 );
        }
        super.scheduledTick(state, world, pos, random);
    }


    private void spawnFluidParticle(World pParticleData , double pX1 , double pX2 , double pZ1 , double pZ2 , double pY ){
        pParticleData.addParticle((ParticleEffect) OParticleTypes.DRIPPING_LEAD , MathHelper.lerp( pParticleData.random.nextDouble() , pX1 , pX2 ) , pY , MathHelper.lerp( pParticleData.random.nextDouble() , pZ1 , pZ2 ) , 0.0D , 0.0D , 0.0D );
    }
    private boolean scheduleFallingTick( World pLevel , BlockPos pPos , int pDelay ){
        if(pLevel.getBlockState( pPos.down() ).getBlock() == Blocks.AIR || pLevel.getBlockState(pPos.down()).isIn(BlockTags.REPLACEABLE_PLANTS)
                || pLevel.getBlockState( pPos.down() ).getFluidState().isIn(FluidTags.WATER)
                || pLevel.getBlockState( pPos.down() ).isIn(BlockTags.SMALL_FLOWERS)
                || pLevel.getBlockState(pPos.down()).isIn(BlockTags.TALL_FLOWERS)){
            pLevel.createAndScheduleBlockTick(pPos , this , pDelay );
            return true;
        }
        return false;
    }
}
