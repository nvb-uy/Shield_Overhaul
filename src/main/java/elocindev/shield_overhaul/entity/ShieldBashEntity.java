package elocindev.shield_overhaul.entity;

import elocindev.shield_overhaul.registry.EffectRegistry;
import elocindev.shield_overhaul.registry.EntityRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ShieldBashEntity extends PersistentProjectileEntity {
    private int life = 0;

    public ShieldBashEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public ShieldBashEntity(LivingEntity owner, World world) {
        super(EntityRegistry.SHIELD_BASH_ENTITY, owner, world);
        this.setNoGravity(true);
        this.setDamage(0f);
    }

    @Override
    public void tick() {
        super.tick();

        ++this.life;
        if (this.life >= 3) {
            if (this.getOwner() != null && !this.world.isClient()) {
                LivingEntity entity = (LivingEntity) this.getOwner();
                Vec3d vec3d = (new Vec3d(entity.getX() - this.getX(), entity.getY() - this.getY(), entity.getZ() - this.getZ())).multiply(0.1D);
                entity.setVelocity(entity.getVelocity().add(vec3d));
            }
            this.discard();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (entityHitResult.getEntity() instanceof LivingEntity entity && !entity.world.isClient()) {
            entity.addStatusEffect(new StatusEffectInstance(EffectRegistry.STUN_EFFECT, 20, 0, false, false, true));
            this.setRemoved(RemovalReason.DISCARDED); 
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return null;
    }

    @Override
    protected SoundEvent getHitSound() {
        return new SoundEvent(new Identifier("null"));
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        this.setRemoved(RemovalReason.DISCARDED); 
    }
}
