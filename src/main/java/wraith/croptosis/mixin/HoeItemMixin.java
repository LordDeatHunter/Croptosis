package wraith.croptosis.mixin;

import net.minecraft.item.HoeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wraith.croptosis.registry.BlockRegistry;

@Mixin(HoeItem.class)
public class HoeItemMixin {

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void stat(CallbackInfo ci) {
        HoeItemAccessor.getTiledBlocks().put(BlockRegistry.BLOCKS.get("fertilized_dirt"), BlockRegistry.BLOCKS.get("fertilized_farmland").getDefaultState());
    }

}
