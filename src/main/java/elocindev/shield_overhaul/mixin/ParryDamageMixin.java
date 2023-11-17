package elocindev.shield_overhaul.mixin;

import elocindev.shield_overhaul.ShieldOverhaul;
import elocindev.shield_overhaul.config.ConfigEntries;
import elocindev.shield_overhaul.util.ShieldUtils;
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
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class ParryDamageMixin {
    private static String key = "parry_window";
    ConfigEntries config = ShieldOverhaul.CONFIG;



    @Inject(method = "modifyAppliedDamage", at = @At("HEAD"), cancellable = true)
    public void $shield_overhaul_damage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if (!(((LivingEntity) (Object) this) instanceof PlayerEntity playerEntity) || !(playerEntity.getStackInHand(playerEntity.getActiveHand()).getItem() instanceof ShieldItem)) return;

        ItemStack shield = playerEntity.getStackInHand(playerEntity.getActiveHand());
        boolean isParrying = ShieldUtils.isParrying(shield, playerEntity.getWorld());
        long window = (shield.getNbt().getLong(key) - playerEntity.getWorld().getTime());

        float damageReduction = Math.max(0, Math.min(1, (window/config.parry_abuse_cooldown_secs)/100));
        playerEntity.sendMessage(Text.literal("parry damage reduction: "+ String.valueOf(damageReduction)));

        if (isParrying && damageReduction > 0) {
            //cir.cancel();
            parryDamageLogic(source, amount, damageReduction, cir);
        }
    }

    private void parryDamageLogic(DamageSource source, float amount, float damageReduction, CallbackInfoReturnable<Float> cir) {
        if (source.isIn(DamageTypeTags.BYPASSES_EFFECTS)) {
            cir.setReturnValue(amount);
        } else {
            int i;
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

                if (((LivingEntity) (Object) this) instanceof PlayerEntity playerEntity) {
                    playerEntity.sendMessage(Text.literal("amount: "+ String.valueOf(amount)));
                }

                cir.setReturnValue(amount);
            }
        }
    }

}
