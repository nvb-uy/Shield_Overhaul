package elocindev.shield_overhaul.mixin;

import elocindev.shield_overhaul.ShieldOverhaul;
import elocindev.shield_overhaul.config.ConfigEntries;
import elocindev.shield_overhaul.registry.ClientPacketRegistry;
import elocindev.shield_overhaul.util.ShieldUtils;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class ParryDamageMixin {
    private static String key = "parry_window";
    ConfigEntries config = ShieldOverhaul.CONFIG;

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void $shield_overhaul_damage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if (!(((LivingEntity) (Object) this) instanceof PlayerEntity playerEntity) || !(playerEntity.getStackInHand(playerEntity.getActiveHand()).getItem() instanceof ShieldItem)) return;

        ItemStack shield = playerEntity.getStackInHand(playerEntity.getActiveHand());
        boolean isParrying = ShieldUtils.isParrying(shield, playerEntity.getWorld());
        long window = (shield.getNbt().getLong(key) - playerEntity.getWorld().getTime());
        float damageReduction = Math.max(0, Math.min(1, (window/config.parry_abuse_cooldown_secs)/100));

        // Perfect parry
        if (isParrying && damageReduction >= 0.8) {
            if (!playerEntity.getWorld().isClient()) {
                playerEntity.getWorld().playSound(null, playerEntity.getBlockPos(), SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, 1, 1);
            }
            for (ServerPlayerEntity target : PlayerLookup.tracking((ServerWorld)playerEntity.getWorld(), new ChunkPos((int)playerEntity.getPos().x / 16, (int)playerEntity.getPos().z / 16))) {
                ServerPlayNetworking.send(target, ClientPacketRegistry.PARRY_EFFECT_S2C_PACKET, PacketByteBufs.create().writeUuid(playerEntity.getUuid()));
            }
            cir.cancel();
        }
    }

    @Inject(method = "modifyAppliedDamage", at = @At("HEAD"), cancellable = true)
    public void $shield_overhaul_modifyAppliedDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if (!(((LivingEntity) (Object) this) instanceof PlayerEntity playerEntity) || !(playerEntity.getStackInHand(playerEntity.getActiveHand()).getItem() instanceof ShieldItem)) return;

        ItemStack shield = playerEntity.getStackInHand(playerEntity.getActiveHand());
        boolean isParrying = ShieldUtils.isParrying(shield, playerEntity.getWorld());
        long window = (shield.getNbt().getLong(key) - playerEntity.getWorld().getTime());

        float damageReduction = Math.max(0, Math.min(1, (window/config.parry_abuse_cooldown_secs)/100));

        if (isParrying && damageReduction > 0) {
            parryDamageLogic(source, amount, damageReduction, cir);
        }
    }

    private void parryDamageLogic(DamageSource source, float amount, float damageReduction, CallbackInfoReturnable<Float> cir) {
        if (source.isIn(DamageTypeTags.BYPASSES_EFFECTS)) {
            cir.setReturnValue(amount);
        } else {
            int i;
            float initialAmount = amount;
            if (!((LivingEntity) (Object) this).hasStatusEffect(StatusEffects.RESISTANCE) && !source.isIn(DamageTypeTags.BYPASSES_RESISTANCE)) {
                // i = percentage from 0-100 of resistance
                // formula = amplifier * 5, eg 1*5 all the way to 5*5 and then is taken away
                // if j = 0, then 0 damage is applied, meaning if j is 1 then theoretically all damage should be applied.

                // j becomes 0, which I assume is the damage applied

                float j = damageReduction * 100;
                float f = amount * (float)j;
                float g = amount;
                amount = Math.max(f / 100.0F, 0.0F);
                float h = g - amount;
                if (h > 0.0F && h < 3.4028235E37F) {
                    if ((Object) this instanceof ServerPlayerEntity) {
                        ((ServerPlayerEntity)(Object)this).increaseStat(Stats.DAMAGE_RESISTED, Math.round(h * 10.0F));
                    } else if (source.getAttacker() instanceof ServerPlayerEntity) {
                        ((ServerPlayerEntity)source.getAttacker()).increaseStat(Stats.DAMAGE_DEALT_RESISTED, Math.round(h * 10.0F));
                    }
                }
            }

            if (amount <= 0.0F) {
                cir.setReturnValue(0.0F);
            } else if (source.isIn(DamageTypeTags.BYPASSES_ENCHANTMENTS)) {
                cir.setReturnValue(amount);
            } else {
                i = EnchantmentHelper.getProtectionAmount(((LivingEntity) (Object) this).getArmorItems(), source);
                if (i > 0) {
                    amount = DamageUtil.getInflictedDamage(amount, (float)i);
                }

                cir.setReturnValue(initialAmount - amount);
            }
        }
    }

}
