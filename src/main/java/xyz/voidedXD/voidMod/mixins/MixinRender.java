package xyz.voidedXD.voidMod.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Render.class)
public abstract class MixinRender<T extends Entity> {
    @Shadow protected abstract void renderLivingLabel(T entityIn, String str, double x, double y, double z, int maxDistance);

    @Shadow protected abstract void renderName(T entity, double x, double y, double z);

    @Shadow protected boolean renderOutlines;

    //    @Inject(method="doRender", at = @At("TAIL"))
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.gameSettings.showDebugInfo) {
            String entityID = Integer.toString(entity.getEntityId());
            this.renderLivingLabel(entity, entity.getName().equals("voidedXD") ? "jeb_void (" + entityID + ")" : entityID, x, y, z, 64);
        } else if (!this.renderOutlines) {
            this.renderName(entity, x, y, z);
        }
    }
}
