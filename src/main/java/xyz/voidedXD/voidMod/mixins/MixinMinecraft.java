package xyz.voidedXD.voidMod.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMemoryErrorScreen;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.crash.CrashReport;
import net.minecraft.init.Bootstrap;
import net.minecraft.profiler.ISnooperInfo;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.MinecraftError;
import net.minecraft.util.ReportedException;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.voidedXD.voidMod.awt.InfoPanel;
import xyz.voidedXD.voidMod.awt.MinecraftApplet;
import xyz.voidedXD.voidMod.awt.MinecraftFrame;
import xyz.voidedXD.voidMod.awt.PanelCrashReport;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements IThreadListener, ISnooperInfo {

    @Shadow @Final private static Logger LOGGER;

    @Shadow public abstract void displayGuiScreen(@Nullable GuiScreen guiScreenIn);

    @Shadow public abstract void freeMemory();

    @Shadow public abstract CrashReport addGraphicsAndWorldToCrashReport(CrashReport theCrash);

    @Shadow private CrashReport crashReporter;

    @Shadow protected abstract void runGameLoop() throws IOException;

    @Shadow private boolean hasCrashed;
    @Shadow private volatile boolean running;

    @Shadow protected abstract void init() throws LWJGLException, IOException;

    @Shadow public abstract void shutdownMinecraftApplet();

    public Frame frame = new MinecraftFrame();
    public MinecraftApplet mcApplet = new MinecraftApplet();

    private void createDisplay() throws LWJGLException {
        frame.setVisible(true);
        frame.removeAll();
        frame.setLayout(new BorderLayout());
//        frame.add(InfoPanel.getInstance(), BorderLayout.SOUTH);
//        frame.add(mcApplet, BorderLayout.CENTER);
        frame.add(mcApplet);
        frame.validate();
        mcApplet.init();
    }

    public void run()
    {
        this.running = true;

        try
        {
            this.init();
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Initializing game");
            crashreport.makeCategory("Initialization");
            this.displayCrashReport(this.addGraphicsAndWorldToCrashReport(crashreport));
            return;
        }

        while (true)
        {
            try
            {
                while (this.running)
                {
                    if (!this.hasCrashed || this.crashReporter == null)
                    {
                        try
                        {
                            this.runGameLoop();
                        }
                        catch (OutOfMemoryError var10)
                        {
                            this.freeMemory();
                            this.displayGuiScreen(new GuiMemoryErrorScreen());
                            System.gc();
                        }
                    }
                    else
                    {
                        this.displayCrashReport(this.crashReporter);
                    }
                }
            }
            catch (MinecraftError var12)
            {
                break;
            }
            catch (ReportedException reportedexception)
            {
                this.addGraphicsAndWorldToCrashReport(reportedexception.getCrashReport());
                this.freeMemory();
                LOGGER.fatal("Reported exception thrown!", (Throwable)reportedexception);
                this.displayCrashReport(reportedexception.getCrashReport());
                break;
            }
            catch (Throwable throwable1)
            {
                CrashReport crashreport1 = this.addGraphicsAndWorldToCrashReport(new CrashReport("Unexpected error", throwable1));
                this.freeMemory();
                LOGGER.fatal("Unreported exception thrown!", throwable1);
                this.displayCrashReport(crashreport1);
                break;
            }
            finally
            {
//                if(!this.hasCrashed || this.crashReporter == null) {
//                    this.shutdownMinecraftApplet();
//                }
            }

            return;
        }
    }

    public void displayCrashReport(CrashReport crashReportIn) {
        File file1 = new File(Minecraft.getMinecraft().mcDataDir, "crash-reports");
        File file2 = new File(file1, "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-client.txt");
        Bootstrap.printToSYSOUT(crashReportIn.getCompleteReport());
        if(crashReportIn.getFile() != null) {

        } else if(crashReportIn.saveToFile(file2)) {

        }

        Display.destroy();

        frame.removeAll();
        frame.setLayout(new BorderLayout());
        frame.add(new PanelCrashReport(crashReportIn), "Center");
        frame.validate();

//        this.world.sendQuittingDisconnectingPacket();
//        // this.loadWorld((WorldClient) null);
//        this.displayGuiScreen(new GuiMainMenu());
//        this.displayGuiScreen(new GuiCrashReport(crashReportIn));

    }
}
