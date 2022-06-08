package wraith.croptosis.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wraith.croptosis.registry.BlockRegistry;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin {

    private boolean repeat = true;

    @Shadow
    public abstract void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random);

    @Inject(method = "randomTick", at = @At("HEAD"))
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (world.getBlockState(pos.down()).getBlock() == BlockRegistry.get("fertilized_farmland") && repeat) {
            repeat = false;
            randomTick(state, world, pos, random);
        }
    }

    @Inject(method = "randomTick", at = @At("TAIL"))
    public void cancelRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        repeat = true;
    }

}
