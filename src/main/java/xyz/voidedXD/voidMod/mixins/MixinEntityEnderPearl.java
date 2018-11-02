package xyz.voidedXD.voidMod.mixins;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.voidedXD.voidMod.awt.InfoPanel;

import java.awt.*;

@Mixin(EntityEnderPearl.class)
public abstract class MixinEntityEnderPearl extends EntityThrowable {

    @Shadow private EntityLivingBase perlThrower;
    private InfoPanel infoPanel = InfoPanel.getInstance();
    private TextArea info = new TextArea();

    public MixinEntityEnderPearl(World worldIn) {
        super(worldIn);
    }

    @Inject(method="<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/EntityLivingBase;)V", at=@At("RETURN"))
    public void onConstructed(CallbackInfo ci) {
        info.setFont(new Font("Monospaced", 0, 12));
        info.setEditable(false);
        info.setPreferredSize(new Dimension(854, 50));
        infoPanel.add(info, BorderLayout.NORTH);
        infoPanel.validate();
    }

    @Inject(method="onUpdate", at=@At("HEAD"))
    public void onUpdate(CallbackInfo ci) {
        info.setText("(" + Double.toString(this.motionX) + ", " + Double.toString(this.motionY) + ", " + Double.toString(this.motionZ) +  ")" + "\n");
    }

    protected void onImpact(RayTraceResult result)
    {
        EntityLivingBase entitylivingbase = this.getThrower();

        if (result.entityHit != null)
        {
            if (result.entityHit == this.perlThrower)
            {
                return;
            }

            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, entitylivingbase), 0.0F);
        }

        if (result.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            BlockPos blockpos = result.getBlockPos();
            TileEntity tileentity = this.world.getTileEntity(blockpos);

            if (tileentity instanceof TileEntityEndGateway)
            {
                TileEntityEndGateway tileentityendgateway = (TileEntityEndGateway)tileentity;

                if (entitylivingbase != null)
                {
                    if (entitylivingbase instanceof EntityPlayerMP)
                    {
                        CriteriaTriggers.ENTER_BLOCK.trigger((EntityPlayerMP)entitylivingbase, this.world.getBlockState(blockpos));
                    }

                    tileentityendgateway.teleportEntity(entitylivingbase);
                    this.setDead();
                    return;
                }

                tileentityendgateway.teleportEntity(this);
                return;
            }
        }

        for (int i = 0; i < 32; ++i)
        {
            this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX, this.posY + this.rand.nextDouble() * 2.0D, this.posZ, this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian());
        }

        if (!this.world.isRemote)
        {
            if (entitylivingbase instanceof EntityPlayerMP)
            {
                EntityPlayerMP entityplayermp = (EntityPlayerMP)entitylivingbase;

                if (entityplayermp.connection.getNetworkManager().isChannelOpen() && entityplayermp.world == this.world && !entityplayermp.isPlayerSleeping())
                {
                    if (this.rand.nextFloat() < 0.05F && this.world.getGameRules().getBoolean("doMobSpawning"))
                    {
                        EntityEndermite entityendermite = new EntityEndermite(this.world);
                        entityendermite.setSpawnedByPlayer(true);
                        entityendermite.setLocationAndAngles(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, entitylivingbase.rotationYaw, entitylivingbase.rotationPitch);
                        this.world.spawnEntity(entityendermite);
                    }

                    if (entitylivingbase.isRiding())
                    {
                        entitylivingbase.dismountRidingEntity();
                    }

                    entitylivingbase.setPositionAndUpdate(this.posX, this.posY, this.posZ);
                    entitylivingbase.fallDistance = 0.0F;
                    entitylivingbase.attackEntityFrom(DamageSource.FALL, 5.0F);
                }
            }
            else if (entitylivingbase != null)
            {
                entitylivingbase.setPositionAndUpdate(this.posX, this.posY, this.posZ);
                entitylivingbase.fallDistance = 0.0F;
            }

            infoPanel.remove(info);
            this.setDead();
        }
    }
}
