package wraith.croptosis.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.CactusBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wraith.croptosis.block.FertilizedSandBlock;

import java.util.Random;

@Mixin(CactusBlock.class)
public class CactusBlockMixin {

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (world.isAir(pos.up())) {
            int height = 1;
            while(world.getBlockState(pos.down(height)).isOf(((CactusBlock)(Object)this))) {
                ++height;
            }
            BlockState lowerBlock = world.getBlockState(pos.down(height));
            if (!(lowerBlock.getBlock() instanceof FertilizedSandBlock)) {
                return;
            }
            if (height < lowerBlock.get(FertilizedSandBlock.MAX_HEIGHT)) {
                int j = state.get(CactusBlock.AGE);
                if (j == 15) {
                    world.setBlockState(pos.up(), ((CactusBlock)(Object)this).getDefaultState());
                    BlockState blockState = state.with(CactusBlock.AGE, 0);
                    world.setBlockState(pos, blockState.with(CactusBlock.AGE, 0), 4);
                    blockState.neighborUpdate(world, pos.up(), ((CactusBlock)(Object)this), pos, false);
                } else {
                    world.setBlockState(pos, state.with(CactusBlock.AGE, j + 1), 4);
                }
            }
            ci.cancel();
        }

    }

}
