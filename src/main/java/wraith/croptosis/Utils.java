package wraith.croptosis;

import net.minecraft.util.Identifier;

import java.util.Random;

public class Utils {

    public static final Random random = new Random();
    public static int getRandomIntInRange(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static Identifier ID(String id) {
        return new Identifier(Croptosis.MOD_ID, id);
    }

}
