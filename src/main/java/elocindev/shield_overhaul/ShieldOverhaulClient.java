package elocindev.shield_overhaul;

import elocindev.shield_overhaul.event.ShieldInteraction;
import elocindev.shield_overhaul.registry.EntityRegistry;
import elocindev.shield_overhaul.registry.PacketRegistry;
import net.fabricmc.api.ClientModInitializer;

public class ShieldOverhaulClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRegistry.registerRenderers();
        PacketRegistry.registerS2CPackets();
        ShieldInteraction.bashingPacketRegistry();
    }
}
