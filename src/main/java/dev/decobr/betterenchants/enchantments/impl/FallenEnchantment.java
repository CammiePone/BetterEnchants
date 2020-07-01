package dev.decobr.betterenchants.enchantments.impl;

import dev.decobr.betterenchants.enchantments.BEEnchantment;
import dev.decobr.betterenchants.helpers.MultithreadingHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

import java.util.concurrent.TimeUnit;

public class FallenEnchantment extends BEEnchantment {
    public FallenEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.BREAKABLE, EquipmentSlot.values());
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

    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target != null) {
            target.setPose(EntityPose.SLEEPING);
            MultithreadingHelper.schedule(() -> target.setPose(EntityPose.STANDING), 3, TimeUnit.SECONDS);
        }
    }

    public boolean isTreasure() {
        return true;
    }

    public String registryName() {
        return "fallen";
    }
}
