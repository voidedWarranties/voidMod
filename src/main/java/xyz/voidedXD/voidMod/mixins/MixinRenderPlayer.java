package xyz.voidedXD.voidMod.mixins;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderPlayer.class)
public abstract class MixinRenderPlayer extends RenderLivingBase<AbstractClientPlayer> {

    public MixinRenderPlayer(RenderManager renderManager, boolean useSmallArms) {
        super(renderManager, new ModelPlayer(0.0F, useSmallArms), 0.5F);
    }

    @Inject(method = "doRender", at = @At("HEAD"))
    public void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        int previousTicks = entity.ticksExisted;
        int offset = entity.getEntityId();

        final int ticks = previousTicks / 25 + offset;
        final int colorCount = EnumDyeColor.values().length;
        final int colorMeta1 = ticks % colorCount;
        final int colorMeta2 = (ticks + 1) % colorCount;
        final float f = (previousTicks % 25 + partialTicks) / 25.0F;
        final float[] color1 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(colorMeta1));
        final float[] color2 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(colorMeta2));
        GlStateManager.color(color1[0] * (1.0F - f) + color2[0] * f, color1[1] * (1.0F - f) + color2[1] * f, color1[2] * (1.0F - f) + color2[2] * f);
    }
}
