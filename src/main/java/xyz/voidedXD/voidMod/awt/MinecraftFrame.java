package xyz.voidedXD.voidMod.awt;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MinecraftFrame extends Frame {
    public MinecraftFrame() {
        setLayout(new BorderLayout());
        setTitle("Minecraft");
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(854, 480));
        pack();
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("windowClosing");
                System.exit(0);
            }
        });
    }
}
