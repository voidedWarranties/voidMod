package xyz.voidedXD.voidMod.awt;

import net.minecraft.crash.CrashReport;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PanelCrashReport extends Panel {
    public PanelCrashReport(CrashReport crashReport) {
        this.setBackground(new Color(3028036));
        this.setLayout(new BorderLayout());
        String report = crashReport.getCompleteReport();
        StringBuilder builder = new StringBuilder();
        crashReport.getCategory().appendToStringBuilder(builder);
        String preReport =
                        "      Minecraft has crashed!      \n" +
                        "      ----------------------      \n" +
                        "\n" +
                        "Minecraft has stopped running because it encountered a problem; " + crashReport.getDescription() +
                        "\n\n" +
                        "A full error report has been saved to " + crashReport.getFile().getAbsolutePath() + " - Please include a copy of that file (Not this screen!) if you report this crash to anyone; without it, they will not be able to help fix the crash :(" +
                        "\n\n\n\n" +
                        "--- BEGIN ERROR REPORT " + Integer.toHexString(report.hashCode()) + " --------\n" +
                        "Full report at:\n" + crashReport.getFile().getAbsolutePath() + "\nPlease show that file to Mojang, NOT just this screen!\n\n" +
                        "Generated " + (new SimpleDateFormat()).format(new Date()) + "\n\n" +
                        builder.toString() + "\n\n" +
                        crashReport.getCauseStackTraceOrString() +
                        "--- END ERROR REPORT " + Integer.toHexString(report.hashCode()) + " ----------\n" +
                        "\n\n";
        TextArea textArea = new TextArea(preReport, 0, 0, 1);
        textArea.setFont(new Font("Monospaced", 0, 12));
        this.add(new CanvasMojangLogo(), "North");
        this.add(new CanvasCrashReport(80), "East");
        this.add(new CanvasCrashReport(80), "West");
        this.add(new CanvasCrashReport(100), "South");
        this.add(textArea, "Center");
    }
}
