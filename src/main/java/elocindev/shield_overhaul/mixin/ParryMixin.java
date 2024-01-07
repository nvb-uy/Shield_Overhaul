package elocindev.shield_overhaul.mixin;

import elocindev.shield_overhaul.ShieldOverhaul;
import elocindev.shield_overhaul.config.ConfigEntries;
import elocindev.shield_overhaul.util.ShieldUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ParryMixin {
    ConfigEntries config = ShieldOverhaul.CONFIG;

    @Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)
    public void interactItem(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        Hand shieldHand = player.getActiveHand();
        ItemStack shield = player.getStackInHand(shieldHand);
        ShieldOverhaul.LOGGER.info("Parry re-applied;" + (int)(config.parry_window_secs * 20 * 4));
        ShieldUtils.setParryWindow(shield, player.getWorld(), (int)(config.parry_window_secs * 20));
    }
}
