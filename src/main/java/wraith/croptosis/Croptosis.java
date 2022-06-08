package wraith.croptosis;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wraith.croptosis.registry.BlockRegistry;
import wraith.croptosis.registry.ItemRegistry;
import wraith.croptosis.registry.OreRegistry;
import wraith.croptosis.util.Config;

public class Croptosis implements ModInitializer {

    public static final String MOD_ID = "croptosis";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        Config.getInstance();
        BlockRegistry.init();
        ItemRegistry.init();
        OreRegistry.init();
        LOGGER.info("has been initiated.");
    }

}
