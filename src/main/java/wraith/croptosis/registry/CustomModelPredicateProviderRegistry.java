package wraith.croptosis.registry;

import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.util.Arm;
import wraith.croptosis.Utils;
import wraith.croptosis.item.WateringCanItem;

public class CustomModelPredicateProviderRegistry {

    public static void register() {

        FabricModelPredicateProviderRegistry.register(Utils.ID("water_level"),
                (stack, world, entity, seed) -> {
                    if (entity == null || entity.getMainArm() != Arm.RIGHT) {
                        return 0f;
                    }
                    return !stack.isEmpty() && stack.getItem() instanceof WateringCanItem && WateringCanItem.isFilled(stack) ? 1F : 0F;
                }
        );

    }

}
