package net.livzmc.betterhandbobbing.mixin.iris;

import net.irisshaders.iris.pathways.HandRenderer;
import net.livzmc.betterhandbobbing.BetterHandBobbing;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandRenderer.class)
public class HandRendererMixin {
    @Inject(method = "setupGlState", at = @At("RETURN"))
    private void setupGLState$HandBobbing(GameRenderer gameRenderer, Camera camera, Matrix4fc modelMatrix, float tickDelta, CallbackInfoReturnable<MatrixStack> cir) {
        if (BetterHandBobbing.getHandBob().getValue()) {
            BetterHandBobbing.handBob(cir.getReturnValue(), tickDelta, MinecraftClient.getInstance());
        }
    }
}
