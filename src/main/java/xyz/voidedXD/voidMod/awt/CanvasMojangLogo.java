package xyz.voidedXD.voidMod.awt;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

public class CanvasMojangLogo extends Canvas {
    private BufferedImage logo;

    public CanvasMojangLogo() {
        try {
            ResourceLocation loc = new ResourceLocation("textures/gui/crash_logo.png");
            // this.logo = ImageIO.read(PanelCrashReport.class.getResource("textures/gui/title/mojang.png"));
            this.logo = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(loc).getInputStream());
        } catch (IOException e) {

        }

        byte size = 100;
        this.setPreferredSize(new Dimension(size, size));
        this.setMinimumSize(new Dimension(size, size));
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.drawImage(this.logo, this.getWidth() / 2 - this.logo.getWidth() / 2, 32, (ImageObserver) null);
    }
}
