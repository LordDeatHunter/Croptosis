package wraith.croptosis.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import wraith.croptosis.Croptosis;
import wraith.croptosis.util.CUtils;
import wraith.croptosis.util.Config;

import java.util.List;

public class WateringCanItem extends Item {

    private final int range;
    private final double chance;
    private final int capacity;

    public WateringCanItem(int range, int capacity, double chance, Settings settings) {
        super(settings.maxCount(1));
        this.range = Math.min(range, 10);
        this.capacity = capacity;
        this.chance = MathHelper.clamp(chance, 0, 1);
    }

    public static boolean isFilled(ItemStack stack) {
        if (!Config.getInstance().allowWateringCans() || stack.isEmpty() || !(stack.getItem() instanceof WateringCanItem wateringCan)) {
            return false;
        }
        NbtCompound tag = stack.getSubNbt(CUtils.capitalize(Croptosis.MOD_ID));
        return tag != null && ((tag.contains("StoredFluid") && tag.getInt("StoredFluid") > 0) || wateringCan.capacity == -1);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 10;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!Config.getInstance().allowWateringCans()) {
            return TypedActionResult.fail(user.getStackInHand(hand));
        }
        ItemStack stack = user.getStackInHand(hand);
        var wateringCan = (WateringCanItem) stack.getItem();
        if (!isFilled(stack)) {
            BlockHitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.ANY);
            if (hitResult.getType() == HitResult.Type.MISS || hitResult.getType() != HitResult.Type.BLOCK) {
                return TypedActionResult.pass(stack);
            }

            BlockPos pos = hitResult.getBlockPos();

            if (world.getFluidState(pos).getFluid() instanceof WaterFluid) {
                NbtCompound tag = stack.getOrCreateSubNbt(CUtils.capitalize(Croptosis.MOD_ID));
                tag.putInt("StoredFluid", capacity);
            }
        } else {
            int xPos = (int) Math.floor(user.getBlockPos().getX());
            int yPos = (int) Math.floor(user.getBlockPos().getY());
            int zPos = (int) Math.floor(user.getBlockPos().getZ());
            NbtCompound tag = stack.getOrCreateSubNbt(CUtils.capitalize(Croptosis.MOD_ID));
            if (wateringCan.capacity > 0) {
                tag.putInt("StoredFluid", tag.getInt("StoredFluid") - 1);
            }
            world.playSound(user, user.getBlockPos(), SoundEvents.BLOCK_WET_GRASS_PLACE, SoundCategory.AMBIENT, 1F, 1F);

            for (float x = -2f; x <= 2f; x += 2f) {
                for (float z = -2f; z <= 2f; z += 2f) {
                    world.addParticle(ParticleTypes.FALLING_WATER, xPos + 0.5f + x * CUtils.random.nextDouble(), yPos + 3f - CUtils.random.nextDouble(), zPos + 0.5 + z * CUtils.random.nextDouble(), x, 1f, z);
                }
            }

            if (world.isClient) {
                return TypedActionResult.success(stack);
            }
            for (int x = -range; x <= range; ++x) {
                for (int y = -3; y <= 3; ++y) {
                    for (int z = -range; z <= range; ++z) {
                        if (CUtils.random.nextDouble() > chance) {
                            continue;
                        }
                        BlockPos cropPos = new BlockPos(xPos + x, yPos + y, zPos + z);
                        BlockState state = world.getBlockState(cropPos);
                        if (state.getBlock() instanceof Fertilizable fertilizable) {
                            if (fertilizable.isFertilizable(world, cropPos, state, false) && fertilizable.canGrow(world, world.random, cropPos, state)) {
                                fertilizable.grow((ServerWorld) world, world.random, cropPos, state);
                            }
                        }
                    }
                }
            }
        }
        return TypedActionResult.success(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (!Config.getInstance().allowWateringCans()) {
            tooltip.add(Text.translatable("tooltip.croptosis.watering_can.disabled"));
            return;
        }
        if (!(stack.getItem() instanceof WateringCanItem wateringCan)) {
            return;
        }
        NbtCompound tag = stack.getSubNbt(CUtils.capitalize(Croptosis.MOD_ID));
        var fluidAmount = tag != null && tag.contains("StoredFluid") ? tag.getInt("StoredFluid") : 0;
        var fluidAmountStr = fluidAmount == -1 ? "∞" : String.valueOf(fluidAmount);
        var capacityStr = wateringCan.capacity == -1 ? "∞" : String.valueOf(wateringCan.capacity);
        // TODO: Make colored args work
        tooltip.add(Text.translatable(
            "tooltip.croptosis.watering_can.range",
            Text.literal(String.valueOf(wateringCan.range)).styled(style -> style.withColor(TextColor.parse(Text.translatable("tooltip.croptosis.watering_can.range.arg_color").getString())))
        ));
        tooltip.add(Text.translatable(
            "tooltip.croptosis.watering_can.fluid",
            Text.literal(fluidAmountStr).styled(style -> style.withColor(TextColor.parse(Text.translatable("tooltip.croptosis.watering_can.fluid.arg_color1").getString()))),
            Text.literal(capacityStr).styled(style -> style.withColor(TextColor.parse(Text.translatable("tooltip.croptosis.watering_can.fluid.arg_color2").getString())))
        ));
        tooltip.add(Text.translatable(
            "tooltip.croptosis.watering_can.chance",
            Text.literal(CUtils.formatDouble(wateringCan.chance * 100)).styled(style -> style.withColor(TextColor.parse(Text.translatable("tooltip.croptosis.watering_can.chance.arg_color").getString()))),
            Text.literal("%").styled(style -> style.withColor(TextColor.parse(Text.translatable("tooltip.croptosis.watering_can.chance.percent_color").getString())))
        ));
    }

}