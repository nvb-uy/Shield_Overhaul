package elocindev.shield_overhaul.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import elocindev.shield_overhaul.ShieldOverhaul;
import elocindev.shield_overhaul.config.ConfigEntries;
import elocindev.shield_overhaul.registry.EffectRegistry;
import elocindev.shield_overhaul.util.ShieldUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {
    
    @Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)
    public void interactItem(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (player.hasStatusEffect(EffectRegistry.STUN_EFFECT)) {
            cir.setReturnValue(ActionResult.FAIL);
        }

        Hand shieldHand = player.getActiveHand();
        ItemStack shield = player.getStackInHand(shieldHand);
        ConfigEntries config = ShieldOverhaul.CONFIG;
        
        if (config.enable_parrying && shield.getItem() instanceof ShieldItem && !player.getItemCooldownManager().isCoolingDown(shield.getItem())) {
            if (ShieldUtils.isParrying(stack, world)) {
                // This will reset the parry if the player happens to use the shield why the parry window is active
                // To prevent spam clicking for permanent parry
                ShieldUtils.resetParryWindow(stack);
                if (config.enable_parry_abuse_prevention) {
                    player.getItemCooldownManager().set(stack.getItem(), (int)(config.parry_abuse_cooldown_secs * 20));
                }
            } else {
                ShieldUtils.setParryWindow(stack, world, (int)(config.parry_window_secs * 20));
            }
        }
    }
}
