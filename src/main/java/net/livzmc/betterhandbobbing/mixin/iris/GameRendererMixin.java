package net.livzmc.betterhandbobbing.mixin.iris;

import com.mojang.blaze3d.vertex.PoseStack;
import net.irisshaders.iris.api.v0.IrisApi;
import net.livzmc.betterhandbobbing.BetterHandBobbing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
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
    @Shadow @Final private Minecraft minecraft;

    @Redirect(method = "bobView", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
    private void redirectTranslate$bhb(PoseStack instance, float x, float y, float z) {
        // only translate if view bobbing and hand bobbing are enabled
        if (this.minecraft.options.bobView().get() && BetterHandBobbing.getHandBob().get()) {
            instance.translate(x, y, z);
        }
    }

    @Inject(method = "renderItemInHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options;bobView()Lnet/minecraft/client/OptionInstance;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    private void renderHand$bhb(Camera camera, float tickDelta, Matrix4f matrix4f, CallbackInfo ci, PoseStack matrices) {
        // use custom method only if view bobbing is off.
        // do this to prevent it from making the screen bob doubled
        if (!IrisApi.getInstance().isShaderPackInUse() && !this.minecraft.options.bobView().get()) {
            BetterHandBobbing.handBob(matrices, tickDelta, this.minecraft);
        }
    }
}
