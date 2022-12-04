package net.livzmc.betterhandbobbing.mixin.iris;

import net.irisshaders.iris.api.v0.IrisApi;
import net.livzmc.betterhandbobbing.BetterHandBobbing;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "bobView", cancellable = true)
    private void bhb$bobView(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (!IrisApi.getInstance().isShaderPackInUse()) {
            if (this.client.getCameraEntity() instanceof PlayerEntity playerEntity) {
                float f = playerEntity.horizontalSpeed - playerEntity.prevHorizontalSpeed;
                float g = -(playerEntity.horizontalSpeed + f * tickDelta);
                float h = MathHelper.lerp(tickDelta, playerEntity.prevStrideDistance, playerEntity.strideDistance);
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.sin(g * 3.1415927F) * h * 3.0F));
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(Math.abs(MathHelper.cos(g * 3.1415927F - 0.2F) * h) * 5.0F));
            }
        }
        ci.cancel();
    }

    private void handView(MatrixStack matrices, float tickDelta) {
        if (this.client.getCameraEntity() instanceof PlayerEntity playerEntity) {
            float f = playerEntity.horizontalSpeed - playerEntity.prevHorizontalSpeed;
            float g = -(playerEntity.horizontalSpeed + f * tickDelta);
            float h = MathHelper.lerp(tickDelta, playerEntity.prevStrideDistance, playerEntity.strideDistance);
            if (this.client.options.getPerspective().isFirstPerson()) {
                if (BetterHandBobbing.getHandBob().getValue()) {
                    matrices.translate(MathHelper.sin(g * 3.1415927F) * h * 0.5F, -Math.abs(MathHelper.cos(g * 3.1415927F) * h), 0.0);
                }
            }
        }
    }

    @Inject(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;getBobView()Lnet/minecraft/client/option/SimpleOption;", shift = At.Shift.AFTER))
    private void inject(MatrixStack matrices, Camera camera, float tickDelta, CallbackInfo ci) {
        if (!IrisApi.getInstance().isShaderPackInUse()) {
            if (BetterHandBobbing.getHandBob().getValue()) {
                this.handView(matrices, tickDelta);
            }
        }
    }
}
