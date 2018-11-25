package xyz.voidedXD.voidMod.mixins;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase {
//    Random rand = new Random();
//    @Shadow public PlayerCapabilities capabilities;

    public MixinEntityPlayer(World worldIn) {
        super(worldIn);
    }

//    @Inject(method = "onUpdate", at = @At("RETURN"))
//    public void onUpdate(CallbackInfo ci) {
//        if(this.capabilities.isFlying) {
//            for(int i = 0; i < 10; i++) {
//                this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY, this.posZ, rand.nextDouble() * 0.25 - 0.125, 0, rand.nextDouble() * 0.25 - 0.125);
////                this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX, this.posY, this.posZ, rand.nextDouble() * 0.25 - 0.125, 0, rand.nextDouble() * 0.25 - 0.125);
//            }
////            ((WorldServer)this.world).spawnParticle(EnumParticleTypes.REDSTONE, this.posX, this.posY, this.posZ, 20, 0, 0, 0, 0.2D);
//        }
//    }

//    public boolean isElytraFlying() {
//        if(this.isSneaking() && this.world.isRemote) {
//            return true;
//        } else {
//            return this.getFlag(7);
//        }
//    }

//    @Inject(method = "attackTargetEntityWithCurrentItem", at = @At("HEAD"))
//    public void attackTargetEntityWithCurrentItem(Entity targetEntity, CallbackInfo ci) {
//        if (this.getCooledAttackStrength(0.5F) < 1.0F) {
//            return;
//        }
//    }
}
