package dev.decobr.betterenchants.enchantments;

import dev.decobr.betterenchants.enchantments.impl.*;

public enum BEEnchantments {
    STRENGTH(new StrengthEnchantment()),
    ANTIBREAK(new AntibreakEnchantment()),
    SOULBOUND(new SoulboundEnchantment()),
    REPAIRING(new RepairingEnchantment()),
    FALLEN(new FallenEnchantment()),
    FIREWALKER(new FireWalkerEnchantment()),
    EXPLODING(new ExplodingEnchantment());

    private final BEEnchantment enchantment;

    BEEnchantments(BEEnchantment enchantment) {
        this.enchantment = enchantment;
    }

    public BEEnchantment getEnchantment() {
        return enchantment;
    }
}
