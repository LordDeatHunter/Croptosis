package wraith.croptosis;

import net.fabricmc.api.ClientModInitializer;
import wraith.croptosis.registry.CustomModelPredicateProviderRegistry;

public class CroptosisClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CustomModelPredicateProviderRegistry.register();
    }

}
