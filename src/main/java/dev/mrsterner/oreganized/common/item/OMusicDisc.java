package dev.mrsterner.oreganized.common.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Rarity;

public class OMusicDisc extends MusicDiscItem {
    public OMusicDisc(int comparatorOutput, SoundEvent sound, Settings settings) {
        super(comparatorOutput, sound, settings.maxCount(1).group(ItemGroup.MISC).rarity(Rarity.RARE));
    }
}
