package xyz.voidedXD.voidMod.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.ParticleItemPickup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketCollectItem;
import net.minecraft.util.SoundCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlerPlayClient implements INetHandlerPlayClient {

    @Shadow private Minecraft client;

    @Shadow private WorldClient world;

    @Shadow @Final private Random avRandomizer;

    @Inject(method="handleCollectItem", at=@At("HEAD"), cancellable = true)
    public void handleCollectItem(SPacketCollectItem packetIn, CallbackInfo ci)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Entity entity = this.world.getEntityByID(packetIn.getCollectedItemEntityID());
        EntityLivingBase entitylivingbase = (EntityLivingBase)this.world.getEntityByID(packetIn.getEntityID());

        if (entitylivingbase == null)
        {
            entitylivingbase = this.client.player;
        }

        if (entity != null)
        {
            if (entity instanceof EntityXPOrb)
            {
                this.world.playSound(entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.2F, 0.5F * ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7F + 1.0F), false);
            }
            else
            {
                this.world.playSound(entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, (this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 1.4F + 2.0F, false);
            }

            if (entity instanceof EntityItem)
            {
                ((EntityItem)entity).getItem().setCount(packetIn.getAmount());
            }

            this.client.effectRenderer.addEffect(new ParticleItemPickup(this.world, entity, entitylivingbase, 0.5F));
            this.world.removeEntityFromWorld(packetIn.getCollectedItemEntityID());
        }
        ci.cancel();
    }
}
