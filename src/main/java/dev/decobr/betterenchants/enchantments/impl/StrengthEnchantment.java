package dev.decobr.betterenchants.enchantments.impl;

import dev.decobr.betterenchants.enchantments.BEEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class StrengthEnchantment extends BEEnchantment {
    public StrengthEnchantment() {
        super(Rarity.COMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public boolean isAcceptableItem(ItemStack stack) {
        return !(stack.getItem() instanceof ArmorItem) && super.isAcceptableItem(stack);
    }

    public int getMaxLevel() {
        return 3;
    }

    public int getMinPower(int level) {
        return 1 + 10 * (level - 1);
    }

    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        if (attacker != null)
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20 * (level + 1), level - 1));

        super.onUserDamaged(user, attacker, level);
    }

    public String registryName() {
        return "strength";
    }
}
