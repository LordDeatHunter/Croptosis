package wraith.croptosis.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import wraith.croptosis.CustomItemGroups;
import wraith.croptosis.Utils;
import wraith.croptosis.item.WateringCanItem;

import java.util.HashMap;

public final class ItemRegistry {

    private ItemRegistry() {}

    private static final HashMap<String, Item> ITEMS = new HashMap<>();

    private static void registerItem(String id, Item item){
        ITEMS.put(id, Registry.register(Registry.ITEM, Utils.ID(id), item));
    }

    public static void init() {
        if (!ITEMS.isEmpty()) {
            return;
        }
        registerItem("fertilized_sand", new BlockItem(BlockRegistry.get("fertilized_sand"), new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));
        registerItem("fertilized_farmland", new BlockItem(BlockRegistry.get("fertilized_farmland"), new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));
        registerItem("fertilized_dirt", new BlockItem(BlockRegistry.get("fertilized_dirt"), new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));

        registerItem("potash_ore", new BlockItem(BlockRegistry.get("potash_ore"), new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));
        registerItem("apatite_ore", new BlockItem(BlockRegistry.get("apatite_ore"), new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));

        registerItem("potash_block", new BlockItem(BlockRegistry.get("potash_block"), new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));
        registerItem("apatite_block", new BlockItem(BlockRegistry.get("apatite_block"), new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));

        registerItem("feather_meal", new BoneMealItem(new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));
        registerItem("rotten_pile", new BoneMealItem(new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));
        registerItem("apatite", new BoneMealItem(new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));
        registerItem("potash", new BoneMealItem(new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));

        registerItem("iron_watering_can", new WateringCanItem(2, 12, 0.02D, new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));
        registerItem("gold_watering_can", new WateringCanItem(5, 6, 0.025D, new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));
        registerItem("diamond_watering_can", new WateringCanItem(3, 16, 0.025D, new FabricItemSettings().group(CustomItemGroups.CROPTOSIS)));
        registerItem("netherite_watering_can", new WateringCanItem(4, 20, 0.03D, new FabricItemSettings().group(CustomItemGroups.CROPTOSIS).fireproof()));
        registerItem("creative_watering_can", new WateringCanItem(10, 1, 1.0D, new FabricItemSettings().group(CustomItemGroups.CROPTOSIS).fireproof()));
    }
    
    public static Item get(String id) {
        return ITEMS.getOrDefault(id, Items.AIR);
    }

}
