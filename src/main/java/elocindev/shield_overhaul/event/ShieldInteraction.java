package elocindev.shield_overhaul.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

public class ShieldInteraction {
    public static void bashingPacketRegistry() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.options == null) return;

            ClientPlayerEntity player = client.player;

            if (client.options.attackKey.isPressed())  {
                if (player.isBlocking()) {
                    if (player.getItemCooldownManager().isCoolingDown(player.getMainHandStack().getItem()))
                        player.sendMessage(Text.of("Packet should be sent here"));
                }
            }
        });
    }
}
