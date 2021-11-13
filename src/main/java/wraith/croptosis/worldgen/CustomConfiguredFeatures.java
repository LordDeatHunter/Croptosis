package wraith.croptosis.worldgen;

import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
import wraith.croptosis.registry.BlockRegistry;

import java.util.HashMap;

public class CustomConfiguredFeatures {

    public static final HashMap<String, ConfiguredFeature<?, ?>> ORES = new HashMap<>() {{
        put("potash_ore", Feature.ORE
                .configure(new OreFeatureConfig(
                        OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                        BlockRegistry.get("potash_ore").getDefaultState(),
                        8))
                .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(UniformHeightProvider.create(YOffset.fixed(0), YOffset.fixed(96)))))
                .spreadHorizontally()
                .repeat(6));

        put("apatite_ore", Feature.ORE
                .configure(new OreFeatureConfig(
                        OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                        BlockRegistry.get("apatite_ore").getDefaultState(),
                        6))
                .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(UniformHeightProvider.create(YOffset.fixed(0), YOffset.fixed(64)))))
                .spreadHorizontally()
                .repeat(6));
    }};

}