package elocindev.shield_overhaul.networking;

import elocindev.shield_overhaul.util.ShieldAnimationUtils;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

public class ShieldBashAnimationS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        
        PlayerEntity target = client.world.getPlayerByUuid(buf.readUuid());
        ShieldAnimationUtils.playShieldBashRight(target);
    }
}