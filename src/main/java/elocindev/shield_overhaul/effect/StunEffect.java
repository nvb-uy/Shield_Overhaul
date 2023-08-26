package elocindev.shield_overhaul.effect;

import elocindev.shield_overhaul.ShieldOverhaul;
import elocindev.shield_overhaul.registry.EffectRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

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
        if (ShieldOverhaul.CONFIG.bosses_immune_to_stun && !entity.canUsePortals()) entity.removeStatusEffect(this);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity.hasStatusEffect(EffectRegistry.STUN_IMMUNITY)) { entity.removeStatusEffect(EffectRegistry.STUN_EFFECT); return; }
        super.onApplied(entity, attributes, amplifier);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);

        if (ShieldOverhaul.CONFIG.add_stun_immunity && entity.canUsePortals() && !entity.hasStatusEffect(EffectRegistry.STUN_IMMUNITY)) {
            entity.addStatusEffect(new StatusEffectInstance(EffectRegistry.STUN_IMMUNITY, 60, 0, false, false, true));
        }
    }
}
