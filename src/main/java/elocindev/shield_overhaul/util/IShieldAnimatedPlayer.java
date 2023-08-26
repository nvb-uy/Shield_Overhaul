package elocindev.shield_overhaul.util;

import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;

public interface IShieldAnimatedPlayer {
    ModifierLayer<IAnimation> shield_overhaul_getModAnimation();
}
