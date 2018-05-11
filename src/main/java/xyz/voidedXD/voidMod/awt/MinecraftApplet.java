package xyz.voidedXD.voidMod.awt;

import net.minecraft.client.Minecraft;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import java.applet.Applet;
import java.awt.*;

public class MinecraftApplet extends Applet {
    private Canvas mcCanvas;

    public void startLWJGL() {
        System.out.println("startLWJGL");
        try {
            Display.setParent(mcCanvas);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public void stopLWJGL() {
        System.out.println("stopLWJGL");
    }

    public void destroy() {
        remove(mcCanvas);
        super.destroy();
    }

    public void init() {
        System.out.println("init");
        this.mcCanvas = new CanvasMinecraftApplet(this);
        setLayout(new BorderLayout());
        add(mcCanvas, "Center");
        mcCanvas.setFocusable(true);
        mcCanvas.setFocusTraversalKeysEnabled(false);
        setVisible(true);
        validate();
    }
}
