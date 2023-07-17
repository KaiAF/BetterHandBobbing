package net.livzmc.betterhandbobbing;

import net.fabricmc.loader.FabricLoader;
import org.spongepowered.asm.mixin.Mixins;

public class RunnableLaunchWithOptiFine implements Runnable {
    @Override
    public void run() {
        if (FabricLoader.INSTANCE.isModLoaded("optifabric")) {
            Mixins.addConfiguration("betterhandbobbing.mixins.compat.optifine.json");
        }
    }
}
