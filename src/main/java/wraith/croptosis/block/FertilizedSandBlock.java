package wraith.croptosis.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SandBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import wraith.croptosis.util.CUtils;

@SuppressWarnings("deprecation")
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
        CUtils.randomFertilizedBlockTick(state, world, pos, random);
    }

}
