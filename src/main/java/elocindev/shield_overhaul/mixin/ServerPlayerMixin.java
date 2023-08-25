package elocindev.shield_overhaul.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import elocindev.shield_overhaul.registry.EffectRegistry;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerMixin {
    @Inject(method = "updateInput", at = @At("HEAD"))
    public void updateInput(float sidewaysSpeed, float forwardSpeed, boolean jumping, boolean sneaking, CallbackInfo info) {
        ServerPlayerEntity entity = (ServerPlayerEntity) (Object) this;

        if (entity.hasStatusEffect(EffectRegistry.STUN_EFFECT)) {
            sidewaysSpeed = 0.0f;
            forwardSpeed = 0.0f;
            jumping = false;
        }
    }
}
