package dev.mrsterner.oreganized.common.block;

import dev.mrsterner.oreganized.common.entities.PrimedShrapnelBomb;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class ShrapnelBombBlock extends TntBlock {
    public ShrapnelBombBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.isOf(state.getBlock())) {
            if (world.isReceivingRedstonePower(pos)) {
                explode(world, pos, null);
                world.removeBlock(pos, false);
            }
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isReceivingRedstonePower(pos)) {
            explode(world, pos, null);
            world.removeBlock(pos, false);
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient() && !player.isCreative() && state.get(UNSTABLE)) {
            explode(world, pos, player);
        }
        super.onBreak(world, pos, state, null);
    }



    private static void explode(World world, BlockPos pPos, @Nullable LivingEntity pEntity) {
        if (!world.isClient()) {
            PrimedShrapnelBomb primedbomb = new PrimedShrapnelBomb(world, (double)pPos.getX() + 0.5D, (double)pPos.getY(), (double)pPos.getZ() + 0.5D, pEntity);
            world.spawnEntity(primedbomb);
            world.playSound((PlayerEntity) null, primedbomb.getX(), primedbomb.getY(), primedbomb.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(pEntity, GameEvent.PRIME_FUSE, pPos);
        }
    }

    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        if (!world.isClient()) {
            PrimedShrapnelBomb primedBomb = new PrimedShrapnelBomb(world, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, explosion.getCausingEntity());
            int i = primedBomb.getFuse();
            primedBomb.setFuse((short)(world.random.nextInt(i / 4) + i / 8));
            world.spawnEntity(primedBomb);
        }
    }
}
