package dev.mrsterner.oreganized.common.item;

import dev.mrsterner.oreganized.common.block.SilverBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class SilverMirrorItem extends Item {
    boolean isUndeadNearby = false;
    public SilverMirrorItem(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public void inventoryTick(ItemStack itemStack, World world, Entity entity, int p_41407_, boolean p_41408_) {
        int dist = 8;
        if(entity instanceof PlayerEntity player){
            NbtCompound nbt = new NbtCompound();
            BlockPos pos = player.getBlockPos();
            List<Entity> list = world.getOtherEntities(player,
                    new Box(pos.getX() + SilverBlock.RANGE, pos.getY() + SilverBlock.RANGE, pos.getZ() + SilverBlock.RANGE,
                            pos.getX() - SilverBlock.RANGE, pos.getY() - SilverBlock.RANGE, pos.getZ() - SilverBlock.RANGE), (Entity entity2) -> entity2 instanceof LivingEntity
            );

            isUndeadNearby = false;
            for (Entity e : list) {
                LivingEntity living = (LivingEntity) e;
                if (living.isUndead()) {
                    isUndeadNearby = true;
                    double distance = living.distanceTo(player);
                    if (distance < SilverBlock.RANGE && ((int) Math.ceil(distance / (SilverBlock.RANGE / 8))) < dist) {
                        if (distance < 6) {
                            dist = 1;
                        } else dist = Math.max( (int) Math.ceil(distance / (SilverBlock.RANGE / 8)), 2);

                        if (dist > 8) {
                            dist = 8;
                        }
                    }
                }
            }

            if (!isUndeadNearby) {
                dist = 8;
            }

            nbt.putInt("Dist", dist);
            itemStack.setNbt(nbt);
        }

    }
}
