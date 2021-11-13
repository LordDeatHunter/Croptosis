package wraith.croptosis.mixin;

import net.minecraft.block.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wraith.croptosis.block.FertilizedDirtBlock;
import wraith.croptosis.block.FertilizedSandBlock;
import wraith.croptosis.registry.BlockRegistry;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockMixin {

    @Shadow public abstract Block getBlock();

    @Inject(method = "isOf", at = @At("HEAD"), cancellable = true)
    public void isOf(Block block, CallbackInfoReturnable<Boolean> cir) {
        boolean isSand = (block instanceof SandBlock && getBlock() instanceof FertilizedSandBlock) ||
                         (block instanceof FertilizedSandBlock && getBlock() instanceof SandBlock);
        boolean isFarmland = (block instanceof FarmlandBlock && getBlock() == BlockRegistry.get("fertilized_farmland")) ||
                             (block == BlockRegistry.get("fertilized_farmland") && getBlock() instanceof FarmlandBlock);
        boolean isDirt = (block == Blocks.DIRT && getBlock() instanceof FertilizedDirtBlock) ||
                         (getBlock() == Blocks.DIRT && block instanceof FertilizedDirtBlock);

        if (isSand || isFarmland || isDirt) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

}