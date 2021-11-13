package wraith.croptosis;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import wraith.croptosis.registry.ItemRegistry;

public class CustomItemGroups {

    public static final ItemGroup CROPTOSIS = FabricItemGroupBuilder.create(Utils.ID("croptosis")).icon(() -> new ItemStack(ItemRegistry.get("apatite"))).build();

}
