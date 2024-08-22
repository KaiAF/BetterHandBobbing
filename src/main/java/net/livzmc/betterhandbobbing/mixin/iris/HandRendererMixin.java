package net.livzmc.betterhandbobbing.mixin.iris;

import net.irisshaders.iris.pathways.HandRenderer;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HandRenderer.class)
public class HandRendererMixin {
  @Redirect(method = "setupGlState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/SimpleOption;getValue()Ljava/lang/Object;"))
  public Object getValue(SimpleOption instance) {
	return true;
  }
}
