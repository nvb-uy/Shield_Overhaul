package elocindev.shield_overhaul;

import elocindev.shield_overhaul.event.ShieldInteraction;
import net.fabricmc.api.ClientModInitializer;

public class ShieldOverhaulClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        
        ShieldInteraction.bashingPacketRegistry();
    }
}
