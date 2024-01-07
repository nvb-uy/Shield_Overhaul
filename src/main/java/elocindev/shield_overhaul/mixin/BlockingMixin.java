package elocindev.shield_overhaul.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class BlockingMixin extends LivingEntity {

    @Shadow protected abstract void applyDamage(DamageSource source, float amount);

    protected BlockingMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "damage", at = @At("TAIL"), cancellable = true)
    public void $shield_overhaul_damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        boolean bl = cir.getReturnValue();
        if (amount > 0.0f && this.blockedByShield(source)) {
            Entity entity;
            this.damageShield(amount);
            if (!source.isIn(DamageTypeTags.IS_PROJECTILE) && (entity = source.getSource()) instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                this.takeShieldHit(livingEntity);
            }
            bl = true;
            this.applyDamage(source, amount * 0.001f);
        }
        cir.setReturnValue(bl);
    }
}