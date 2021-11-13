package wraith.croptosis.block;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import wraith.croptosis.registry.BlockRegistry;

import java.util.Random;

public class FertilizedDirtBlock extends Block {

    public static final int MAX_TOTAL_HEIGHT = 10;
    public static IntProperty MAX_HEIGHT = IntProperty.of("max_height", 1, MAX_TOTAL_HEIGHT);

    public FertilizedDirtBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(MAX_HEIGHT, 3));
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(MAX_HEIGHT);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);

        for (int height = 1; height <= state.get(FertilizedDirtBlock.MAX_HEIGHT); height++) {
            Block topBlock = world.getBlockState(pos.up(height)).getBlock();

            if (topBlock == BlockRegistry.get("fertilized_farmland")) continue;
            if (topBlock == BlockRegistry.get("fertilized_dirt")) continue;
            if (topBlock == BlockRegistry.get("fertilized_sand")) continue;

            if (topBlock instanceof SugarCaneBlock || topBlock instanceof CactusBlock) {
                while (true) {
                    Block nextTopBlock = world.getBlockState(pos.up(height + 1)).getBlock();
                    if (!(nextTopBlock instanceof SugarCaneBlock || nextTopBlock instanceof CactusBlock)) break;
                    height++;
                }
            }

            BlockState tickBlockState = world.getBlockState(pos.up(height));
            Block tickBlock = tickBlockState.getBlock();
            tickBlock.randomTick(tickBlockState, world, pos.up(height), random);
            break;
        }
    }

}
