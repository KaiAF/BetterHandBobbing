package net.livzmc.betterhandbobbing;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.livzmc.betterhandbobbing.util.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BetterHandBobbing implements ModInitializer {
    public static final Config config = new Config();
    public static final Logger LOGGER = LogManager.getLogger("BetterHandBobbing");
    public static final OptionInstance<Boolean> handBob = OptionInstance.createBoolean("betterhandbobbing.options.handbob", config.getHandBob(), (bool) -> {
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

    public static OptionInstance<Boolean> getHandBob() {
        return handBob;
    }

    // Only use custom method if minecraft's view bobbing setting is disabled and my hand bobbing setting is enabled
    public static void handBob(PoseStack matrices, float tickDelta, Minecraft client) {
        if (client.getCameraEntity() instanceof Player playerEntity) {
            if (client.options.getCameraType().isFirstPerson() && BetterHandBobbing.getHandBob().get()) {
                float f = playerEntity.walkDist - playerEntity.walkDistO;
                float g = -(playerEntity.walkDist + f * tickDelta);
                float h = Mth.lerp(tickDelta, playerEntity.oBob, playerEntity.bob);
                float pi = 3.1415927F;
                matrices.translate(Mth.sin(g * pi) * h * 0.5F, -Math.abs(Mth.cos(g * pi) * h), 0.0F);
            }
        }
    }
}