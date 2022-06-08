package wraith.croptosis.mixin;

import com.google.gson.JsonElement;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wraith.croptosis.Croptosis;
import wraith.croptosis.registry.ItemRegistry;
import wraith.croptosis.util.Config;

import java.util.HashSet;
import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At("HEAD"))
    public void removeRecipes(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
        if (!Config.getInstance().allowWateringCans()) {
            var wateringCanRecipes = map.keySet().stream().filter(id -> id.getNamespace().equals(Croptosis.MOD_ID) && id.getPath().contains("watering_can")).toList();
            for (var id : wateringCanRecipes) {
                map.remove(id);
            }
        }
        var fertilizers = new HashSet<String>();
        for (var fertilizer : ItemRegistry.FERTILIZER_ITEMS) {
            if (!Config.getInstance().isFertilizerItemEnabled(fertilizer)) {
                fertilizers.add(fertilizer);
            }
        }
        if (!fertilizers.isEmpty()) {
            var fertilizerRecipes = map.keySet().stream().filter(id -> id.getNamespace().equals(Croptosis.MOD_ID) && fertilizers.contains(id.getPath())).toList();
            for (var id : fertilizerRecipes) {
                map.remove(id);
            }
        }
    }

}
