package xyz.voidedXD.voidMod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.crash.CrashReport;

import java.io.IOException;

public class GuiCrashReport extends GuiScreen {

    private CrashReport crashReport;

    public GuiCrashReport(CrashReport crashReportIn) {
        this.crashReport = crashReportIn;
    }

    public void initGui() {
        this.buttonList.add(new GuiButton(0, 5 + fontRenderer.getStringWidth("Saved To: " + crashReport.getFile()), 30, fontRenderer.getStringWidth("Open Crash Report") + 10, 20, "Open Crash Report"));
    }

    public void onGuiClosed() {
        Minecraft.getMinecraft().shutdownMinecraftApplet();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawRect(0, 0, this.width, this.height, 0xFF3366FF);
        this.drawCenteredString(this.fontRenderer, ":( Minecraft has Crashed!", this.width / 2, 5, 0xFFFFFFFF);
        this.drawString(this.fontRenderer, "Cause: " + crashReport.getCrashCause().toString(), 5, 20, 0xFFFFFFFF);
        this.drawString(this.fontRenderer, "Saved To: " + crashReport.getFile(), 5, 40, 0xFFFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id == 0) {
            OpenGlHelper.openFile(crashReport.getFile());
        }
    }
}
