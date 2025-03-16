package net.livzmc.betterhandbobbing.mixin.sodium;

import com.google.common.collect.ImmutableList;
import net.caffeinemc.mods.sodium.client.gui.SodiumGameOptionPages;
import net.caffeinemc.mods.sodium.client.gui.options.Option;
import net.caffeinemc.mods.sodium.client.gui.options.OptionGroup;
import net.caffeinemc.mods.sodium.client.gui.options.OptionImpl;
import net.caffeinemc.mods.sodium.client.gui.options.OptionPage;
import net.caffeinemc.mods.sodium.client.gui.options.binding.compat.VanillaBooleanOptionBinding;
import net.caffeinemc.mods.sodium.client.gui.options.control.TickBoxControl;
import net.caffeinemc.mods.sodium.client.gui.options.storage.MinecraftOptionsStorage;
import net.fabricmc.loader.api.FabricLoader;
import net.livzmc.betterhandbobbing.BetterHandBobbing;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(SodiumGameOptionPages.class)
public class UserConfigCategoriesMixin {
    private static final Boolean hasOtherGroup = FabricLoader.getInstance().isModLoaded("reeses-sodium-options") && FabricLoader.getInstance().isModLoaded("sodium-extra");
    @Shadow @Final private static MinecraftOptionsStorage vanillaOpts;

    @Inject(method = "general", at = @At("RETURN"), remap = false, cancellable = true)
    private static void bhb$addOption(CallbackInfoReturnable<OptionPage> cir) {
        List<OptionGroup> groups = new ArrayList<>(cir.getReturnValue().getGroups());
        OptionGroup group = groups.get(hasOtherGroup ? 3 : 2);
        List<Option<?>> options = new ArrayList<>(group.getOptions());
        options.add(1, OptionImpl.createBuilder(Boolean.TYPE, vanillaOpts).setName(Text.translatable("betterhandbobbing.options.handbob")).setTooltip(Text.translatable("betterhandbobbing.options.handbob.tooltip")).setControl(TickBoxControl::new).setBinding(new VanillaBooleanOptionBinding(BetterHandBobbing.getHandBob())).build());
        OptionGroup.Builder builder = OptionGroup.createBuilder();
        options.forEach(builder::add);
        groups.set(hasOtherGroup ? 3 : 2, builder.build());
        cir.setReturnValue(new OptionPage(Text.translatable("stat.generalButton"), ImmutableList.copyOf(groups)));
    }
}
