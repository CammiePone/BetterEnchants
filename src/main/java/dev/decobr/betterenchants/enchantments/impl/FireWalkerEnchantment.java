package dev.decobr.betterenchants.enchantments.impl;

import dev.decobr.betterenchants.enchantments.BEEnchantment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class FireWalkerEnchantment extends BEEnchantment {
    public FireWalkerEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    public int getMinPower(int level) {
        return level * 10;
    }

    public int getMaxPower(int level) {
        return this.getMinPower(level) + 15;
    }

    public boolean isTreasure() {
        return true;
    }

    public int getMaxLevel() {
        return 2;
    }

    public boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != Enchantments.DEPTH_STRIDER;
    }

    public String registryName() {
        return "firewalker";
    }

    public void setFire(LivingEntity entity, World world, BlockPos blockPos, int level, ItemStack stack) {
        if (entity.isOnGround()) {
            BlockPos blockInfrontOfPlayer = blockPos.offset(entity.getMovementDirection()).down();
            // Lava -> Obsidian requires Firewalker 2
            if (world.getBlockState(blockInfrontOfPlayer).isOf(Blocks.LAVA) && level == 2) {
                for (BlockPos pos : BlockPos.iterate(blockInfrontOfPlayer, blockInfrontOfPlayer.offset(entity.getMovementDirection().getOpposite()))) {
                    BlockState blockState = Blocks.OBSIDIAN.getDefaultState();
                    world.setBlockState(pos, blockState);
                    world.getBlockTickScheduler().schedule(pos, Blocks.OBSIDIAN, 0);

                    stack.damage(1, entity, null);
                }
            } else {
                BlockPos blockBehindPlayer = blockPos.offset(entity.getMovementDirection().getOpposite(), 2);
                if (world.isAir(blockBehindPlayer) && !world.getBlockState(blockBehindPlayer.down()).isOf(Blocks.OBSIDIAN)) {
                    BlockState blockState = Blocks.FIRE.getDefaultState();
                    if (blockState.canPlaceAt(world, blockBehindPlayer) && world.canPlace(blockState, blockBehindPlayer, ShapeContext.absent())) {
                        world.setBlockState(blockBehindPlayer, blockState);
                        world.getBlockTickScheduler().schedule(blockBehindPlayer, Blocks.FIRE, MathHelper.nextInt(entity.getRandom(), 5 * level, 15 * level));
                    }
                }
            }
        }
    }
}
