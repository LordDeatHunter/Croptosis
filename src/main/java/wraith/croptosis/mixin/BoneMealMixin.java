package wraith.croptosis.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wraith.croptosis.block.FertilizedDirtBlock;
import wraith.croptosis.block.FertilizedSandBlock;
import wraith.croptosis.registry.BlockRegistry;

@Mixin(BoneMealItem.class)
public class BoneMealMixin {

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        if (!world.isClient) {
            boolean success = false;
            int decrementAmount = 1;
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() == Blocks.SAND) {
                world.setBlockState(pos, BlockRegistry.BLOCKS.get("fertilized_sand").getDefaultState());
                success = true;
            } else if (state.getBlock() == Blocks.DIRT) {
                world.setBlockState(pos, Blocks.GRASS_BLOCK.getDefaultState());
                success = true;
            } else if (state.getBlock() == Blocks.FARMLAND) {
                world.setBlockState(pos, BlockRegistry.BLOCKS.get("fertilized_farmland").getDefaultState());
                success = true;
            } else if ((state.getBlock() instanceof FertilizedSandBlock && state.get(FertilizedSandBlock.MAX_HEIGHT) < FertilizedSandBlock.MAX_TOTAL_HEIGHT && context.getStack().getCount() > state.get(FertilizedSandBlock.MAX_HEIGHT))) {
                decrementAmount += state.get(FertilizedSandBlock.MAX_HEIGHT) - 2;
                world.setBlockState(context.getBlockPos(), state.with(FertilizedSandBlock.MAX_HEIGHT, state.get(FertilizedSandBlock.MAX_HEIGHT) + 1));
                success = true;
            } else if ((state.getBlock() instanceof FertilizedDirtBlock && state.get(FertilizedDirtBlock.MAX_HEIGHT) < FertilizedDirtBlock.MAX_TOTAL_HEIGHT && context.getStack().getCount() > state.get(FertilizedDirtBlock.MAX_HEIGHT))) {
                decrementAmount += state.get(FertilizedDirtBlock.MAX_HEIGHT) - 2;
                world.setBlockState(context.getBlockPos(), state.with(FertilizedDirtBlock.MAX_HEIGHT, state.get(FertilizedDirtBlock.MAX_HEIGHT) + 1));
                success = true;
            }
            if (success) {
                context.getStack().decrement(decrementAmount);
                world.syncWorldEvent(2005, pos, 0);
                cir.setReturnValue(ActionResult.SUCCESS);
                cir.cancel();
            }
        }
    }
}