package elocindev.shield_overhaul.mixin;

import elocindev.shield_overhaul.ShieldOverhaul;
import elocindev.shield_overhaul.config.ConfigEntries;
import elocindev.shield_overhaul.util.ShieldUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class ParryDamageMixin {
    private static String key = "parry_window";
    ConfigEntries config = ShieldOverhaul.CONFIG;

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void $shield_overhaul_damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!(((LivingEntity) (Object) this) instanceof PlayerEntity playerEntity) || !(playerEntity.getStackInHand(playerEntity.getActiveHand()).getItem() instanceof ShieldItem)) return;

        ItemStack shield = playerEntity.getStackInHand(playerEntity.getActiveHand());
        boolean isParrying = ShieldUtils.isParrying(shield, playerEntity.getWorld());
        long window = (shield.getNbt().getLong(key) - playerEntity.getWorld().getTime());

        float damageReduction = Math.max(0, Math.min(1, (window/config.parry_abuse_cooldown_secs)/100));
        playerEntity.sendMessage(Text.literal("parry damage reduction: "+ String.valueOf(damageReduction)));

        if (isParrying) {
            cir.cancel();
        }
    }
}
