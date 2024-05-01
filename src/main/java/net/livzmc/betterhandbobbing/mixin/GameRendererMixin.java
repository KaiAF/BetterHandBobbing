package net.livzmc.betterhandbobbing.mixin;

import net.livzmc.betterhandbobbing.BetterHandBobbing;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow @Final private MinecraftClient client;

    @Redirect(method = "bobView", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"))
    private void redirectTranslate$bhb(MatrixStack instance, float x, float y, float z) {
        // only translate if view bobbing and hand bobbing are enabled
        if (this.client.options.getBobView().getValue() && BetterHandBobbing.getHandBob().getValue()) {
            instance.translate(x, y, z);
        }
    }

    @Inject(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;getBobView()Lnet/minecraft/client/option/SimpleOption;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    private void renderHand$bhb(Camera camera, float tickDelta, Matrix4f matrix4f, CallbackInfo ci, MatrixStack matrices) {
        // use custom method only if view bobbing is off.
        // do this to prevent it from making the screen bob doubled
        if (!this.client.options.getBobView().getValue()) {
            BetterHandBobbing.handBob(matrices, tickDelta, this.client);
        }
    }
}
