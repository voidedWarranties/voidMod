package xyz.voidedXD.voidMod;

import com.mumfrey.liteloader.LiteMod;
import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.core.LiteLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.crash.CrashReport;
import org.lwjgl.input.Keyboard;
import xyz.voidedXD.voidMod.exceptions.ScrewYouException;

import java.awt.*;
import java.io.File;

public class LiteModVoidMod implements LiteMod, Tickable {

    private static KeyBinding testKeyBinding = new KeyBinding("key.test.activate", Keyboard.KEY_DOWN, "key.categories.voidMod");

    private int ticks = 0;
    /**
     * Default constructor. All LiteMods must have a default constructor. In general you should do very little
     * in the mod constructor EXCEPT for initialising any non-game-interfacing components or performing
     * sanity checking prior to initialisation
     */
    public LiteModVoidMod() {
    }

    /**
     * getName() should be used to return the display name of your mod and MUST NOT return null
     *
     * @see com.mumfrey.liteloader.LiteMod#getName()
     */
    @Override
    public String getName() {
        return "voidMod";
    }

    /**
     * getVersion() should return the same version string present in the mod metadata, although this is
     * not a strict requirement.
     *
     * @see com.mumfrey.liteloader.LiteMod#getVersion()
     */
    @Override
    public String getVersion() {
        return "1.0-SNAPSHOT";
    }

    /**
     * init() is called very early in the initialisation cycle, before the game is fully initialised, this
     * means that it is important that your mod does not interact with the game in any way at this point.
     *
     * @see com.mumfrey.liteloader.LiteMod#init(File)
     */
    @Override
    public void init(File configPath) {
        LiteLoader.getInput().registerKeyBinding(LiteModVoidMod.testKeyBinding);
    }

    /**
     * upgradeSettings is used to notify a mod that its version-specific settings are being migrated
     *
     * @see com.mumfrey.liteloader.LiteMod#upgradeSettings(String, File, File)
     */
    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath) {
    }

    @Override
    public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock) {


        if(LiteModVoidMod.testKeyBinding.isKeyDown() && inGame) {
//            float sine = 0.5f * ((float) Math.sin(Math.toRadians(4.0f * ((float) ticks + minecraft.getRenderPartialTicks()))) + 1.0f);
            float frequency = 0.0125f;
            double red = Math.sin(frequency * ticks + 0) * 127 + 128;
            double green = Math.sin(frequency * ticks + 2) * 127 + 128;
            double blue = Math.sin(frequency * ticks + 4) * 127 + 128;

            int r = ((int) Math.round(red)) & 0xFF;
            int g = ((int) Math.round(green)) & 0xFF;
            int b = ((int) Math.round(blue)) & 0xFF;
            int a = 50;

            Color color = new Color(r, g, b);
            Color tColor = new Color(r, g, b, a);

            int posX = 100;
            int posY = 100;
            FontRenderer fr = minecraft.fontRenderer;
            int colorTranslucent = tColor.getRGB();
            String text = "voided";
            fr.drawString(text, posX, posY, color.getRGB());
            fr.drawString(text, posX + 1, posY, colorTranslucent);
            fr.drawString(text, posX + 1, posY + 1, colorTranslucent);
            fr.drawString(text, posX + 1, posY - 1, colorTranslucent);
            fr.drawString(text, posX - 1, posY, colorTranslucent);
            fr.drawString(text, posX - 1, posY + 1, colorTranslucent);
            fr.drawString(text, posX - 1, posY - 1, colorTranslucent);
            fr.drawString(text, posX, posY + 1, colorTranslucent);
            fr.drawString(text, posX, posY - 1, colorTranslucent);
            ticks++;

//            minecraft.displayCrashReport(new CrashReport("\n" +
//                    "                                                                      \n" +
//                    "                                                                      \n" +
//                    "                                                                      \n" +
//                    "                                                                      \n" +
//                    "                                  `````                               \n" +
//                    "                                 ``````.```                           \n" +
//                    "                                ``      ``.```                        \n" +
//                    "                                ``  #       `.````                    \n" +
//                    "                                `  ''+@#.      ``.```                 \n" +
//                    "                               ``  @.,..'@@:       `.```              \n" +
//                    "                               `   #....,..:#@'       ```             \n" +
//                    "                               .  ::..........,+@#.     `             \n" +
//                    "                              `.  @............,..;@@;  ``            \n" +
//                    "                              ``  #...............,,,+, ``            \n" +
//                    "                             `.  ',...............,,,#  ``            \n" +
//                    "                             ``  @................,,,@  .`            \n" +
//                    "                            `.  .'................,,,@  .`            \n" +
//                    "                           ``   @.................,,,@   `            \n" +
//                    "                          ``   +;...................,';  .            \n" +
//                    "                         ``   :@....................,,@  ``           \n" +
//                    "                         .`  ,@......................,#` ``           \n" +
//                    "                        ``  ,@.......................,:+  `           \n" +
//                    "                       ``  `@.........................,@  .           \n" +
//                    "                      `.   @..........................,@  ``          \n" +
//                    "                     ``   @,..........................,@  .`          \n" +
//                    "                     `   @,............................@  .`          \n" +
//                    "                    ``  +;...,.......................:.@  .`          \n" +
//                    "                   `.  `#...........................,:.@  .`          \n" +
//                    "                   ``  @.............................,+@  ``          \n" +
//                    "                  ``  +:,............................,:@  .`          \n" +
//                    "                  ``  @..............................,,@  .           \n" +
//                    "                  `` .:...............................,@  ``          \n" +
//                    "                  `` ',...............................,@  ``          \n" +
//                    "                  `  +,.................'.............,@  .`          \n" +
//                    "                  `  +,;.............,,...'...........,#  ``          \n" +
//                    "                  `` ,;.,..........,.:@,...'.,........,+` ``          \n" +
//                    "                  `` .'.+..........,.#`+@@''@.........,;, ``          \n" +
//                    "                  `` `#.,:.........,#+     .+:........,:; ``          \n" +
//                    "                 `.   +.,'.........@'        @,........:+  `          \n" +
//                    "                 ``  #+..,'.....,#@:         @.........,#  `          \n" +
//                    "                `.  ,@+..,;,,....,;@         .+.......,,@  `          \n" +
//                    "               `.   #,#...,:......,@`         @........,@  `          \n" +
//                    "               `   @,,+...,+......,,#         +:.......,@  `          \n" +
//                    "              ``  ::,@'....,,.....,,@          @.......,@  `          \n" +
//                    "             `.   #,;:'....,'.....,,@          @.......,@  `          \n" +
//                    "             ``  #,,'::.....#.....,,@#@.      ;:......,,#  `          \n" +
//                    "            ``  ,+,,,;,.....;......,,,,##     @........@`  `          \n" +
//                    "            `   @,,+,+,.....,.........,,,@##: @....,:#@;  ``          \n" +
//                    "           `.  #,,,;,#......,'.........,,':,;+@.......@   .           \n" +
//                    "           ``  @,,,,,@......,@..........,,,,,,;......@   .`           \n" +
//                    "          ``  ;.;,+,,@......,@@'..............,...',;:  ``            \n" +
//                    "          `.  @,,,#,,@......@, ,@#....,......,...,,,#  ``             \n" +
//                    "          `.  +#,;',,#......@     #@;.,.....',,#.#,,@  .`             \n" +
//                    "          `.  @,,@,,,#......#       .@#',,:'@@@@@#;@`  `              \n" +
//                    "          ``   +##,,,+......@  .``    `,;;;.     ,,   ``              \n" +
//                    "           ``   ;,,,,+......@  .````                 ```              \n" +
//                    "            .`  @,,,,+......@  `` ```.```````...````.`                \n" +
//                    "            `  .:,,,,#......@  .     ```````````````                  \n" +
//                    "           `.  @,,,,,@.....,@  `                                      \n" +
//                    "           `` `',,,,,@.....,: ``                                      \n" +
//                    "           .  .':,,,#@.....'  ``                                      \n" +
//                    "          ``  @.',,,@@.....@  .`                                      \n" +
//                    "          `.  ;,,,.# #.....@  .`                                      \n" +
//                    "          `.  ++,,', #....,+  .                                       \n" +
//                    "          ``  ++:@,  #....,' ``                                       \n" +
//                    "           `    `    #'...', ``                                       \n" +
//                    "           ``        @,...@  ``                                       \n" +
//                    "            `````..  @,.,,@  .`                                       \n" +
//                    "              ````.  @,,.#   .                                        \n" +
//                    "                 ``  :@:@   ``                                        \n" +
//                    "                  ``       ``                                         \n" +
//                    "                   ``     ``                                          \n" +
//                    "                   ```..```                                           \n" +
//                    "                      ```                                             \n" +
//                    "                                                                      \n" +
//                    "                                                                      ", new ScrewYouException()));
//            RayTraceResult trace = minecraft.player.rayTrace(100, partialTicks);
//            if(trace.typeOfHit == RayTraceResult.Type.BLOCK) {
//                Block block = minecraft.world.getBlockState(trace.getBlockPos()).getBlock();
//                minecraft.getRenderItem().renderItemIntoGUI(new ItemStack(block, 1, block.getMetaFromState(minecraft.world.getBlockState(trace.getBlockPos()))), 100, 100);
//            }
        }

        if(minecraft.player != null) {
//            List<Entity> box = minecraft.world.getEntitiesWithinAABBExcludingEntity(minecraft.player, minecraft.player.getEntityBoundingBox().grow(128));
        }
    }
}