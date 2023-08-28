package elocindev.shield_overhaul.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
        Hand shieldHand = player.getActiveHand();
        ItemStack shield = player.getStackInHand(shieldHand);
        
        if (shield.getItem() instanceof ShieldItem && !player.getItemCooldownManager().isCoolingDown(shield.getItem())) {
            if (ShieldUtils.isParrying(stack, world)) {
                // This will reset the parry if the player happens to use the shield why the parry window is active
                // To prevent spam clicking for permanent parry
                ShieldUtils.resetParryWindow(stack);
            } else {
                ShieldUtils.setParryWindow(stack, world, 10);;
            }
        }
    }
}
