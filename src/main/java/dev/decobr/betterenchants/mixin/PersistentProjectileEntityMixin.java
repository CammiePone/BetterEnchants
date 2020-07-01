package dev.decobr.betterenchants.mixin;

import dev.decobr.betterenchants.enchantments.BEEnchantments;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin extends Entity {
    private boolean didHit = false;

    public PersistentProjectileEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Environment(EnvType.CLIENT)
    @Inject(method = "onBlockHit", at = @At("RETURN"), cancellable = true)
    private void onBlockHit(BlockHitResult blockHitResult, CallbackInfo ci) {
        if (!didHit && MinecraftClient.getInstance().world != null) {
            if (EnchantmentHelper.chooseEquipmentWith(BEEnchantments.EXPLODING.getEnchantment(), MinecraftClient.getInstance().player) != null) {
                MinecraftClient.getInstance().world.createExplosion(null, blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ(), 4.0F, Explosion.DestructionType.BREAK);
                this.remove();
                ci.cancel();
            }
        }
    }
}
