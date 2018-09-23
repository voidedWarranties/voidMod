package xyz.voidedXD.voidMod.mixins;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderLiving.class)
public abstract class MixinRenderLiving<T extends EntityLiving> {
    @Inject(method = "doRender", at = @At("HEAD"))
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
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
