package xyz.voidedXD.voidMod.mixins;

import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.WorldServerDemo;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(GuiMainMenu.class)
public abstract class MixinGuiMainMenu extends GuiScreen {
    @Shadow @Final @Mutable private float minceraftRoll;

    @Shadow private GuiButton realmsButton;

    @Shadow protected abstract void switchToRealms();

    @Inject(method = "drawScreen(IIF)V", at = @At("HEAD"))
    private void onUpdateScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        minceraftRoll = 0;
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    protected void actionPerformed(GuiButton button, CallbackInfo ci)
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        if (button.id == 5)
        {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }

        if (button.id == 1)
        {
            this.mc.displayGuiScreen(new GuiWorldSelection(this));
        }

        if (button.id == 2)
        {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (button.id == 14 && this.realmsButton.visible)
        {
            this.switchToRealms();
        }

        if (button.id == 4)
        {
//            this.mc.shutdown();
            this.mc.shutdownMinecraftApplet();
        }

        if (button.id == 11)
        {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", WorldServerDemo.DEMO_WORLD_SETTINGS);
        }

        if (button.id == 12)
        {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

            if (worldinfo != null)
            {
                this.mc.displayGuiScreen(new GuiYesNo(this, I18n.format("selectWorld.deleteQuestion"), "'" + worldinfo.getWorldName() + "' " + I18n.format("selectWorld.deleteWarning"), I18n.format("selectWorld.deleteButton"), I18n.format("gui.cancel"), 12));
            }
        }

        ci.cancel();
    }
}
