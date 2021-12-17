package wraith.croptosis.util;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.mixin.content.registry.HoeItemAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.HoeItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import wraith.croptosis.Croptosis;
import wraith.croptosis.block.FertilizedSandBlock;

import java.text.DecimalFormat;
import java.util.Random;

public final class CUtils {

    private CUtils() {
    }

    public static final Random random = new Random();

    public static int getRandomIntInRange(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static Identifier ID(String id) {
        return new Identifier(Croptosis.MOD_ID, id);
    }

    public static String capitalize(String s) {
        return s == null || s.length() < 1 ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public static int getCropMaxHeight(int maxHeight, BlockState state, ServerWorld world, BlockPos pos, Random random, Block block) {
        var topPos = pos.up();
        if (!world.isAir(topPos)) {
            return maxHeight;
        }
        int height = 1;
        while (world.getBlockState(pos.down(height)).isOf(block)) {
            ++height;
        }
        BlockState plantedOn = world.getBlockState(pos.down(height));
        return !(plantedOn.getBlock() instanceof FertilizedSandBlock) ? maxHeight : plantedOn.get(FertilizedSandBlock.MAX_HEIGHT);
    }

    public static void addTillable(Block original, Block result) {
        HoeItemAccessor.getTillingActions().put(original, Pair.of(HoeItem::canTillFarmland, HoeItem.createTillAction(result.getDefaultState())));
    }

    private static final DecimalFormat DF = new DecimalFormat("#.##");

    public static String formatDouble(double val) {
        return DF.format(val);
    }

}
