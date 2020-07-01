package dev.decobr.betterenchants.helpers;

import dev.decobr.betterenchants.enchantments.BEEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

public class ItemStackHelper {
    public static int durabilityRemaining(ItemStack stack) {
        return stack.getMaxDamage() - stack.getDamage();
    }

    public static double durabilityPercent(ItemStack stack) {
        return (float) durabilityRemaining(stack) / (float) stack.getMaxDamage() * 100;
    }

    public static boolean antibreakEligible(ItemStack stack) {
        return hasEnchantment(BEEnchantments.ANTIBREAK, stack) && durabilityRemaining(stack) == 1;
    }

    public static boolean hasEnchantment(Enchantment enchantment, ItemStack stack) {
        return EnchantmentHelper.fromTag(stack.getEnchantments()).containsKey(enchantment);
    }

    public static boolean hasEnchantment(BEEnchantments enchantment, ItemStack stack) {
        return EnchantmentHelper.fromTag(stack.getEnchantments()).containsKey(enchantment.getEnchantment());
    }

    public static int getLevelOfEnchantment(Enchantment enchantment, ItemStack stack) {
        return EnchantmentHelper.fromTag(stack.getEnchantments()).get(enchantment);
    }

    public static int getLevelOfEnchantment(BEEnchantments enchantment, ItemStack stack) {
        return EnchantmentHelper.fromTag(stack.getEnchantments()).get(enchantment.getEnchantment());
    }
}
