package dev.decobr.betterenchants.enchantments.impl;

import dev.decobr.betterenchants.enchantments.BEEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class RepairingEnchantment extends BEEnchantment {
    public RepairingEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.BREAKABLE, EquipmentSlot.values());
    }

    public int getMaxLevel() {
        return 2;
    }

    public int getMinPower(int level) {
        return level * 25;
    }

    public int getMaxPower(int level) {
        return this.getMinPower(level) + 50;
    }

    public String registryName() {
        return "repairing";
    }
}
