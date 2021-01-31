package wraith.croptosis;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wraith.croptosis.registry.BlockRegistry;
import wraith.croptosis.registry.ItemRegistry;
import wraith.croptosis.registry.OreRegistry;

public class Croptosis implements ModInitializer {

    public static final String MOD_ID = "croptosis";
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        BlockRegistry.register();
        ItemRegistry.register();
        OreRegistry.register();
        LOGGER.info("[Croptosis] has been initiated.");
    }

}
