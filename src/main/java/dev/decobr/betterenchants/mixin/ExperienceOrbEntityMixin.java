package dev.decobr.betterenchants.mixin;

import dev.decobr.betterenchants.enchantments.BEEnchantments;
import dev.decobr.betterenchants.helpers.ItemStackHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin extends Entity {
    @Shadow
    public int pickupDelay;
    @Shadow
    private int amount;

    public ExperienceOrbEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    protected abstract int getMendingRepairAmount(int experienceAmount);

    @Shadow
    protected abstract int getMendingRepairCost(int repairAmount);

    private int getRepairingRepairAmount(int experienceAmount, ItemStack item) {
        int level = ItemStackHelper.getLevelOfEnchantment(BEEnchantments.REPAIRING.getEnchantment(), item);

        if (ItemStackHelper.durabilityPercent(item) < (level == 2 ? 75 : 50)) {
            return experienceAmount * (level + 1);
        }

        return 0;
    }

    /**
     * @author Decobr + Camellias_
     * @reason To add Repairing with an @Inject
     */
    @Inject(method = "onPlayerCollision", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "net/minecraft/entity/player/PlayerEntity.sendPickup(Lnet/minecraft/entity/Entity;I)V", ordinal = 0))
    private void onPlayerCollision(PlayerEntity player, CallbackInfo info)
    {
        Map.Entry<EquipmentSlot, ItemStack> repairingEntry = EnchantmentHelper.chooseEquipmentWith(BEEnchantments.REPAIRING.getEnchantment(), player, ItemStack::isDamaged);

        if (repairingEntry != null) {
            ItemStack itemStack = repairingEntry.getValue();
            if (!itemStack.isEmpty() && itemStack.isDamaged()) {
                int i = Math.min(this.getRepairingRepairAmount(this.amount, itemStack), itemStack.getDamage());
                this.amount -= this.getMendingRepairCost(i);
                itemStack.setDamage(itemStack.getDamage() - i);
            }
        }
    }
}
