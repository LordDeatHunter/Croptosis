package wraith.croptosis.registry;

import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import wraith.croptosis.item.WateringCanItem;
import wraith.croptosis.util.CUtils;

public class CustomModelPredicateProviderRegistry {

    public static void register() {
        FabricModelPredicateProviderRegistry.register(CUtils.ID("water_level"),
                (stack, world, entity, seed) -> entity == null ? 0f : WateringCanItem.isFilled(stack) ? 1F : 0F
        );
    }

}
