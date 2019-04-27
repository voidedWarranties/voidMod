package xyz.voidedXD.voidMod.mixins;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFood.class)
public abstract class MixinItemFood extends Item {
    @Shadow protected abstract void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player);

    @Shadow public abstract int getHealAmount(ItemStack stack);

    @Inject(method = "onItemUseFinish", at=@At("HEAD"), cancellable = true)
    public void onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving, CallbackInfoReturnable cir) {
        if(entityLiving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) entityLiving;
            worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
            this.onFoodEaten(stack, worldIn, entityplayer);
            entityplayer.heal(this.getHealAmount(stack));
        }
        stack.shrink(1);
        cir.setReturnValue(stack);
        cir.cancel();
    }

    @Inject(method = "getMaxItemUseDuration", at=@At("HEAD"), cancellable = true)
    public void getMaxItemUseDuration(CallbackInfoReturnable cir) {
        cir.setReturnValue(16);
        cir.cancel();
    }
}
