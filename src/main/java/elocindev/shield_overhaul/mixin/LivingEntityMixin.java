package elocindev.shield_overhaul.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
}
