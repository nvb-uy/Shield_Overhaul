package elocindev.shield_overhaul.effect;

import elocindev.shield_overhaul.ShieldOverhaul;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class StunEffect extends StatusEffect {
    public StunEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (ShieldOverhaul.CONFIG.bosses_immune_to_stun && entity.canUsePortals()) entity.removeStatusEffect(this);

        if (!entity.world.isClient()) {
            entity.setVelocity(0, entity.getVelocity().y >= 0 ? 0f : (float) entity.getVelocity().y, 0);
        }
    }
}
