package net.livzmc.betterhandbobbing.modmenu;

import net.livzmc.betterhandbobbing.BetterHandBobbing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class BHBModMenuScreen extends OptionsSubScreen {
    private final Screen parent;
    private static final Component TITLE = Component.literal("BetterHandBobbing Options");
    @Nullable
    private OptionsList list;

    public BHBModMenuScreen(Screen parent) {
        super(parent, Minecraft.getInstance().options, TITLE);
        this.parent = parent;
    }

    private static OptionInstance<?>[] options() {
        return new OptionInstance[]{BetterHandBobbing.getHandBob()};
    }

    @Override
    protected void init() {
        this.list = this.addRenderableWidget(new OptionsList(this.minecraft, this.width, this.height, this));
        this.list.addSmall(options());
        super.init();
    }

    @Override
    protected void repositionElements() {
        super.repositionElements();
        if (this.list != null) {
            this.list.updateSize(this.width, this.layout);
        }
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }
}
