package elocindev.shield_overhaul.registry;

import elocindev.shield_overhaul.ShieldOverhaul;
import elocindev.shield_overhaul.networking.ShieldBashC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ServerPacketRegistry {
    public static final Identifier SHIELD_BASH_PACKET = new Identifier(ShieldOverhaul.MODID, "shield_bash_packet");
    public static final Identifier SHIELD_BASH_ANIMATION_PACKET = new Identifier(ShieldOverhaul.MODID, "shield_bash_animation_packet");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(SHIELD_BASH_PACKET, ShieldBashC2SPacket::receive);
    }
}
