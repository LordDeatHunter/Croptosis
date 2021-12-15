package wraith.croptosis.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import wraith.croptosis.util.CUtils;
import wraith.croptosis.util.Config;

import java.util.List;

public class OreRegistry {

    public static final ConfiguredFeature<OreFeatureConfig, ?> POTASH_ORE = Feature.ORE.configure(
            new OreFeatureConfig(
                    List.of(
                            OreFeatureConfig.createTarget(
                                    OreConfiguredFeatures.STONE_ORE_REPLACEABLES, BlockRegistry.get("potash_ore").getDefaultState()
                            )
                    ), Config.getInstance().potashVeinSize()
            )
    );

    public static final ConfiguredFeature<OreFeatureConfig, ?> APATITE_ORE = Feature.ORE.configure(
            new OreFeatureConfig(
                    List.of(
                            OreFeatureConfig.createTarget(
                                    OreConfiguredFeatures.STONE_ORE_REPLACEABLES, BlockRegistry.get("apatite_ore").getDefaultState()
                            ),
                            OreFeatureConfig.createTarget(
                                    OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, BlockRegistry.get("deepslate_apatite_ore").getDefaultState()
                            )
                    ), Config.getInstance().apatiteVeinSize()
            )
    );

    private static final RegistryKey<ConfiguredFeature<?, ?>> POTASH_CONFIGURED_KEY = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, CUtils.ID("potash_ore"));
    private static final RegistryKey<ConfiguredFeature<?, ?>> APATITE_CONFIGURED_KEY = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, CUtils.ID("apatite_ore"));

    private static final RegistryKey<PlacedFeature> POTASH_PLACEMENT_KEY = RegistryKey.of(Registry.PLACED_FEATURE_KEY, CUtils.ID("potash_ore"));
    private static final RegistryKey<PlacedFeature> APATITE_PLACEMENT_KEY = RegistryKey.of(Registry.PLACED_FEATURE_KEY, CUtils.ID("apatite_ore"));

    private static final PlacedFeature POTASH_PLACEMENT_FEATURE = POTASH_ORE.withPlacement(
            modifiersWithCount(
                    Config.getInstance().maxPotashVeinsPerChunk(),
                    Config.getInstance().potashVeinShape().equals("trapezoid") ?
                            HeightRangePlacementModifier.trapezoid(
                                    YOffset.fixed(
                                            Config.getInstance().minimumPotashHeight()
                                    ),
                                    YOffset.fixed(
                                            Config.getInstance().maximumPotashHeight()
                                    )
                            ) :
                            HeightRangePlacementModifier.uniform(
                                    YOffset.fixed(
                                            Config.getInstance().minimumPotashHeight()
                                    ),
                                    YOffset.fixed(
                                            Config.getInstance().maximumPotashHeight()
                                    )
                            )

            )
    );
    private static final PlacedFeature APATITE_PLACEMENT_FEATURE = APATITE_ORE.withPlacement(
            modifiersWithCount(
                    Config.getInstance().maxApatiteVeinsPerChunk(),
                    Config.getInstance().apatiteVeinShape().equals("trapezoid") ?
                            HeightRangePlacementModifier.trapezoid(
                                    YOffset.fixed(
                                            Config.getInstance().minimumApatiteHeight()
                                    ),
                                    YOffset.fixed(Config.getInstance().maximumApatiteHeight()
                                    )
                            ) :
                            HeightRangePlacementModifier.uniform(
                                    YOffset.fixed(
                                            Config.getInstance().minimumApatiteHeight()
                                    ),
                                    YOffset.fixed(Config.getInstance().maximumApatiteHeight()
                                    )
                            )
            )
    );

    public static void init() {
        if (Config.getInstance().generatePotash()) {
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, POTASH_CONFIGURED_KEY, POTASH_ORE);
            Registry.register(BuiltinRegistries.PLACED_FEATURE, POTASH_PLACEMENT_KEY, POTASH_PLACEMENT_FEATURE);
            BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, CUtils.ID("potash_ore")));
        }

        if (Config.getInstance().generateApatite()) {
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, APATITE_CONFIGURED_KEY, APATITE_ORE);
            Registry.register(BuiltinRegistries.PLACED_FEATURE, APATITE_PLACEMENT_KEY, APATITE_PLACEMENT_FEATURE);
            BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, CUtils.ID("apatite_ore")));
        }
    }

    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModfier) {
        return modifiers(CountPlacementModifier.of(count), heightModfier);
    }

}
