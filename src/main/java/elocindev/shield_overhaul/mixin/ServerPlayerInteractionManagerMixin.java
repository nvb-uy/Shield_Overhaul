package elocindev.shield_overhaul.mixin;

import elocindev.shield_overhaul.registry.ClientPacketRegistry;
import elocindev.shield_overhaul.registry.ServerPacketRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
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
import net.minecraft.registry.Registries;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {
    
    @Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)
    public void interactItem(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (player.hasStatusEffect(EffectRegistry.STUN_EFFECT)) {
            cir.setReturnValue(ActionResult.FAIL);
        }

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
