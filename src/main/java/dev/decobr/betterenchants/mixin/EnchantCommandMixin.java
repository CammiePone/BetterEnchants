package dev.decobr.betterenchants.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import dev.decobr.betterenchants.config.Configuration;
import dev.decobr.betterenchants.helpers.ItemStackHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.EnchantCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;
import java.util.Iterator;

@Environment(EnvType.CLIENT)
@Mixin(EnchantCommand.class)
public class EnchantCommandMixin {
    @Shadow
    @Final
    private static DynamicCommandExceptionType FAILED_INCOMPATIBLE_EXCEPTION;

    @Inject(method = "execute", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    private static void execute(ServerCommandSource source, Collection<? extends Entity> targets, Enchantment enchantment, int level, CallbackInfoReturnable<Integer> cir, int i, Iterator<?> var5, Entity entity, LivingEntity livingEntity, ItemStack itemStack) throws CommandSyntaxException {
        if (Configuration.INSTANCE.fixEnchantCommand) {
            if (!ItemStackHelper.hasEnchantment(enchantment, itemStack)) {
                itemStack.addEnchantment(enchantment, level);
            } else {
                throw FAILED_INCOMPATIBLE_EXCEPTION.create(itemStack.getItem().getName(itemStack).getString());
            }

            if (targets.size() == 1) {
                source.sendFeedback(new TranslatableText("commands.enchant.success.single", enchantment.getName(level), targets.iterator().next().getDisplayName()), true);
            } else {
                source.sendFeedback(new TranslatableText("commands.enchant.success.multiple", enchantment.getName(level), targets.size()), true);
            }

            cir.setReturnValue(0);
        }
    }
}
