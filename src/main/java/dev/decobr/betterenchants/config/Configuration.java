package dev.decobr.betterenchants.config;

import com.google.gson.Gson;
import dev.decobr.betterenchants.BetterEnchants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

import java.io.*;

class ConfigurationJSON {
    final boolean fixEnchantCommand;

    ConfigurationJSON(boolean fixEnchantmentCommandIn) {
        fixEnchantCommand = fixEnchantmentCommandIn;
    }
}

@Environment(EnvType.CLIENT)
public class Configuration {
    public static final Configuration INSTANCE = new Configuration();
    public final File configurationFile = new File(MinecraftClient.getInstance().runDirectory, "config/betterenchants.json");
    public boolean fixEnchantCommand;

    public void loadConfig() throws IOException {
        if (!configurationFile.exists()) {
            if (configurationFile.createNewFile()) {
                BetterEnchants.INSTANCE.logger.info("Created config file!");
            } else {
                BetterEnchants.INSTANCE.logger.warn("Failed to create config file");
            }
        } else {
            Reader reader = new BufferedReader(new FileReader(configurationFile));
            ConfigurationJSON config = new Gson().fromJson(reader, ConfigurationJSON.class);
            reader.close();
            fixEnchantCommand = config.fixEnchantCommand;
        }
    }

    public void saveConfig() {
        try {
            if (!configurationFile.exists()) {
                if (configurationFile.createNewFile()) {
                    BetterEnchants.INSTANCE.logger.info("Created config file!");
                } else {
                    BetterEnchants.INSTANCE.logger.warn("Failed to create config file :(");
                    return;
                }
            }
            Writer writer = new FileWriter(configurationFile);
            new Gson().toJson(new ConfigurationJSON(fixEnchantCommand), writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            BetterEnchants.INSTANCE.logger.warn("Failed to save configuration: " + e.getLocalizedMessage());
        }
    }
}
