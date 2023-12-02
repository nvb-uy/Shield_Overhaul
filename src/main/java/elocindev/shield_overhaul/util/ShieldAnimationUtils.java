package elocindev.shield_overhaul.util;

import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.modifier.SpeedModifier;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import elocindev.shield_overhaul.ShieldOverhaul;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class ShieldAnimationUtils {
    private static SpeedModifier SPEED = new SpeedModifier(4);

    public static void playShieldBash(PlayerEntity user, String side) {
        if (user != null) {
            var animationContainer = ((IShieldAnimatedPlayer)user).shield_overhaul_getModAnimation();
            KeyframeAnimation anim = PlayerAnimationRegistry.getAnimation(new Identifier(ShieldOverhaul.MODID, side.equals("right") ? "bash_right" : "bash_left"));
            var builder = anim.mutableCopy();
            anim = builder.build();
            animationContainer.addModifierLast(SPEED);
            animationContainer.setAnimation(new KeyframeAnimationPlayer(anim).setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL));
        }
        //animationContainer.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(0, Ease.CONSTANT), null);
    }
}
