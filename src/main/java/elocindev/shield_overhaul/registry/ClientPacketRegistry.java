package elocindev.shield_overhaul.registry;

import elocindev.shield_overhaul.ShieldOverhaul;
import elocindev.shield_overhaul.networking.ParryEffectS2CPacket;
import elocindev.shield_overhaul.networking.ShieldBashAnimationS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

public class ClientPacketRegistry {
    public static final Identifier SHIELD_BASH_PACKET = new Identifier(ShieldOverhaul.MODID, "shield_bash_packet");
    public static final Identifier SHIELD_BASH_ANIMATION_PACKET = new Identifier(ShieldOverhaul.MODID, "shield_bash_animation_packet");

    public static final Identifier PARRY_EFFECT_S2C_PACKET = new Identifier(ShieldOverhaul.MODID, "parry_effect_s2c_packet");

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(SHIELD_BASH_ANIMATION_PACKET, ShieldBashAnimationS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(PARRY_EFFECT_S2C_PACKET, ParryEffectS2CPacket::receive);
    }
}
