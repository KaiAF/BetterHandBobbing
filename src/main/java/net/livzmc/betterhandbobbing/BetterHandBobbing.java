package net.livzmc.betterhandbobbing;

import net.fabricmc.api.ModInitializer;
import net.livzmc.betterhandbobbing.util.Config;
import net.minecraft.client.OptionInstance;

public class BetterHandBobbing implements ModInitializer {
    public static final Config config = new Config();
    public static final OptionInstance<Boolean> handBob = OptionInstance.createBoolean("betterhandbobbing.options.handbob", config.getHandBob(), (bool) -> {
        config.setHandBob(bool);
        config.save();
    });

    @Override
    public void onInitialize() {
    }

    public static OptionInstance<Boolean> getHandBob() {
        return handBob;
    }
}