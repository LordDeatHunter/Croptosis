package wraith.croptosis.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.nbt.NbtCompound;
import wraith.croptosis.registry.ItemRegistry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

import static wraith.croptosis.Croptosis.LOGGER;

public final class Config {

    private static final String CONFIG_FILE = "config/croptosis/config.json";
    private NbtCompound configData;
    private static Config INSTANCE = null;

    private Config() {
    }

    public static Config getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Config();
            INSTANCE.loadConfig();
        }
        return INSTANCE;
    }

    public boolean generatePotash() {
        return configData.getCompound("potash_oregen").getBoolean("enabled");
    }

    public boolean generateApatite() {
        return configData.getCompound("apatite_oregen").getBoolean("enabled");
    }

    public String potashVeinShape() {
        return configData.getCompound("potash_oregen").getString("vein_shape");
    }

    public String apatiteVeinShape() {
        return configData.getCompound("apatite_oregen").getString("vein_shape");
    }

    public int potashVeinSize() {
        return configData.getCompound("potash_oregen").getInt("vein_size");
    }

    public int apatiteVeinSize() {
        return configData.getCompound("apatite_oregen").getInt("vein_size");
    }

    public int minimumPotashHeight() {
        return configData.getCompound("potash_oregen").getInt("min_height");
    }

    public int minimumApatiteHeight() {
        return configData.getCompound("apatite_oregen").getInt("min_height");
    }

    public int maximumPotashHeight() {
        return configData.getCompound("potash_oregen").getInt("max_height");
    }

    public int maximumApatiteHeight() {
        return configData.getCompound("apatite_oregen").getInt("max_height");
    }

    public int maxPotashVeinsPerChunk() {
        return configData.getCompound("potash_oregen").getInt("per_chunk");
    }

    public int maxApatiteVeinsPerChunk() {
        return configData.getCompound("apatite_oregen").getInt("per_chunk");
    }

    public int getWateringCanRange(String wateringCan) {
        var wateringCans = configData.getCompound("watering_cans");
        if (!wateringCans.contains(wateringCan)) {
            LOGGER.error("Watering can " + wateringCan + " not found in config file!");
            return 0;
        }
        return !allowWateringCans() ? 0 : wateringCans.getCompound(wateringCan).getInt("range");
    }

    public int getWateringCanCapacity(String wateringCan) {
        var wateringCans = configData.getCompound("watering_cans");
        if (!wateringCans.contains(wateringCan)) {
            LOGGER.error("Watering can " + wateringCan + " not found in config file!");
            return 0;
        }
        return !allowWateringCans() ? 0 : wateringCans.getCompound(wateringCan).getInt("capacity");
    }

    public double getWateringCanChance(String wateringCan) {
        var wateringCans = configData.getCompound("watering_cans");
        if (!wateringCans.contains(wateringCan)) {
            LOGGER.error("Watering can " + wateringCan + " not found in config file!");
            return 0;
        }
        return !allowWateringCans() ? 0 : wateringCans.getCompound(wateringCan).getDouble("chance");
    }

    public boolean allowWateringCans() {
        return configData.getCompound("watering_cans").getBoolean("enabled");
    }

    public boolean canConvertToFertilizedBlocks() {
        return configData.getCompound("fertilized_blocks").getBoolean("enable_bone_meal_conversion");
    }

    public boolean isFertilizerItemEnabled(String item) {
        var fertilizers = configData.getCompound("fertilizer_items");
        if (!fertilizers.contains("enable_" + item)) {
            LOGGER.error("Fertilizer item " + item + " not found in config file!");
            return false;
        }
        return fertilizers.getBoolean("enable_" + item);
    }

    private NbtCompound getDefaults() {
        NbtCompound defaultConfig = new NbtCompound();

        NbtCompound potash = new NbtCompound();
        potash.putBoolean("enabled", true);
        potash.putString("vein_shape", "trapezoid");
        potash.putInt("min_height", 52);
        potash.putInt("max_height", 204);
        potash.putInt("per_chunk", 8);
        potash.putInt("vein_size", 8);
        defaultConfig.put("potash_oregen", potash);

        NbtCompound apatite = new NbtCompound();
        apatite.putBoolean("enabled", true);
        potash.putString("vein_shape", "uniform");
        apatite.putInt("min_height", -64);
        apatite.putInt("max_height", 34);
        apatite.putInt("per_chunk", 4);
        apatite.putInt("vein_size", 12);
        defaultConfig.put("apatite_oregen", apatite);

        NbtCompound wateringCans = new NbtCompound();
        wateringCans.putBoolean("enabled", true);

        NbtCompound ironWateringCan = new NbtCompound();
        ironWateringCan.putInt("range", 2);
        ironWateringCan.putInt("capacity", 12);
        ironWateringCan.putDouble("chance", 0.02);

        NbtCompound goldWateringCan = new NbtCompound();
        goldWateringCan.putInt("range", 5);
        goldWateringCan.putInt("capacity", 6);
        goldWateringCan.putDouble("chance", 0.025);

        NbtCompound diamondWateringCan = new NbtCompound();
        diamondWateringCan.putInt("range", 3);
        diamondWateringCan.putInt("capacity", 16);
        diamondWateringCan.putDouble("chance", 0.025);

        NbtCompound netheriteWateringCan = new NbtCompound();
        netheriteWateringCan.putInt("range", 4);
        netheriteWateringCan.putInt("capacity", 20);
        netheriteWateringCan.putDouble("chance", 0.03);

        wateringCans.put("iron", ironWateringCan);
        wateringCans.put("gold", goldWateringCan);
        wateringCans.put("diamond", diamondWateringCan);
        wateringCans.put("netherite", netheriteWateringCan);
        defaultConfig.put("watering_cans", wateringCans);

        NbtCompound fertilizers = new NbtCompound();
        for (var fertilizer : ItemRegistry.FERTILIEZR_ITEMS) {
            fertilizers.putBoolean("enable_" + fertilizer, true);
        }
        defaultConfig.put("fertilizer_items", fertilizers);

        NbtCompound fertilizedBlocks = new NbtCompound();
        fertilizedBlocks.putBoolean("enable_bone_meal_conversion", true);
        defaultConfig.put("fertilized_blocks", fertilizedBlocks);

        return defaultConfig;
    }

    private int difference = 0;

    public int getIntOrDefault(NbtCompound getFrom, String key, NbtCompound defaults) {
        if (getFrom.contains(key)) {
            return getFrom.getInt(key);
        } else {
            ++difference;
            return defaults.getInt(key);
        }
    }

    public boolean getBooleanOrDefault(NbtCompound getFrom, String key, NbtCompound defaults) {
        if (getFrom.contains(key)) {
            return getFrom.getBoolean(key);
        } else {
            ++difference;
            return defaults.getBoolean(key);
        }
    }

    private String getStringOrDefault(NbtCompound getFrom, String key, NbtCompound defaults) {
        if (getFrom.contains(key)) {
            return getFrom.getString(key);
        } else {
            ++difference;
            return defaults.getString(key);
        }
    }

    private NbtCompound getCompoundOrDefault(NbtCompound getFrom, String key, NbtCompound defaults) {
        if (getFrom.contains(key)) {
            return getFrom.getCompound(key);
        } else {
            ++difference;
            return defaults.getCompound(key);
        }
    }

    private double getDoubleOrDefault(NbtCompound getFrom, String key, NbtCompound defaults) {
        if (getFrom.contains(key)) {
            return getFrom.getDouble(key);
        } else {
            ++difference;
            return defaults.getDouble(key);
        }
    }

    public int getIntOrDefault(JsonObject getFrom, String key, NbtCompound defaults) {
        if (getFrom.has(key)) {
            return getFrom.get(key).getAsInt();
        } else {
            ++difference;
            return defaults.getInt(key);
        }
    }

    public boolean getBooleanOrDefault(JsonObject getFrom, String key, NbtCompound defaults) {
        if (getFrom.has(key)) {
            return getFrom.get(key).getAsBoolean();
        } else {
            ++difference;
            return defaults.getBoolean(key);
        }
    }

    private String getStringOrDefault(JsonObject getFrom, String key, NbtCompound defaults) {
        if (getFrom.has(key)) {
            return getFrom.get(key).getAsString();
        } else {
            ++difference;
            return defaults.getString(key);
        }
    }

    private double getDoubleOrDefault(JsonObject getFrom, String key, NbtCompound defaults) {
        if (getFrom.has(key)) {
            return getFrom.get(key).getAsDouble();
        } else {
            ++difference;
            return defaults.getDouble(key);
        }
    }

    private JsonObject toJson(NbtCompound tag) {
        JsonObject json = new JsonObject();
        NbtCompound defaults = getDefaults();

        JsonObject potashOregen = new JsonObject();
        NbtCompound potash = getCompoundOrDefault(tag, "potash_oregen", defaults);
        potashOregen.addProperty("enabled", getBooleanOrDefault(potash, "enabled", defaults));
        potashOregen.addProperty("vein_shape", getStringOrDefault(potash, "vein_shape", defaults));
        potashOregen.addProperty("min_height", getIntOrDefault(potash, "min_height", defaults));
        potashOregen.addProperty("max_height", getIntOrDefault(potash, "max_height", defaults));
        potashOregen.addProperty("per_chunk", getIntOrDefault(potash, "per_chunk", defaults));
        potashOregen.addProperty("vein_size", getIntOrDefault(potash, "vein_size", defaults));
        json.add("potash_oregen", potashOregen);

        JsonObject apatiteOregen = new JsonObject();
        NbtCompound apatite = getCompoundOrDefault(tag, "apatite_oregen", defaults);
        apatiteOregen.addProperty("enabled", getBooleanOrDefault(apatite, "enabled", defaults));
        apatiteOregen.addProperty("vein_shape", getStringOrDefault(apatite, "vein_shape", defaults));
        apatiteOregen.addProperty("min_height", getIntOrDefault(apatite, "min_height", defaults));
        apatiteOregen.addProperty("max_height", getIntOrDefault(apatite, "max_height", defaults));
        apatiteOregen.addProperty("per_chunk", getIntOrDefault(apatite, "per_chunk", defaults));
        apatiteOregen.addProperty("vein_size", getIntOrDefault(apatite, "vein_size", defaults));
        json.add("apatite_oregen", apatiteOregen);

        JsonObject wateringCan = new JsonObject();
        NbtCompound wateringCanNbt = getCompoundOrDefault(tag, "watering_cans", defaults);
        wateringCan.addProperty("enabled", getBooleanOrDefault(wateringCanNbt, "enabled", defaults));

        JsonObject ironWateringCan = new JsonObject();
        NbtCompound ironWateringCanNbt = getCompoundOrDefault(wateringCanNbt, "iron", defaults);
        ironWateringCan.addProperty("range", ironWateringCanNbt.getInt("range"));
        ironWateringCan.addProperty("capacity", ironWateringCanNbt.getInt("capacity"));
        ironWateringCan.addProperty("chance", ironWateringCanNbt.getDouble("chance"));

        JsonObject goldWateringCan = new JsonObject();
        NbtCompound goldWateringCanNbt = getCompoundOrDefault(wateringCanNbt, "gold", defaults);
        goldWateringCan.addProperty("range", goldWateringCanNbt.getInt("range"));
        goldWateringCan.addProperty("capacity", goldWateringCanNbt.getInt("capacity"));
        goldWateringCan.addProperty("chance", goldWateringCanNbt.getDouble("chance"));

        JsonObject diamondWateringCan = new JsonObject();
        NbtCompound diamondWateringCanNbt = getCompoundOrDefault(wateringCanNbt, "diamond", defaults);
        diamondWateringCan.addProperty("range", diamondWateringCanNbt.getInt("range"));
        diamondWateringCan.addProperty("capacity", diamondWateringCanNbt.getInt("capacity"));
        diamondWateringCan.addProperty("chance", diamondWateringCanNbt.getDouble("chance"));

        JsonObject netheriteWateringCan = new JsonObject();
        NbtCompound netheriteWateringCanNbt = getCompoundOrDefault(wateringCanNbt, "netherite", defaults);
        netheriteWateringCan.addProperty("range", netheriteWateringCanNbt.getInt("range"));
        netheriteWateringCan.addProperty("capacity", netheriteWateringCanNbt.getInt("capacity"));
        netheriteWateringCan.addProperty("chance", netheriteWateringCanNbt.getDouble("chance"));

        wateringCan.add("iron", ironWateringCan);
        wateringCan.add("gold", goldWateringCan);
        wateringCan.add("diamond", diamondWateringCan);
        wateringCan.add("netherite", netheriteWateringCan);
        json.add("watering_cans", wateringCan);

        JsonObject fertilizerItems = new JsonObject();
        NbtCompound fertilizerItemsNbt = getCompoundOrDefault(tag, "fertilizer_items", defaults);
        for (var fertilizer : ItemRegistry.FERTILIEZR_ITEMS) {
            var fertilizerKey = "enable_" + fertilizer;
            fertilizerItems.addProperty(fertilizerKey, getBooleanOrDefault(fertilizerItemsNbt, fertilizerKey, defaults));
        }
        json.add("fertilizer_items", fertilizerItems);

        JsonObject fertilizedBlocks = new JsonObject();
        NbtCompound fertilizedBlocksNbt = getCompoundOrDefault(tag, "fertilized_blocks", defaults);
        fertilizedBlocks.addProperty("enable_bone_meal_conversion", getBooleanOrDefault(fertilizedBlocksNbt, "enable_bone_meal_conversion", defaults));
        json.add("fertilized_blocks", fertilizedBlocks);

        createFile(json, this.difference > 0);
        difference = 0;
        return json;
    }

    private NbtCompound toNbtCompound(JsonObject json) {
        NbtCompound tag = new NbtCompound();
        NbtCompound defaults = getDefaults();

        NbtCompound potashOregen = new NbtCompound();
        if (json.has("potash_oregen")) {
            var potashJson = json.get("potash_oregen").getAsJsonObject();
            var defaultPotash = defaults.getCompound("potash_oregen");
            potashOregen.putBoolean("enabled", getBooleanOrDefault(potashJson, "enabled", defaultPotash));
            potashOregen.putString("vein_shape", getStringOrDefault(potashJson, "vein_shape", defaultPotash));
            potashOregen.putInt("min_height", getIntOrDefault(potashJson, "min_height", defaultPotash));
            potashOregen.putInt("max_height", getIntOrDefault(potashJson, "max_height", defaultPotash));
            potashOregen.putInt("per_chunk", getIntOrDefault(potashJson, "per_chunk", defaultPotash));
            potashOregen.putInt("vein_size", getIntOrDefault(potashJson, "vein_size", defaultPotash));
        } else {
            ++difference;
            potashOregen = defaults.getCompound("potash_oregen");
        }
        tag.put("potash_oregen", potashOregen);

        NbtCompound apatiteOregen = new NbtCompound();
        if (json.has("apatite_oregen")) {
            var apatiteJson = json.get("apatite_oregen").getAsJsonObject();
            var defaultApatite = defaults.getCompound("apatite_oregen");
            apatiteOregen.putBoolean("enabled", getBooleanOrDefault(apatiteJson, "enabled", defaultApatite));
            apatiteOregen.putString("vein_shape", getStringOrDefault(apatiteJson, "vein_shape", defaultApatite));
            apatiteOregen.putInt("min_height", getIntOrDefault(apatiteJson, "min_height", defaultApatite));
            apatiteOregen.putInt("max_height", getIntOrDefault(apatiteJson, "max_height", defaultApatite));
            apatiteOregen.putInt("per_chunk", getIntOrDefault(apatiteJson, "per_chunk", defaultApatite));
            apatiteOregen.putInt("vein_size", getIntOrDefault(apatiteJson, "vein_size", defaultApatite));
        } else {
            ++difference;
            apatiteOregen = defaults.getCompound("apatite_oregen");
        }
        tag.put("apatite_oregen", apatiteOregen);

        NbtCompound wateringCans = new NbtCompound();
        if (json.has("watering_cans")) {
            var wateringCanJson = json.get("watering_cans").getAsJsonObject();
            var defaultWateringCan = defaults.getCompound("watering_cans");
            wateringCans.putBoolean("enabled", getBooleanOrDefault(wateringCanJson, "enabled", defaultWateringCan));
            NbtCompound ironWateringCan = defaultWateringCan.getCompound("iron");
            NbtCompound goldWateringCan = defaultWateringCan.getCompound("gold");
            NbtCompound diamondWateringCan = defaultWateringCan.getCompound("diamond");
            NbtCompound netheriteWateringCan = defaultWateringCan.getCompound("netherite");
            if (wateringCanJson.has("iron")) {
                var ironJson = wateringCanJson.get("iron").getAsJsonObject();
                ironWateringCan.putInt("range", getIntOrDefault(ironJson, "range", defaultWateringCan));
                ironWateringCan.putInt("capacity", getIntOrDefault(ironJson, "capacity", defaultWateringCan));
                ironWateringCan.putDouble("chance", getDoubleOrDefault(ironJson, "chance", defaultWateringCan));
            }
            if (wateringCanJson.has("gold")) {
                var goldJson = wateringCanJson.get("gold").getAsJsonObject();
                goldWateringCan.putInt("range", getIntOrDefault(goldJson, "range", defaultWateringCan));
                goldWateringCan.putInt("capacity", getIntOrDefault(goldJson, "capacity", defaultWateringCan));
                goldWateringCan.putDouble("chance", getDoubleOrDefault(goldJson, "chance", defaultWateringCan));
            }
            if (wateringCanJson.has("diamond")) {
                var diamondJson = wateringCanJson.get("diamond").getAsJsonObject();
                diamondWateringCan.putInt("range", getIntOrDefault(diamondJson, "range", defaultWateringCan));
                diamondWateringCan.putInt("capacity", getIntOrDefault(diamondJson, "capacity", defaultWateringCan));
                diamondWateringCan.putDouble("chance", getDoubleOrDefault(diamondJson, "chance", defaultWateringCan));
            }
            if (wateringCanJson.has("netherite")) {
                var netheriteJson = wateringCanJson.get("netherite").getAsJsonObject();
                netheriteWateringCan.putInt("range", getIntOrDefault(netheriteJson, "range", defaultWateringCan));
                netheriteWateringCan.putInt("capacity", getIntOrDefault(netheriteJson, "capacity", defaultWateringCan));
                netheriteWateringCan.putDouble("chance", getDoubleOrDefault(netheriteJson, "chance", defaultWateringCan));
            }
            wateringCans.put("iron", ironWateringCan);
            wateringCans.put("gold", goldWateringCan);
            wateringCans.put("diamond", diamondWateringCan);
            wateringCans.put("netherite", netheriteWateringCan);
        } else {
            ++difference;
            wateringCans = defaults.getCompound("watering_cans");
        }
        tag.put("watering_cans", wateringCans);

        NbtCompound fertilizerItems = new NbtCompound();
        if (json.has("fertilizer_items")) {
            var fertilizerItemsJson = json.get("fertilizer_items").getAsJsonObject();
            var defaultFertilizerItems = defaults.getCompound("fertilizer_items");
            for (var fertilizer : ItemRegistry.FERTILIEZR_ITEMS) {
                var fertilizerKey = "enable_" + fertilizer;
                fertilizerItems.putBoolean(fertilizerKey, getBooleanOrDefault(fertilizerItemsJson, fertilizerKey, defaultFertilizerItems));
            }
        } else {
            ++difference;
            fertilizerItems = defaults.getCompound("fertilizer_items");
        }
        tag.put("fertilizer_items", fertilizerItems);

        NbtCompound fertilizedBlocks = new NbtCompound();
        if (json.has("fertilized_blocks")) {
            var fertilizedBlocksJson = json.get("fertilized_blocks").getAsJsonObject();
            var defaultFertilizedBlocks = defaults.getCompound("fertilized_blocks");
            fertilizedBlocks.putBoolean("enable_bone_meal_conversion", getBooleanOrDefault(fertilizedBlocksJson, "enable_bone_meal_conversion", defaultFertilizedBlocks));
        } else {
            ++difference;
            fertilizedBlocks = defaults.getCompound("fertilized_blocks");
        }
        tag.put("fertilized_blocks", fertilizedBlocks);

        createFile(toJson(tag), this.difference > 0);
        difference = 0;
        return tag;
    }

    public boolean loadConfig() {
        try {
            return loadConfig(getJsonObject(readFile(new File(CONFIG_FILE))));
        } catch (Exception e) {
            LOGGER.info("Found error with config. Using default config.");
            this.configData = getDefaults();
            createFile(toJson(this.configData), true);
            return false;
        }
    }

    private boolean loadConfig(JsonObject fileConfig) {
        try {
            this.configData = toNbtCompound(fileConfig);
            return true;
        } catch (Exception e) {
            LOGGER.info("Found error with config. Using default config.");
            this.configData = getDefaults();
            createFile(toJson(this.configData), true);
            return false;
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean loadConfig(NbtCompound config) {
        try {
            this.configData = config;
            return true;
        } catch (Exception e) {
            LOGGER.info("Found error with config. Using default config.");
            this.configData = getDefaults();
            createFile(toJson(this.configData), true);
            return false;
        }
    }

    private void createFile(JsonObject contents, boolean overwrite) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        contents = getJsonObject(gson.toJson(contents));

        File file = new File(Config.CONFIG_FILE);
        if (file.exists() && !overwrite) {
            return;
        }
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        file.setReadable(true);
        file.setWritable(true);
        file.setExecutable(true);
        if (contents == null) {
            return;
        }
        try (FileWriter writer = new FileWriter(file)) {
            String json = gson.toJson(contents).replace("\n", "").replace("\r", "");
            writer.write(gson.toJson(getJsonObject(json)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readFile(File file) throws FileNotFoundException {
        Scanner scanner;
        scanner = new Scanner(file);
        scanner.useDelimiter("\\Z");
        var result = scanner.next();
        scanner.close();
        return result;
    }


    public static JsonObject getJsonObject(String json) {
        return JsonParser.parseString(json).getAsJsonObject();
    }

}
