package wraith.croptosis.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import wraith.croptosis.util.Config;

import java.util.List;

public class OreRegistry {

    public static void init() {
        if (Config.getInstance().generatePotash()) {
            var POTASH_ORE = ConfiguredFeatures.register(
                "potash_ore", Feature.ORE,
                new OreFeatureConfig(
                    List.of(
                        OreFeatureConfig.createTarget(
                            OreConfiguredFeatures.STONE_ORE_REPLACEABLES, BlockRegistry.get("potash_ore").getDefaultState()
                        )
                    ), Config.getInstance().potashVeinSize()
                )
            );
            var POTASH_ORE_FEATURE = PlacedFeatures.register(
                "potash_ore", POTASH_ORE,
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
            if (POTASH_ORE_FEATURE.getKey().isPresent()) {
                BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, POTASH_ORE_FEATURE.getKey().get());
            }
        }

        if (Config.getInstance().generateApatite()) {
            var APATITE_ORE = ConfiguredFeatures.register(
                "apatite_ore", Feature.ORE,
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
            var APATITE_ORE_FEATURE = PlacedFeatures.register(
                "apatite_ore", APATITE_ORE,
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
            if (APATITE_ORE_FEATURE.getKey().isPresent()) {
                BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, APATITE_ORE_FEATURE.getKey().get());
            }
        }
    }

    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacementModifier.of(count), heightModifier);
    }

}
