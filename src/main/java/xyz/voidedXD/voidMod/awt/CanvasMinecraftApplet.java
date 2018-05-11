package xyz.voidedXD.voidMod.awt;

import java.awt.*;

public class CanvasMinecraftApplet extends Canvas {
    final MinecraftApplet mcApplet;

    public CanvasMinecraftApplet(MinecraftApplet minecraft) {
        this.mcApplet = minecraft;
    }

    public final void addNotify() {
        super.addNotify();
        this.mcApplet.startLWJGL();
    }

    public final void removeNotify() {
        this.mcApplet.stopLWJGL();
        super.removeNotify();
    }

    public boolean isDisplayable() {
        return true;
    }
}
