package dev.decobr.betterenchants.enchantments.impl;

import dev.decobr.betterenchants.enchantments.BEEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class AntibreakEnchantment extends BEEnchantment {
    public AntibreakEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public boolean isAcceptableItem(ItemStack stack) {
        return !(stack.getItem() instanceof ArmorItem) && super.isAcceptableItem(stack);
    }

    public int getMaxLevel() {
        return 1;
    }

    public int getMinPower(int level) {
        return 1 + 10 * (level - 1);
    }

    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    public String registryName() {
        return "antibreak";
    }
}
