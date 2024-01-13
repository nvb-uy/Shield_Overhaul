package elocindev.shield_overhaul.mixin;

import elocindev.shield_overhaul.ShieldOverhaul;
import elocindev.shield_overhaul.config.ConfigEntries;
import elocindev.shield_overhaul.registry.ClientPacketRegistry;
import elocindev.shield_overhaul.registry.ServerPacketRegistry;
import elocindev.shield_overhaul.util.ShieldUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.ChunkPos;
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
        if (!(player.getStackInHand(player.getActiveHand()).getItem() instanceof ShieldItem)) return;

        Hand shieldHand = player.getActiveHand();
        ItemStack shield = player.getStackInHand(shieldHand);

        for (ServerPlayerEntity target : PlayerLookup.tracking((ServerWorld)player.getWorld(), new ChunkPos((int)player.getPos().x / 16, (int)player.getPos().z / 16))) {
            ServerPlayNetworking.send(target, ClientPacketRegistry.SHIELD_BASH_ANIMATION_PACKET, PacketByteBufs.create().writeUuid(player.getUuid()).writeString(shieldHand == Hand.MAIN_HAND ? "parry_right" : "parry_left"));
        }

        // Bumped this up for testing purposes but should lower it back down to 20
        ShieldUtils.setParryWindow(shield, player.getWorld(), (int)(config.parry_window_secs * 40));
    }
}
