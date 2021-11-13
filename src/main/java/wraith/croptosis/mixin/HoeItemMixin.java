package wraith.croptosis.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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

    @Shadow @Final protected static Map<Block, Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>> TILLING_ACTIONS;

    @Shadow
    public static Consumer<ItemUsageContext> createTillAction(BlockState result) {
        return null;
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void stat(CallbackInfo ci) {
        TILLING_ACTIONS.put(BlockRegistry.get("fertilized_dirt"), Pair.of(HoeItem::canTillFarmland, createTillAction(BlockRegistry.get("fertilized_farmland").getDefaultState())));
    }

}
