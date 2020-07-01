package dev.decobr.betterenchants.mixin;

import dev.decobr.betterenchants.enchantments.BEEnchantments;
import dev.decobr.betterenchants.enchantments.impl.FireWalkerEnchantment;
import dev.decobr.betterenchants.helpers.ItemStackHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "applyMovementEffects", at = @At("HEAD"))
    protected void applyMovementEffects(BlockPos pos, CallbackInfo ci) {
        LivingEntity casted = (LivingEntity) (Object) this;
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.chooseEquipmentWith(BEEnchantments.FIREWALKER.getEnchantment(), casted);

        if (entry != null) {
            ItemStack item = entry.getValue();
            FireWalkerEnchantment enchantment = ((FireWalkerEnchantment) BEEnchantments.FIREWALKER.getEnchantment());
            enchantment.setFire(casted, this.world, pos, ItemStackHelper.getLevelOfEnchantment(BEEnchantments.FIREWALKER, item), item);
        }
    }
}
