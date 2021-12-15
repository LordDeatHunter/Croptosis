package wraith.croptosis.mixin;

import net.minecraft.block.BambooBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import wraith.croptosis.util.CUtils;

import java.util.Random;

@Mixin(BambooBlock.class)
public class BambooBlockMixin {

    @ModifyConstant(method = "randomTick", constant = @Constant(intValue = 3))
    public int setMaxHeight(int maxHeight, BlockState state, ServerWorld world, BlockPos pos, Random random) {
        return CUtils.getCropMaxHeight(maxHeight, state, world, pos, random, _this());
    }

    private Block _this() {
        return ((BambooBlock)(Object)this);
    }

}
