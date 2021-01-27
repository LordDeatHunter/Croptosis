package wraith.croptosis.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import wraith.croptosis.CustomItemGroups;
import wraith.croptosis.Utils;
import wraith.croptosis.item.WateringCanItem;

import java.util.HashMap;
import java.util.Map;

public class ItemRegistry {

    public static final HashMap<String, Item> ITEMS = new HashMap<String, Item>(){{
        put("fertilized_sand", new BlockItem(BlockRegistry.BLOCKS.get("fertilized_sand"), new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));
        put("fertilized_farmland", new BlockItem(BlockRegistry.BLOCKS.get("fertilized_farmland"), new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));
        put("fertilized_dirt", new BlockItem(BlockRegistry.BLOCKS.get("fertilized_dirt"), new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));

        put("potash_ore", new BlockItem(BlockRegistry.BLOCKS.get("potash_ore"), new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));
        put("apatite_ore", new BlockItem(BlockRegistry.BLOCKS.get("apatite_ore"), new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));

        put("potash_block", new BlockItem(BlockRegistry.BLOCKS.get("potash_block"), new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));
        put("apatite_block", new BlockItem(BlockRegistry.BLOCKS.get("apatite_block"), new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));

        put("feather_meal", new BoneMealItem(new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));
        put("rotten_pile", new BoneMealItem(new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));
        put("apatite", new BoneMealItem(new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));
        put("potash", new BoneMealItem(new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));

        put("watering_can", new WateringCanItem(new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));
    }};

    public static void register() {
        for (Map.Entry<String, Item> item : ITEMS.entrySet()) {
            Registry.register(Registry.ITEM, Utils.ID(item.getKey()), item.getValue());
        }
    }

}
