package wraith.croptosis.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import wraith.croptosis.Croptosis;
import wraith.croptosis.worldgen.CustomConfiguredFeatures;

import java.util.HashMap;
import java.util.Map;

public class OreRegistry {

    private static final HashMap<String, RegistryKey<ConfiguredFeature<?, ?>>> ORES = new HashMap<String, RegistryKey<ConfiguredFeature<?,?>>>(){{
        put("potash_ore", RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, Croptosis.ID("potash_ore")));
        put("apatite_ore", RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, Croptosis.ID("apatite_ore")));
    }};


    public static void register() {
        for (Map.Entry<String, RegistryKey<ConfiguredFeature<?, ?>>> ore : ORES.entrySet()) {
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ore.getValue().getValue(), CustomConfiguredFeatures.ORES.get(ore.getKey()));
            BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, ore.getValue());
        }
    }

}
