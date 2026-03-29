package net.livzmc.betterhandbobbing.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.livzmc.betterhandbobbing.BetterHandBobbing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.OptionsRenderState;
import org.joml.Quaternionfc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow @Final private Minecraft minecraft;

    @Redirect(method = "renderItemInHand", at = @At(value = "FIELD", target = "net/minecraft/client/renderer/state/OptionsRenderState.bobView:Z"))
    public boolean getValue(OptionsRenderState instance) {
        return true;
    }

    @Redirect(method = "bobView", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
    private void redirectedTranslate(PoseStack instance, float x, float y, float z) {
        if (BetterHandBobbing.getHandBob().get()) {
            instance.translate(x, y, z);
        }
    }

    @Redirect(method = "bobView", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionfc;)V"))
    private void redirectedMultiply(PoseStack instance, Quaternionfc quaternion) {
        if (this.minecraft.options.bobView().get()) {
            instance.mulPose(quaternion);
        }
    }
}
