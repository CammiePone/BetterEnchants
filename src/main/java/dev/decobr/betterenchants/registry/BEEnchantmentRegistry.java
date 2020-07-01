package dev.decobr.betterenchants.registry;

import dev.decobr.betterenchants.BetterEnchants;
import dev.decobr.betterenchants.enchantments.BEEnchantment;
import dev.decobr.betterenchants.enchantments.BEEnchantments;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class BEEnchantmentRegistry {
    public void registerAll() {
        for (BEEnchantments enchantment : BEEnchantments.values()) {
            if (enchantment != BEEnchantments.EXPLODING) {
                register(enchantment.getEnchantment());
            }
        }
    }

    private void register(BEEnchantment enchantment) {
        BetterEnchants.INSTANCE.logger.info("Registering enchantment " + enchantment.registryName());
        Registry.register(Registry.ENCHANTMENT, new Identifier("betterenchants", enchantment.registryName()), enchantment);
    }
}
