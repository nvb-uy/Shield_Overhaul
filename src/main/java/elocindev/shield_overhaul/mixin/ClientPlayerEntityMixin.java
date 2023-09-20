package elocindev.shield_overhaul.mixin;


import com.mojang.authlib.GameProfile;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import elocindev.shield_overhaul.util.IShieldAnimatedPlayer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.encryption.PlayerPublicKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractClientPlayerEntity.class)
public class ClientPlayerEntityMixin implements IShieldAnimatedPlayer {

    //Unique annotation will rename private methods/fields if needed to avoid collisions.
    @Unique
    private final ModifierLayer<IAnimation> modAnimationContainer = new ModifierLayer<>();

    /**
     * Add the animation registration to the end of the constructor
     * Or you can use {@link dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess#REGISTER_ANIMATION_EVENT} event for this
     */
    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void init(ClientWorld world, GameProfile profile, CallbackInfo ci) {
        //Mixin does not know (yet) that this will be merged with AbstractClientPlayerEntity
        PlayerAnimationAccess.getPlayerAnimLayer((AbstractClientPlayerEntity) (Object)this).addAnimLayer(1000, modAnimationContainer); //Register the layer with a priority
        //The priority will tell, how important is this animation compared to other mods. Higher number means higher priority
        //Mods with higher priority will override the lower priority mods (if they want to animation anything)
    }

    /**
     * Override the interface function, so we can use it in the future
     */
    @Override
    public ModifierLayer<IAnimation> shield_overhaul_getModAnimation() {
        return modAnimationContainer;
    }
}