package wraith.croptosis.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wraith.croptosis.registry.BlockRegistry;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Mixin(HoeItem.class)
public abstract class HoeItemMixin {

    @Shadow
    public static Consumer<ItemUsageContext> getTillingConsumer(BlockState state) {
        return null;
    }

    @Shadow @Final protected static Map<Block, Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>> TILLED_BLOCKS;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void stat(CallbackInfo ci) {
        TILLED_BLOCKS.put(BlockRegistry.BLOCKS.get("fertilized_dirt"), Pair.of(HoeItem::usagePredicate, getTillingConsumer(BlockRegistry.BLOCKS.get("fertilized_farmland").getDefaultState())));
    }

}
