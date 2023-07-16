package net.livzmc.betterhandbobbing.mixin.iris;

import net.coderbot.iris.pipeline.HandRenderer;
import net.livzmc.betterhandbobbing.BetterHandBobbing;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandRenderer.class)
public class HandRendererMixin {
    @Inject(method = "setupGlState", at = @At("TAIL"))
    private void setupGLState$HandBobbing(GameRenderer gameRenderer, Camera camera, MatrixStack poseStack, float tickDelta, CallbackInfo ci) {
        if (BetterHandBobbing.getHandBob().getValue()) {
            BetterHandBobbing.handBob(poseStack, tickDelta, MinecraftClient.getInstance());
        }
    }
}
