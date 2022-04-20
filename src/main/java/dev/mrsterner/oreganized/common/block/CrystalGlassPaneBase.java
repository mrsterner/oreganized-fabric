package dev.mrsterner.oreganized.common.block;

import net.minecraft.block.PaneBlock;
import net.minecraft.sound.BlockSoundGroup;

public class CrystalGlassPaneBase extends PaneBlock {
    public CrystalGlassPaneBase(Settings settings) {
        super(settings.strength(0.3F).sounds(BlockSoundGroup.GLASS).nonOpaque());
    }
}
