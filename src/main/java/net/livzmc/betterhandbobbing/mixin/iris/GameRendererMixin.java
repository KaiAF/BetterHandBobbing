package net.livzmc.betterhandbobbing.mixin.iris;

import net.irisshaders.iris.api.v0.IrisApi;
import net.livzmc.betterhandbobbing.BetterHandBobbing;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow @Final private MinecraftClient client;

    @Redirect(method = "bobView", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"))
    private void bhb$redirectTranslate(MatrixStack instance, float x, float y, float z) {
        // only translate if view bobbing and hand bobbing are enabled
        if (this.client.options.getBobView().getValue() && BetterHandBobbing.getHandBob().getValue()) {
            instance.translate(x, y, z);
        }
    }

    @Inject(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;getBobView()Lnet/minecraft/client/option/SimpleOption;", shift = At.Shift.AFTER))
    private void inject(MatrixStack matrices, Camera camera, float tickDelta, CallbackInfo ci) {
        // use custom method only if view bobbing is off.
        // do this to prevent it from making the screen bob doubled
        if (!IrisApi.getInstance().isShaderPackInUse() && !this.client.options.getBobView().getValue()) {
            BetterHandBobbing.handBob(matrices, tickDelta, client);
        }
    }
}
