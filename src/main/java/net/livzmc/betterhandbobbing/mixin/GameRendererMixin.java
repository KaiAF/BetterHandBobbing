package net.livzmc.betterhandbobbing.mixin;

import net.livzmc.betterhandbobbing.BetterHandBobbing;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow @Final private MinecraftClient client;

    @Redirect(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/SimpleOption;getValue()Ljava/lang/Object;"))
    public Object getValue(SimpleOption instance) {
        return true;
    }

    @Redirect(method = "bobView", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"))
    private void redirectedTranslate(MatrixStack instance, float x, float y, float z) {
        if (BetterHandBobbing.getHandBob().getValue()) {
            instance.translate(x, y, z);
        }
    }

    @Redirect(method = "bobView", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lorg/joml/Quaternionf;)V"))
    private void redirectedMultiply(MatrixStack instance, Quaternionf quaternion) {
        if (this.client.options.getBobView().getValue()) {
            instance.multiply(quaternion);
        }
    }
}
