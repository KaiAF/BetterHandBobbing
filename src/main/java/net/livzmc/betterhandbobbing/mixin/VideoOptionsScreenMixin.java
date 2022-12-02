package net.livzmc.betterhandbobbing.mixin;

import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.livzmc.betterhandbobbing.BetterHandBobbing.handBob;

@Mixin(VideoOptionsScreen.class)
public class VideoOptionsScreenMixin {
    @Inject(method = "getOptions", at = @At("RETURN"), cancellable = true)
    private static void bhb$getOptions(GameOptions gameOptions, CallbackInfoReturnable<SimpleOption<?>[]> cir) {
        SimpleOption<?>[] values = cir.getReturnValue();
        cir.setReturnValue(ArrayUtils.insert(9, values, handBob));
    }
}
