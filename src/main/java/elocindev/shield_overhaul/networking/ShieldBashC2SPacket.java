package elocindev.shield_overhaul.networking;

import elocindev.shield_overhaul.ShieldOverhaul;
import elocindev.shield_overhaul.entity.ShieldBashEntity;
import elocindev.shield_overhaul.registry.PacketRegistry;
import elocindev.shield_overhaul.util.MathUtils;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class ShieldBashC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {

        if (!player.getItemCooldownManager().isCoolingDown(player.getActiveItem().getItem()) && player.isBlocking()) {
                if (ShieldOverhaul.CONFIG.bash_only_on_ground && !player.isOnGround()) return;

                ShieldBashEntity entity = new ShieldBashEntity(player, player.world);
                entity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 2.0F, 0F);
                player.world.spawnEntity(entity);

                //ServerPlayNetworking.send(player, PacketRegistry.SHIELD_BASH_ANIMATION_PACKET, PacketByteBufs.create());

                Vec3d velocityVector = MathUtils.getLookingVec(player, 1.5f);
                player.addVelocity(velocityVector.x, velocityVector.y, velocityVector.z);
                player.velocityModified = true;

                player.getItemCooldownManager().set(player.getActiveItem().getItem(), 20);
                player.clearActiveItem();
        }
    }
}
