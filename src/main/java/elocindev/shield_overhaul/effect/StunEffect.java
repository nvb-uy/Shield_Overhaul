package elocindev.shield_overhaul.effect;

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
        if (!entity.world.isClient()) {
            entity.setVelocity(0, entity.getVelocity().y >= 0 ? 0f : (float) entity.getVelocity().y, 0);
        }
    }
}
