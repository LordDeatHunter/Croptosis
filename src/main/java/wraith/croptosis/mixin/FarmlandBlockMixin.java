package wraith.croptosis.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wraith.croptosis.registry.BlockRegistry;

@Mixin(FarmlandBlock.class)
public class FarmlandBlockMixin {

    @Inject(method = "setToDirt", at = @At("HEAD"), cancellable = true)
    private static void setToDirt(BlockState state, World world, BlockPos pos, CallbackInfo ci) {
        if (state.getBlock() == BlockRegistry.get("fertilized_farmland")) {
            world.setBlockState(pos, Block.pushEntitiesUpBeforeBlockChange(state, BlockRegistry.get("fertilized_dirt").getDefaultState(), world, pos));
            ci.cancel();
        }
    }

}
