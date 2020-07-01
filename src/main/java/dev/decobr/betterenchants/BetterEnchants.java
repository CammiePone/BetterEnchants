package dev.decobr.betterenchants;

import dev.decobr.betterenchants.config.Configuration;
import dev.decobr.betterenchants.gui.ConfigGUI;
import dev.decobr.betterenchants.gui.ConfigScreen;
import dev.decobr.betterenchants.registry.BEEnchantmentRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.CommandManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Environment(EnvType.CLIENT)
public class BetterEnchants implements ModInitializer {
    public static final BetterEnchants INSTANCE = new BetterEnchants();
    public final Logger logger = LogManager.getLogger("BetterEnchants");
    public final BEEnchantmentRegistry registry = new BEEnchantmentRegistry();

    @Override
    public void onInitialize() {
        logger.info("Initializing");

        logger.info("Registering enchantments");
        registry.registerAll();

        logger.info("Registering commands");
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(CommandManager.literal("betterenchants").executes(context -> {
                MinecraftClient.getInstance().openScreen(new ConfigScreen(new ConfigGUI()));
                return 1;
            }));
        });

        logger.info("Loading configuration");
        try {
            Configuration.INSTANCE.loadConfig();
        } catch (IOException e) {
            BetterEnchants.INSTANCE.logger.warn("Failed to load configuration: " + e.getLocalizedMessage());
        }

        logger.info("Ready");
    }
}
