package wraith.croptosis.block;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;

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

        BlockState topBlock = world.getBlockState(pos.up());
        int height = 1;
        while (true) {
            BlockState nextTopBlockState = world.getBlockState(pos.up(height));
            Block nextTopBlock = nextTopBlockState.getBlock();
            if (!(nextTopBlock instanceof SugarCaneBlock || nextTopBlock instanceof CactusBlock)) {
                --height;
                break;
            } else {
                topBlock = nextTopBlockState;
            }
            ++height;
        }
        BlockPos topBlockPos = pos.up(height);
        topBlock.getBlock().randomTick(topBlock, world, topBlockPos, random);
    }

}
