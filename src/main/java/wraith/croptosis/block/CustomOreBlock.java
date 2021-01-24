package wraith.croptosis.block;

import net.minecraft.block.OreBlock;
import net.minecraft.util.math.MathHelper;
import wraith.croptosis.registry.BlockRegistry;

import java.util.Random;

public class CustomOreBlock extends OreBlock {

    public CustomOreBlock(Settings settings) {
        super(settings);
    }

    @Override
    public int getExperienceWhenMined(Random random) {
        if (this == BlockRegistry.BLOCKS.get("apatite_ore") || this == BlockRegistry.BLOCKS.get("potash_ore")) {
            return MathHelper.nextInt(random, 0, 3);
        }
        return 0;
    }

}
