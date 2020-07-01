package dev.decobr.betterenchants.mixin;

import dev.decobr.betterenchants.enchantments.BEEnchantments;
import dev.decobr.betterenchants.helpers.ItemStackHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {
    @Shadow
    @Final
    public PlayerEntity player;

    @Shadow
    @Final
    private List<DefaultedList<ItemStack>> combinedInventory;

    /**
     * @author Decobr
     * @reason Make the code better + don't drop Soulbound items
     */
    @Overwrite
    public void dropAll() {
        for (DefaultedList<ItemStack> itemStacks : this.combinedInventory) {
            for (ItemStack stack : itemStacks) {
                if (!stack.isEmpty() && !(ItemStackHelper.hasEnchantment(BEEnchantments.SOULBOUND, stack))) {
                    this.player.dropItem(stack, true, false);
                    ((List<ItemStack>) itemStacks).set(((List<ItemStack>) itemStacks).indexOf(stack), ItemStack.EMPTY);
                }
            }
        }
    }
}
