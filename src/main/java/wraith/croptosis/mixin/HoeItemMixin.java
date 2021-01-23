package wraith.croptosis.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import wraith.croptosis.block.FertilizedDirtBlock;
import wraith.croptosis.registry.BlockRegistry;

@Mixin(HoeItem.class)
public class HoeItemMixin {

    @ModifyVariable(method = "useOnBlock", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;", shift = At.Shift.AFTER))
    public BlockState useOnBlock(BlockState state, ItemUsageContext context) {
        BlockState newState = context.getWorld().getBlockState(context.getBlockPos());
        if (newState.getBlock() instanceof FertilizedDirtBlock) {
            return BlockRegistry.BLOCKS.get("fertilized_dirt").getDefaultState();
        }
        return state;
    }

}
