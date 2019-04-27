package xyz.voidedXD.voidMod.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.FoodStats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase {
//    Random rand = new Random();
//    @Shadow public PlayerCapabilities capabilities;

    @Shadow protected abstract void collideWithPlayer(Entity entityIn);

    @Shadow public abstract NBTTagCompound getLeftShoulderEntity();

    @Shadow public abstract NBTTagCompound getRightShoulderEntity();

    @Shadow protected abstract void playShoulderEntityAmbientSound(@Nullable NBTTagCompound p_192028_1_);

    @Shadow protected abstract void spawnShoulderEntities();

    @Shadow public PlayerCapabilities capabilities;

    @Shadow public abstract boolean isSpectator();

    @Shadow public float cameraYaw;

    @Shadow protected float speedInAir;

    @Shadow public float prevCameraYaw;

    @Shadow public InventoryPlayer inventory;

    @Shadow protected FoodStats foodStats;

    @Shadow protected int flyToggleTimer;

    public MixinEntityPlayer(World worldIn) {
        super(worldIn);
    }

    @Inject(method = "getFoodStats", at = @At("HEAD"), cancellable = true)
    public void getFoodStats(CallbackInfoReturnable cir)
    {
        FoodStats stat = new FoodStats();
        stat.setFoodLevel(20);
        stat.setFoodSaturationLevel(20);
        cir.setReturnValue(stat);
        cir.cancel();
    }

    @Inject(method = "shouldHeal", at = @At("HEAD"), cancellable = true)
    public void shouldHeal(CallbackInfoReturnable cir) {
        cir.setReturnValue(false);
        cir.cancel();
    }

    @Inject(method = "addExhaustion", at = @At("HEAD"), cancellable = true)
    public void addExhaustion(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "canEat", at = @At("HEAD"), cancellable = true)
    public void canEat(CallbackInfoReturnable cir) {
        cir.setReturnValue(this.getHealth() < 20);
        cir.cancel();
    }

    @Inject(method = "onLivingUpdate", at = @At("HEAD"), cancellable = true)
    public void onLivingUpdate(CallbackInfo ci) {
        if (this.flyToggleTimer > 0)
        {
            --this.flyToggleTimer;
        }

        this.inventory.decrementAnimations();
        this.prevCameraYaw = this.cameraYaw;
        super.onLivingUpdate();
        IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

        if (!this.world.isRemote)
        {
            iattributeinstance.setBaseValue((double)this.capabilities.getWalkSpeed());
        }

        this.jumpMovementFactor = this.speedInAir;

        if (this.isSprinting())
        {
            this.jumpMovementFactor = (float)((double)this.jumpMovementFactor + (double)this.speedInAir * 0.3D);
        }

        this.setAIMoveSpeed((float)iattributeinstance.getAttributeValue());
        float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float f1 = (float)(Math.atan(-this.motionY * 0.20000000298023224D) * 15.0D);

        if (f > 0.1F)
        {
            f = 0.1F;
        }

        if (!this.onGround || this.getHealth() <= 0.0F)
        {
            f = 0.0F;
        }

        if (this.onGround || this.getHealth() <= 0.0F)
        {
            f1 = 0.0F;
        }

        this.cameraYaw += (f - this.cameraYaw) * 0.4F;
        this.cameraPitch += (f1 - this.cameraPitch) * 0.8F;

        if (this.getHealth() > 0.0F && !this.isSpectator())
        {
            AxisAlignedBB axisalignedbb;

            if (this.isRiding() && !this.getRidingEntity().isDead)
            {
                axisalignedbb = this.getEntityBoundingBox().union(this.getRidingEntity().getEntityBoundingBox()).grow(1.0D, 0.0D, 1.0D);
            }
            else
            {
                axisalignedbb = this.getEntityBoundingBox().grow(1.0D, 0.5D, 1.0D);
            }

            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);

            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity = list.get(i);

                if (!entity.isDead)
                {
                    this.collideWithPlayer(entity);
                }
            }
        }

        this.playShoulderEntityAmbientSound(this.getLeftShoulderEntity());
        this.playShoulderEntityAmbientSound(this.getRightShoulderEntity());

        if (!this.world.isRemote && (this.fallDistance > 0.5F || this.isInWater() || this.isRiding()) || this.capabilities.isFlying)
        {
            this.spawnShoulderEntities();
        }
        ci.cancel();
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
