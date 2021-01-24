package wraith.croptosis.worldgen;

import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import wraith.croptosis.registry.BlockRegistry;

import java.util.HashMap;

public class CustomConfiguredFeatures {

    public static final HashMap<String, ConfiguredFeature<?, ?>> ORES = new HashMap<String, ConfiguredFeature<?, ?>>(){{
        put("potash_ore", Feature.ORE
                .configure(new OreFeatureConfig(
                        OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                        BlockRegistry.BLOCKS.get("potash_ore").getDefaultState(),
                        12))
                .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(
                        0,
                        32,
                        128)))
                .spreadHorizontally()
                .repeat(16));

        put("apatite_ore", Feature.ORE
                .configure(new OreFeatureConfig(
                        OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                        BlockRegistry.BLOCKS.get("apatite_ore").getDefaultState(),
                        8))
                .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(
                        0,
                        32,
                        96)))
                .spreadHorizontally()
                .repeat(12));
    }};

}