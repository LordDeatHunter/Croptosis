package wraith.croptosis.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;
import wraith.croptosis.util.CUtils;
import wraith.croptosis.item.WateringCanItem;
import wraith.croptosis.util.Config;

import java.util.HashMap;

public final class ItemRegistry {

    private ItemRegistry() {}

    private static final HashMap<String, Item> ITEMS = new HashMap<>();
    public static final ItemGroup CROPTOSIS = FabricItemGroupBuilder.create(CUtils.ID("croptosis")).icon(() -> new ItemStack(ItemRegistry.get("apatite"))).build();

    private static void registerItem(String id, Item item){
        ITEMS.put(id, Registry.register(Registry.ITEM, CUtils.ID(id), item));
    }

    public static void init() {
        if (!ITEMS.isEmpty()) {
            return;
        }
        registerItem("fertilized_sand", new BlockItem(BlockRegistry.get("fertilized_sand"), new FabricItemSettings().group(CROPTOSIS)));
        registerItem("fertilized_farmland", new BlockItem(BlockRegistry.get("fertilized_farmland"), new FabricItemSettings().group(CROPTOSIS)));
        registerItem("fertilized_dirt", new BlockItem(BlockRegistry.get("fertilized_dirt"), new FabricItemSettings().group(CROPTOSIS)));

        registerItem("potash_ore", new BlockItem(BlockRegistry.get("potash_ore"), new FabricItemSettings().group(CROPTOSIS)));
        registerItem("apatite_ore", new BlockItem(BlockRegistry.get("apatite_ore"), new FabricItemSettings().group(CROPTOSIS)));
        registerItem("deepslate_apatite_ore", new BlockItem(BlockRegistry.get("deepslate_apatite_ore"), new FabricItemSettings().group(CROPTOSIS)));

        registerItem("potash_block", new BlockItem(BlockRegistry.get("potash_block"), new FabricItemSettings().group(CROPTOSIS)));
        registerItem("apatite_block", new BlockItem(BlockRegistry.get("apatite_block"), new FabricItemSettings().group(CROPTOSIS)));

        registerItem("feather_meal", new BoneMealItem(new FabricItemSettings().group(CROPTOSIS)));
        registerItem("rotten_pile", new BoneMealItem(new FabricItemSettings().group(CROPTOSIS)));
        registerItem("apatite", new BoneMealItem(new FabricItemSettings().group(CROPTOSIS)));
        registerItem("potash", new BoneMealItem(new FabricItemSettings().group(CROPTOSIS)));
        registerItem("potash_pieces", new Item(new FabricItemSettings().group(CROPTOSIS)));

        var config = Config.getInstance();
        if (config.createWateringCans()) {
            registerItem("iron_watering_can", new WateringCanItem(config.getWateringCanRange("iron"), config.getWateringCanCapacity("iron"), config.getWateringCanChance("iron"), new FabricItemSettings().group(CROPTOSIS)));
            registerItem("gold_watering_can", new WateringCanItem(config.getWateringCanRange("gold"), config.getWateringCanCapacity("gold"), config.getWateringCanChance("gold"), new FabricItemSettings().group(CROPTOSIS)));
            registerItem("diamond_watering_can", new WateringCanItem(config.getWateringCanRange("diamond"), config.getWateringCanCapacity("diamond"), config.getWateringCanChance("diamond"), new FabricItemSettings().group(CROPTOSIS)));
            registerItem("netherite_watering_can", new WateringCanItem(config.getWateringCanRange("netherite"), config.getWateringCanCapacity("netherite"), config.getWateringCanChance("netherite"), new FabricItemSettings().group(CROPTOSIS).fireproof()));
        }
        registerItem("creative_watering_can", new WateringCanItem(10, -1, 1.0D, new FabricItemSettings().group(CROPTOSIS).fireproof()));
    }
    
    public static Item get(String id) {
        return ITEMS.getOrDefault(id, Items.AIR);
    }

}
