package wraith.croptosis.registry;

import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.util.Arm;
import wraith.croptosis.Utils;
import wraith.croptosis.item.WateringCanItem;

public class CustomModelPredicateProviderRegistry {

    public static void register() {

        FabricModelPredicateProviderRegistry.register(ItemRegistry.ITEMS.get("watering_can"), Utils.ID("water_level"),
            (itemStack, clientWorld, livingEntity) -> {
                if (livingEntity == null || livingEntity.getMainArm() != Arm.RIGHT) {
                    return 0f;
                }
                return !itemStack.isEmpty() && itemStack.getItem() instanceof WateringCanItem && WateringCanItem.isFilled(itemStack) ? 1F : 0F;
            }
        );

    }

}
