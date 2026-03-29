package net.livzmc.betterhandbobbing.mixin;

import net.livzmc.betterhandbobbing.BetterHandBobbing;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.options.AccessibilityOptionsScreen;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AccessibilityOptionsScreen.class)
public class AccessibilityOptionsScreenMixin {
    @Inject(method = "options", at = @At("RETURN"), cancellable = true)
    private static void bhb$getOptions(Options gameOptions, CallbackInfoReturnable<OptionInstance<?>[]> cir) {
        OptionInstance<?>[] values = cir.getReturnValue();
        cir.setReturnValue(ArrayUtils.insert(9, values, BetterHandBobbing.handBob));
    }
}
