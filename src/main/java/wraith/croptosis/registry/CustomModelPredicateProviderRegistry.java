package wraith.croptosis.registry;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import wraith.croptosis.item.WateringCanItem;
import wraith.croptosis.util.CUtils;

public class CustomModelPredicateProviderRegistry {

    public static void register() {
        ModelPredicateProviderRegistry.register(CUtils.ID("water_level"),
            (stack, world, entity, seed) -> entity == null ? 0f : WateringCanItem.isFilled(stack) ? 1F : 0F
        );
    }

}
