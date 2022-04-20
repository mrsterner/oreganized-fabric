package dev.mrsterner.oreganized.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;

public class EngravedBlock extends Block {
    public static final BooleanProperty ISZAXISUP = BooleanProperty.of( "iszaxisup" );
    public static final BooleanProperty ISZAXISDOWN = BooleanProperty.of( "iszaxisdown" );

    public EngravedBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ISZAXISDOWN, ISZAXISUP);
        super.appendProperties(builder);
    }

}
