package net.livzmc.betterhandbobbing;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.livzmc.betterhandbobbing.util.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BetterHandBobbing implements ModInitializer {
    public static final Config config = new Config();
    public static final Logger LOGGER = LogManager.getLogger("BetterHandBobbing");
    public static final SimpleOption<Boolean> handBob = SimpleOption.ofBoolean("betterhandbobbing.options.handbob", config.getHandBob(), (bool) -> {
        config.setHandBob(bool);
        config.save();
    });

    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().isModLoaded("quilt_loader")) {
            LOGGER.info("[BetterHandBobbing] Using QuiltMC");
            if (!FabricLoader.getInstance().isModLoaded("quilted_fabric_api")) {
                LOGGER.warn("[BetterHandBobbing] Quilted Fabric API was not found! It is suggested to use this so that the language files for my settings will work.");
            }
        }
    }

    public static SimpleOption<Boolean> getHandBob() {
        return handBob;
    }

    // Only use custom method if minecraft's view bobbing setting is disabled and my hand bobbing setting is enabled
    public static void handBob(MatrixStack matrices, float tickDelta, MinecraftClient client) {
        if (client.getCameraEntity() instanceof PlayerEntity playerEntity) {
            if (client.options.getPerspective().isFirstPerson() && BetterHandBobbing.getHandBob().getValue()) {
                float f = playerEntity.horizontalSpeed - playerEntity.prevHorizontalSpeed;
                float g = -(playerEntity.horizontalSpeed + f * tickDelta);
                float h = MathHelper.lerp(tickDelta, playerEntity.prevStrideDistance, playerEntity.strideDistance);
                float pi = (float)Math.PI;
                matrices.translate(MathHelper.sin(g * pi) * h * 0.5F, -Math.abs(MathHelper.cos(g * pi) * h), 0.0);
            }
        }
    }
}