package elocindev.shield_overhaul.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import elocindev.shield_overhaul.ShieldOverhaul;
import elocindev.shield_overhaul.registry.EffectRegistry;
import net.minecraft.entity.LivingEntity;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "isImmobile", at = @At(value = "HEAD"), cancellable = true)
    private void isStunned(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if(entity.hasStatusEffect(EffectRegistry.STUN_EFFECT)) {
            cir.setReturnValue(true);;
        }
    }

    // Credits to https://github.com/Quplet/NoShieldDelay (MIT Licensed)
    @ModifyConstant(method = "isBlocking", constant = @Constant(intValue = 5))
    private int setShieldUseDelay(int constant) {
        return ShieldOverhaul.CONFIG.enable_instant_shield_use ? 1 : constant;
    }
}
