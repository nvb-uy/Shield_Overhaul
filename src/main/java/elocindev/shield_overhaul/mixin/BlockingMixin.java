package elocindev.shield_overhaul.mixin;

import elocindev.shield_overhaul.ShieldOverhaul;
import elocindev.shield_overhaul.config.ConfigEntries;
import elocindev.shield_overhaul.registry.ClientPacketRegistry;
import elocindev.shield_overhaul.util.ShieldUtils;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.ChunkPos;
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
        //damageReduction = 0.5f;
        ShieldOverhaul.LOGGER.info("Damage reduction: " + damageReduction);

        boolean bl = cir.getReturnValue();
        if (amount > 0.0f && this.blockedByShield(source)) {
            Entity entity;
            if (!source.isIn(DamageTypeTags.IS_PROJECTILE) && (entity = source.getSource()) instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                this.takeShieldHit(livingEntity);
            }
            bl = true;

            float trueDamageReduction = Math.max(0.7f, damageReduction);

            ShieldOverhaul.LOGGER.info("Initial Damage: " + amount);
            ShieldOverhaul.LOGGER.info("True damage reduction: " + trueDamageReduction);
            ShieldOverhaul.LOGGER.info("Damage applied: " + (amount - (amount * trueDamageReduction)));
            ShieldOverhaul.LOGGER.info("");

            this.applyDamage(source, amount - (amount * trueDamageReduction));

            if (trueDamageReduction >= 0.9) {
                ShieldOverhaul.LOGGER.info("Perfect parry");
                this.damageShield(0);
                for (ServerPlayerEntity target : PlayerLookup.tracking((ServerWorld)this.getWorld(), new ChunkPos((int)this.getPos().x / 16, (int)this.getPos().z / 16))) {
                    if (target != null && this != null) {
                        ServerPlayNetworking.send(target, ClientPacketRegistry.PARRY_EFFECT_S2C_PACKET, PacketByteBufs.create().writeUuid(this.getUuid()));
                    }
                }
            } else {
                this.damageShield(amount - (amount * damageReduction));
            }
        }
        cir.setReturnValue(bl);
    }

    public float getDamageReduction(PlayerEntity playerEntity) {
        ItemStack shield = playerEntity.getStackInHand(playerEntity.getActiveHand());
        long window = 0;
        if (shield.getNbt() != null) {
            window = (shield.getNbt().getLong("parry_window") - playerEntity.getWorld().getTime());
        }
        return Math.max(0, Math.min(1, (window/config.parry_abuse_cooldown_secs)/10));
    }
}