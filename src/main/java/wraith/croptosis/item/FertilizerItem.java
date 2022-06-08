package wraith.croptosis.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import wraith.croptosis.registry.ItemRegistry;
import wraith.croptosis.util.Config;

import java.util.List;

public class FertilizerItem extends BoneMealItem {

    public FertilizerItem(FabricItemSettings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        var fertilizer = Registry.ITEM.getId(stack.getItem()).getPath();
        if (ItemRegistry.FERTILIZER_ITEMS.contains(fertilizer) && !Config.getInstance().isFertilizerItemEnabled(fertilizer)) {
            tooltip.add(Text.translatable("tooltip.croptosis.fertilizer_item.disabled"));
        } else {
            tooltip.add(Text.translatable("tooltip.croptosis.fertilizer_item"));
        }
    }

}
