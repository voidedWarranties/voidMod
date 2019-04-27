package xyz.voidedXD.voidMod.mixins;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderEntityItem.class)
public abstract class MixinRenderEntityItem extends Render<EntityItem> {
    @Shadow protected abstract int getModelCount(ItemStack stack);

    public MixinRenderEntityItem(RenderManager renderManagerIn, RenderItem p_i46167_2_) {
        super(renderManagerIn);
    }

//    private int transformModelCount(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_)
//    {
//        ItemStack itemstack = itemIn.getItem();
//        Item item = itemstack.getItem();
//
//        if (item == null)
//        {
//            return 0;
//        }
//        else
//        {
//            boolean flag = p_177077_9_.isGui3d();
//            int i = this.getModelCount(itemstack);
//            float f1 = MathHelper.sin(((float)itemIn.getAge() + p_177077_8_) / 10.0F + itemIn.hoverStart) * 0.1F + 0.1F;
//            float f2 = p_177077_9_.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y;
//            GlStateManager.translate((float)p_177077_2_, (float)p_177077_4_ + f1 + 0.25F * f2, (float)p_177077_6_);
//
//            if (flag || this.renderManager.options != null)
//            {
//                float f3 = 180F - renderManager.playerViewY;
//                if(itemstack.getItem() instanceof ItemBlock) {
//                    f3 = (((float)itemIn.getAge() + p_177077_8_) / 20.0F + itemIn.hoverStart) * (180F / (float)Math.PI);
//                }
//                GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
//            }
//
//            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//            return i;
//        }
//    }

    @Inject(method = "transformModelCount", at = @At("HEAD"), cancellable = true)
    private void transformModelCount(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_, CallbackInfoReturnable cir) {
        ItemStack itemstack = itemIn.getItem();
        Item item = itemstack.getItem();

        if (item == null)
        {
            cir.setReturnValue(0);
        }
        else
        {
            boolean flag = p_177077_9_.isGui3d();
            int i = this.getModelCount(itemstack);
            float f1 = MathHelper.sin(((float)itemIn.getAge() + p_177077_8_) / 10.0F + itemIn.hoverStart) * 0.1F + 0.1F;
            float f2 = p_177077_9_.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y;
            GlStateManager.translate((float)p_177077_2_, (float)p_177077_4_ + f1 + 0.25F * f2, (float)p_177077_6_);

            if (flag || this.renderManager.options != null)
            {
                float f3 = 180F - renderManager.playerViewY;
                if(itemstack.getItem() instanceof ItemBlock) {
                    f3 = (((float)itemIn.getAge() + p_177077_8_) / 20.0F + itemIn.hoverStart) * (180F / (float)Math.PI);
                }
                GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            cir.setReturnValue(i);
        }
        cir.cancel();
    }

//    @Inject(method="transformModelCount", at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", shift=At.Shift.BEFORE))
//    private void transformModelCount(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_, CallbackInfoReturnable cir) {
//        if(!(itemIn.getItem().getItem() instanceof ItemBlock)) {
//            GlStateManager.rotate(180F- renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
//        }
//    }
}
