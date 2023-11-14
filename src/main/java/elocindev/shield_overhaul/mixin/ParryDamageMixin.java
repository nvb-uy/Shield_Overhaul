package elocindev.shield_overhaul.mixin;

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

    // stack.getNbt().getLong(key)
    // ||
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void $shield_overhaul_damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {

        if (!(((LivingEntity) (Object) this) instanceof PlayerEntity playerEntity) || !(playerEntity.getStackInHand(playerEntity.getActiveHand()).getItem() instanceof ShieldItem)) return;
        ItemStack shield = playerEntity.getStackInHand(playerEntity.getActiveHand());

        playerEntity.sendMessage(Text.literal("parry window: "+ String.valueOf(shield.getNbt().getLong(key))));
        playerEntity.sendMessage(Text.literal(String.valueOf("time: " + playerEntity.getWorld().getTime())));

        playerEntity.sendMessage(Text.literal(String.valueOf("isParrying: " + String.valueOf(ShieldUtils.isParrying(shield, playerEntity.getWorld())))));
        boolean isParrying = ShieldUtils.isParrying(shield, playerEntity.getWorld());
        if (isParrying) {
            cir.cancel();
        }
    }
}
