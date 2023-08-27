package elocindev.shield_overhaul.registry;

import elocindev.shield_overhaul.ShieldOverhaul;
import elocindev.shield_overhaul.effect.Immunity;
import elocindev.shield_overhaul.effect.StunEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EffectRegistry {
    public static final StatusEffect STUN_EFFECT = new StunEffect(StatusEffectCategory.HARMFUL, 0xffff00);
    public static final StatusEffect STUN_IMMUNITY = new Immunity(StatusEffectCategory.BENEFICIAL, 0xffffff);

    public static void initEffects() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(ShieldOverhaul.MODID, "stun"), STUN_EFFECT);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(ShieldOverhaul.MODID, "immunity"), STUN_IMMUNITY);
    }
}
