package elocindev.shield_overhaul.networking;

import elocindev.shield_overhaul.registry.ClientPacketRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;

public class SendAnimationC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {

        String animationName = buf.readString();
        for (ServerPlayerEntity target : PlayerLookup.tracking((ServerWorld)player.getWorld(), new ChunkPos((int)player.getPos().x / 16, (int)player.getPos().z / 16))) {
            ServerPlayNetworking.send(target, ClientPacketRegistry.SHIELD_BASH_ANIMATION_PACKET, PacketByteBufs.create().writeUuid(player.getUuid()).writeString(animationName));
        }
    }
}
