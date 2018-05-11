package xyz.voidedXD.voidMod.mixins;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public abstract class MixinGuiMainMenu extends GuiScreen {
    @Shadow @Final @Mutable private float minceraftRoll;

    @Inject(method = "drawScreen(IIF)V", at = @At("HEAD"))
    private void onUpdateScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        minceraftRoll = 0;
    }
}
