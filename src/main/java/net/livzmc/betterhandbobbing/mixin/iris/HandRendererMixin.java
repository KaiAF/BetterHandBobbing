package net.livzmc.betterhandbobbing.mixin.iris;

import com.mojang.blaze3d.vertex.PoseStack;
import net.irisshaders.iris.pathways.HandRenderer;
import net.livzmc.betterhandbobbing.BetterHandBobbing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandRenderer.class)
public class HandRendererMixin {
    @Inject(method = "setupGlState", at = @At("RETURN"))
    private void setupGLState$HandBobbing(GameRenderer gameRenderer, Camera camera, Matrix4fc modelMatrix, float tickDelta, CallbackInfoReturnable<PoseStack> cir) {
        if (BetterHandBobbing.getHandBob().get()) {
            BetterHandBobbing.handBob(cir.getReturnValue(), tickDelta, Minecraft.getInstance());
        }
    }
}
