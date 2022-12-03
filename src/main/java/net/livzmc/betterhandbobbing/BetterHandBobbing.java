package net.livzmc.betterhandbobbing;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.option.SimpleOption;

public class BetterHandBobbing implements ModInitializer {
    public static final SimpleOption<Boolean> handBob = SimpleOption.ofBoolean("betterhandbobbing.options.handbob", true);

    @Override
    public void onInitialize() {
    }

    public static SimpleOption<Boolean> getHandBob() {
        return handBob;
    }
}