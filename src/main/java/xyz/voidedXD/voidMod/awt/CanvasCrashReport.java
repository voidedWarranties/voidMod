package xyz.voidedXD.voidMod.awt;

import java.awt.*;

public class CanvasCrashReport extends Canvas {
    public CanvasCrashReport(int size) {
        this.setPreferredSize(new Dimension(size, size));
        this.setMinimumSize(new Dimension(size, size));
    }
}
