package wraith.croptosis.block;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import wraith.croptosis.registry.BlockRegistry;

import java.util.Random;

public class FertilizedSandBlock extends SandBlock {

    public static final int MAX_TOTAL_HEIGHT = 10;
    public static IntProperty MAX_HEIGHT = IntProperty.of("max_height", 1, MAX_TOTAL_HEIGHT);

    public FertilizedSandBlock(int color, Settings settings) {
        super(color, settings);
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
            BlockState topBlockState = world.getBlockState(pos.up(height));
            Block topBlock = topBlockState.getBlock();
            if (!(topBlock == BlockRegistry.BLOCKS.get("fertilized_dirt") || topBlock == BlockRegistry.BLOCKS.get("fertilized_sand"))) {
                topBlock.randomTick(topBlockState, world, pos.up(height), random);
                return;
            }
        }
    }

}
