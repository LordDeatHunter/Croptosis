package wraith.croptosis.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;
import wraith.croptosis.Utils;
import wraith.croptosis.block.CroptosisFarmlandBlock;
import wraith.croptosis.block.FertilizedDirtBlock;
import wraith.croptosis.block.FertilizedSandBlock;

import java.util.HashMap;

public class BlockRegistry {

    private BlockRegistry() {}

    private static final HashMap<String, Block> BLOCKS = new HashMap<>();

    private static void registerBlock(String id, Block block){
        BLOCKS.put(id, Registry.register(Registry.BLOCK, Utils.ID(id), block));
    }

    public static void init() {
        registerBlock("fertilized_sand", new FertilizedSandBlock(0xc1bba3, FabricBlockSettings.of(Material.AGGREGATE, MapColor.PALE_YELLOW).strength(0.5F).sounds(BlockSoundGroup.SAND)));
        registerBlock("fertilized_farmland", new CroptosisFarmlandBlock(FabricBlockSettings.of(Material.SOIL).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRAVEL)));
        registerBlock("fertilized_dirt", new FertilizedDirtBlock(FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN).strength(0.5F).sounds(BlockSoundGroup.GRAVEL)));

        registerBlock("potash_block", new Block(FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).requiresTool().breakByTool(FabricToolTags.PICKAXES, 1).strength(3.0F, 3.0F).sounds(BlockSoundGroup.METAL)));
        registerBlock("apatite_block", new Block(FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).requiresTool().breakByTool(FabricToolTags.PICKAXES, 2).strength(3.5F, 3.0F).sounds(BlockSoundGroup.METAL)));

        registerBlock("potash_ore", new OreBlock(FabricBlockSettings.of(Material.STONE).requiresTool().breakByTool(FabricToolTags.PICKAXES, 1).strength(3.0F, 3.0F)));
        registerBlock("apatite_ore", new OreBlock(FabricBlockSettings.of(Material.STONE).requiresTool().breakByTool(FabricToolTags.PICKAXES, 2).strength(3.5F, 3.0F)));
    }

    public static Block get(String id) {
        return BLOCKS.getOrDefault(id, Blocks.AIR);
    }

}
