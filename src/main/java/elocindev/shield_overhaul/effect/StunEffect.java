package elocindev.shield_overhaul.effect;

import elocindev.shield_overhaul.ShieldOverhaul;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.sound.SoundEvents;

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
        entity.playSound(SoundEvents.BLOCK_ANVIL_DESTROY, 1, 1);
        super.onApplied(entity, attributes, amplifier);
    }
}
