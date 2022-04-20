package dev.mrsterner.oreganized.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class SilverIngot extends Item {
    public SilverIngot(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        if(entity instanceof PlayerEntity player){
            NbtCompound nbt = new NbtCompound();
            if (stack.getOrCreateNbt().getBoolean("Shine")) {
                long tick = stack.getOrCreateNbt().getLong("t");

                if (entity.world.getTime() >= tick + 40) {
                    nbt.putLong("t", 0);
                    nbt.putBoolean("Shine", false);
                    stack.setNbt(nbt);
                }
            }
        }
    }
}
