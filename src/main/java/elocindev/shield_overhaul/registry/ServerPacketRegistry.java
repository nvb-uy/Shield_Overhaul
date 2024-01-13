package elocindev.shield_overhaul.registry;

import elocindev.shield_overhaul.ShieldOverhaul;
import elocindev.shield_overhaul.networking.ParryEffectC2SPacket;
import elocindev.shield_overhaul.networking.ShieldBashC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ServerPacketRegistry {
    public static final Identifier SHIELD_BASH_PACKET = new Identifier(ShieldOverhaul.MODID, "shield_bash_packet");
    public static final Identifier PARRY_EFFECT_PACKET = new Identifier(ShieldOverhaul.MODID, "parry_effect_packet");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(SHIELD_BASH_PACKET, ShieldBashC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PARRY_EFFECT_PACKET, ParryEffectC2SPacket::receive);
    }
}
