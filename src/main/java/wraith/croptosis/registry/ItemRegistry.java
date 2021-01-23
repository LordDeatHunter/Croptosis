package wraith.croptosis.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import wraith.croptosis.Croptosis;

import java.util.HashMap;
import java.util.Map;

public class ItemRegistry {

    public static final HashMap<String, Item> ITEMS = new HashMap<String, Item>(){{
        put("fertilized_sand", new BlockItem(BlockRegistry.BLOCKS.get("fertilized_sand"), new FabricItemSettings()));
        put("fertilized_farmland", new BlockItem(BlockRegistry.BLOCKS.get("fertilized_farmland"), new FabricItemSettings()));
        put("fertilized_dirt", new BlockItem(BlockRegistry.BLOCKS.get("fertilized_dirt"), new FabricItemSettings()));
        put("feather_meal", new BoneMealItem(new FabricItemSettings()));
        put("rotten_pile", new BoneMealItem(new FabricItemSettings()));
        put("apatite", new BoneMealItem(new FabricItemSettings()));
        put("potash", new BoneMealItem(new FabricItemSettings()));
    }};

    public static void register() {
        for (Map.Entry<String, Item> item : ITEMS.entrySet()) {
            Registry.register(Registry.ITEM, Croptosis.ID(item.getKey()), item.getValue());
        }
    }

}
