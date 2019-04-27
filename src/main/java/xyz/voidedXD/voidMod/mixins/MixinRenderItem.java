package xyz.voidedXD.voidMod.mixins;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RenderItem.class)
public abstract class MixinRenderItem {

    @Shadow protected abstract void renderEffect(IBakedModel model);

    @Shadow protected abstract void renderModel(IBakedModel model, ItemStack stack);

    public void renderItem(ItemStack stack, IBakedModel model)
    {
        if (!stack.isEmpty())
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate(-0.5F, -0.5F, -0.5F);

            if (model.isBuiltInRenderer())
            {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.enableRescaleNormal();
                TileEntityItemStackRenderer.instance.renderByItem(stack);
            }
            else
            {
                this.renderModel(model, stack);

                if (stack.hasEffect())
                {
                    this.renderEffect(model);
                }
            }

            GlStateManager.popMatrix();
        }
    }
}
