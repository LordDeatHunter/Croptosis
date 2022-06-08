package wraith.croptosis.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wraith.croptosis.block.FertilizedDirtBlock;
import wraith.croptosis.block.FertilizedSandBlock;
import wraith.croptosis.registry.BlockRegistry;
import wraith.croptosis.registry.ItemRegistry;
import wraith.croptosis.util.Config;

@Mixin(BoneMealItem.class)
public class BoneMealMixin {

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        var fertilizer = Registry.ITEM.getId(_this()).getPath();
        if (ItemRegistry.FERTILIZER_ITEMS.contains(fertilizer) && !Config.getInstance().isFertilizerItemEnabled(fertilizer)) {
            return;
        }
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        if (world.isClient || !Config.getInstance().canConvertToFertilizedBlocks()) {
            return;
        }
        int decrementAmount = 1;
        var state = world.getBlockState(pos);
        var block = state.getBlock();
        var player = context.getPlayer();
        var isCreative = player != null && player.isCreative();
        if (block == Blocks.SAND && world.getFluidState(pos.up()).getFluid() != Fluids.WATER) {
            world.setBlockState(pos, BlockRegistry.get("fertilized_sand").getDefaultState());
        } else if (block == Blocks.DIRT && world.getFluidState(pos.up()).getFluid() != Fluids.WATER) {
            world.setBlockState(pos, Blocks.GRASS_BLOCK.getDefaultState());
        } else if (block == Blocks.FARMLAND) {
            world.setBlockState(pos, BlockRegistry.get("fertilized_farmland").getDefaultState());
        } else if (block instanceof FertilizedSandBlock &&
            state.get(FertilizedSandBlock.MAX_HEIGHT) < FertilizedSandBlock.MAX_TOTAL_HEIGHT &&
            (context.getStack().getCount() > state.get(FertilizedSandBlock.MAX_HEIGHT) - 2 || isCreative)) {
            decrementAmount += state.get(FertilizedSandBlock.MAX_HEIGHT) - 2;
            world.setBlockState(context.getBlockPos(), state.with(FertilizedSandBlock.MAX_HEIGHT, state.get(FertilizedSandBlock.MAX_HEIGHT) + 1));
        } else if (block instanceof FertilizedDirtBlock &&
            state.get(FertilizedDirtBlock.MAX_HEIGHT) < FertilizedDirtBlock.MAX_TOTAL_HEIGHT &&
            (context.getStack().getCount() > state.get(FertilizedDirtBlock.MAX_HEIGHT) - 2 || isCreative)) {
            decrementAmount += state.get(FertilizedDirtBlock.MAX_HEIGHT) - 2;
            world.setBlockState(context.getBlockPos(), state.with(FertilizedDirtBlock.MAX_HEIGHT, state.get(FertilizedDirtBlock.MAX_HEIGHT) + 1));
        } else {
            return;
        }
        context.getStack().decrement(decrementAmount);
        world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, pos, 0);
        cir.setReturnValue(ActionResult.SUCCESS);
        cir.cancel();
    }

    @Unique
    private BoneMealItem _this() {
        return (BoneMealItem) (Object) this;
    }

}