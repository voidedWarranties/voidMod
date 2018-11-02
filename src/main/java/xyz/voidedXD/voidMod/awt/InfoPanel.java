package xyz.voidedXD.voidMod.awt;

import java.awt.*;

public class InfoPanel extends Panel {

    public static InfoPanel instance = new InfoPanel();

    public InfoPanel() {
        this.setBackground(new Color(3028036));
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(854, 100));
    }

    public static InfoPanel getInstance() {
        return instance;
    }
}
