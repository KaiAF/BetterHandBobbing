package net.livzmc.betterhandbobbing.mixin.optifine;

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

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow @Final private MinecraftClient client;

    @Redirect(method = "method_3186", at = @At(value = "INVOKE", target = "Lnet/minecraft/class_4587;method_46416(FFF)V"))
    private void bhb$redirectTranslate(MatrixStack instance, float x, float y, float z) {
        // only translate if view bobbing and hand bobbing are enabled
        if (this.client.options.getBobView().getValue() && BetterHandBobbing.getHandBob().getValue()) {
            instance.translate(x, y, z);
        }
    }

    @Inject(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/class_315;method_42448()Lnet/minecraft/class_7172;", shift = At.Shift.AFTER), remap = false)
    private void inject(Camera camera, float tickDelta, Matrix4f matrix4f, boolean renderItem, boolean renderOverlay, boolean renderTranslucent, CallbackInfo ci, MatrixStack matrices) {
        BetterHandBobbing.handBob(matrices, tickDelta, client);
    }
}
