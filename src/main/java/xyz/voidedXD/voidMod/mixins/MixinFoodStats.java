package xyz.voidedXD.voidMod.mixins;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.world.EnumDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodStats.class)
public abstract class MixinFoodStats {
    @Shadow private int prevFoodLevel;

    @Shadow private int foodLevel;

    @Shadow private float foodExhaustionLevel;

    @Shadow private float foodSaturationLevel;

    @Shadow private int foodTimer;

    @Shadow public abstract void addExhaustion(float exhaustion);

    @Inject(method = "onUpdate", at = @At("HEAD"), cancellable = true)
    public void onUpdate(EntityPlayer player, CallbackInfo ci) {
        EnumDifficulty enumdifficulty = player.world.getDifficulty();
        this.prevFoodLevel = this.foodLevel;

        if (this.foodExhaustionLevel > 4.0F)
        {
            this.foodExhaustionLevel -= 4.0F;

            if (this.foodSaturationLevel > 0.0F)
            {
                this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 1.0F, 0.0F);
            }
            else if (enumdifficulty != EnumDifficulty.PEACEFUL)
            {
                this.foodLevel = Math.max(this.foodLevel - 1, 0);
            }
        }

        if (this.foodLevel <= 0)
        {
            ++this.foodTimer;

            if (this.foodTimer >= 80)
            {
                if (player.getHealth() > 10.0F || enumdifficulty == EnumDifficulty.HARD || player.getHealth() > 1.0F && enumdifficulty == EnumDifficulty.NORMAL)
                {
                    player.attackEntityFrom(DamageSource.STARVE, 1.0F);
                }

                this.foodTimer = 0;
            }
        }
        else
        {
            this.foodTimer = 0;
        }
        ci.cancel();
    }
}
