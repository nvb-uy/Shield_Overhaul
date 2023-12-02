package elocindev.shield_overhaul.mixin;

import elocindev.shield_overhaul.ShieldOverhaul;
import elocindev.shield_overhaul.config.ConfigEntries;
import elocindev.shield_overhaul.util.ShieldUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.registry.Registries;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class PlayerEntityMixin {
    @Inject(method = "setSneaking", at = @At("HEAD"), cancellable = true)
    public void $shield_overhaul_setSneaking(boolean sneaking, CallbackInfo ci) {
        if (!(((LivingEntity) (Object) this) instanceof PlayerEntity playerEntity) || !(playerEntity.getStackInHand(playerEntity.getActiveHand()).getItem() instanceof ShieldItem)) return;

        Hand shieldHand = playerEntity.getActiveHand();
        ItemStack shield = playerEntity.getStackInHand(shieldHand);

        ConfigEntries config = ShieldOverhaul.CONFIG;

        if (config.enable_parrying && (shield.getItem() instanceof ShieldItem) && !playerEntity.getItemCooldownManager().isCoolingDown(shield.getItem()) && playerEntity.isBlocking()) {
            if (config.blacklisted_shields.contains(Registries.ITEM.getId(shield.getItem()).toString()))
                return;

            if (ShieldUtils.isParrying(shield, playerEntity.getWorld())) {
                // This will reset the parry if the player happens to use the shield why the parry window is active
                // To prevent spam clicking for permanent parry
                ShieldUtils.resetParryWindow(shield);
                if (config.enable_parry_abuse_prevention) {
                    playerEntity.getItemCooldownManager().set(shield.getItem(), (int)(config.parry_abuse_cooldown_secs * 20));
                }
            } else {
                ShieldUtils.setParryWindow(shield, playerEntity.getWorld(), (int)(config.parry_window_secs * 20));
            }
        }
    }
}
