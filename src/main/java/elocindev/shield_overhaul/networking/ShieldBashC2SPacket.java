package elocindev.shield_overhaul.networking;

import elocindev.shield_overhaul.entity.ShieldBashEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class ShieldBashC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {

        if (!player.getItemCooldownManager().isCoolingDown(player.getActiveItem().getItem()) && player.isBlocking()) {
            
                ShieldBashEntity entity = new ShieldBashEntity(player, player.world);
                entity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 2.0F, 0F);
                player.world.spawnEntity(entity);

                player.getItemCooldownManager().set(player.getActiveItem().getItem(), 20);
        }
    }
}
