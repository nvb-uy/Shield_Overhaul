package elocindev.shield_overhaul.mixin;

import elocindev.shield_overhaul.ShieldOverhaul;
import elocindev.shield_overhaul.config.ConfigEntries;
import elocindev.shield_overhaul.util.ShieldUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class BlockingMixin extends LivingEntity {
    ConfigEntries config = ShieldOverhaul.CONFIG;

    @Shadow protected abstract void applyDamage(DamageSource source, float amount);

    protected BlockingMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "damage", at = @At("TAIL"), cancellable = true)
    public void $shield_overhaul_damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        float damageReduction = getDamageReduction((PlayerEntity)(Object) this);
        // test / example (it works)
        damageReduction = 0.5f;
        ShieldOverhaul.LOGGER.info("Damage reduction: " + damageReduction);

        boolean bl = cir.getReturnValue();
        if (amount > 0.0f && this.blockedByShield(source)) {
            Entity entity;
            this.damageShield(amount);
            if (!source.isIn(DamageTypeTags.IS_PROJECTILE) && (entity = source.getSource()) instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                this.takeShieldHit(livingEntity);
            }
            bl = true;

            ShieldOverhaul.LOGGER.info("Initial Damage: " + amount);
            ShieldOverhaul.LOGGER.info("Damage applied: " + (amount - (amount * damageReduction)));
            this.applyDamage(source, amount - (amount * damageReduction));
        }
        cir.setReturnValue(bl);
    }

    public float getDamageReduction(PlayerEntity playerEntity) {
        ItemStack shield = playerEntity.getStackInHand(playerEntity.getActiveHand());
        long window = 0;
        if (shield.getNbt() != null) {
            window = (shield.getNbt().getLong("parry_window") - playerEntity.getWorld().getTime());
        }
        return Math.max(0, Math.min(1, (window/config.parry_abuse_cooldown_secs)/100));
    }
}