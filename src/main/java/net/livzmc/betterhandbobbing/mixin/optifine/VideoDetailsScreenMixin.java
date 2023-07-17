package net.livzmc.betterhandbobbing.mixin.optifine;

import net.livzmc.betterhandbobbing.BetterHandBobbing;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import org.apache.commons.lang3.ArrayUtils;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.optifine.gui.GuiDetailSettingsOF", remap = false)
public class VideoDetailsScreenMixin extends Screen {
    private Screen prevScreen;

    protected VideoDetailsScreenMixin(Text title) {
        super(title);
    }

    @ModifyVariable(method = "<init>", at = @At(value = "FIELD",target = "prevScreen", opcode = Opcodes.PUTFIELD))
    private Screen bhb$storeScreen(Screen screen) {
        prevScreen = screen;
        return screen;
    }

    @ModifyVariable(method = "method_25426", at = @At(value = "STORE", ordinal = 0))
    private SimpleOption[] bhb$AddSettingToDetailsScreen(SimpleOption[] simpleOption) {
        return ArrayUtils.insert(11, simpleOption, BetterHandBobbing.handBob);
    }

    @Inject(method = "method_25426", at = @At(value = "INVOKE", target = "Lnet/optifine/gui/GuiDetailSettingsOF;method_37063(Lnet/minecraft/class_364;)Lnet/minecraft/class_364;", shift = At.Shift.BEFORE, ordinal = 1), cancellable = true)
    private void bhb$createDoneButton(CallbackInfo ci) {
        ButtonWidget.Builder b = new ButtonWidget.Builder(Text.literal("Done"), (t) -> {
            MinecraftClient.getInstance().setScreen(this.prevScreen);
        });
        b.position(this.width / 2 - 155 + 19 % 2 * 160, this.height / 6 + 21 * (19 / 2) - 12);
        b.size(150, 20);
        this.addDrawableChild(b.build());
        ci.cancel();
    }
}
