package net.livzmc.betterhandbobbing;

import net.fabricmc.api.ModInitializer;
import net.livzmc.betterhandbobbing.util.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class BetterHandBobbing implements ModInitializer {
    public static final Config config = new Config();
    public static final SimpleOption<Boolean> handBob = SimpleOption.ofBoolean("betterhandbobbing.options.handbob", config.getHandBob(), (bool) -> {
        config.setHandBob(bool);
        config.save();
    });

    @Override
    public void onInitialize() {
    }

    public static SimpleOption<Boolean> getHandBob() {
        return handBob;
    }

    public static void handView(MatrixStack matrices, float tickDelta, MinecraftClient client) {
        if (client.getCameraEntity() instanceof PlayerEntity playerEntity) {
            float f = playerEntity.horizontalSpeed - playerEntity.prevHorizontalSpeed;
            float g = -(playerEntity.horizontalSpeed + f * tickDelta);
            float h = MathHelper.lerp(tickDelta, playerEntity.prevStrideDistance, playerEntity.strideDistance);
            if (client.options.getPerspective().isFirstPerson()) {
                if (BetterHandBobbing.getHandBob().getValue()) {
                    matrices.translate(MathHelper.sin(g * 3.1415927F) * h * 0.5F, -Math.abs(MathHelper.cos(g * 3.1415927F) * h), 0.0);
                }
            }
        }
    }
}