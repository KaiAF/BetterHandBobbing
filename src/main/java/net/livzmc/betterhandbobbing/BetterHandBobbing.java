package net.livzmc.betterhandbobbing;

import net.fabricmc.api.ModInitializer;
import net.livzmc.betterhandbobbing.util.Config;
import net.minecraft.client.option.SimpleOption;

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
}