package elocindev.shield_overhaul.networking;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class ParryEffectS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        PlayerEntity target = client.world.getPlayerByUuid(buf.readUuid());

        // Todo: fix this line causing a LegacyRandomSource from multiple threads crash
        target.getWorld().playSound(target.getX(), target.getY(), target.getZ(), SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, 1, 1, false);

        for(int i = 0; i < 5; ++i) {
            double d = 1 * 0.02D;
            double e = 1 * 0.02D;
            double f = 1 * 0.02D;
            target.getWorld().addParticle(ParticleTypes.SNOWFLAKE, target.getParticleX(1.0D), target.getRandomBodyY(), target.getParticleZ(1.0D), d, e, f);
        }

        for(int i = 0; i < 15; ++i) {
            double d = 1 * 0.02D;
            double e = 1 * 0.02D;
            double f = 1 * 0.02D;
            target.getWorld().addParticle(ParticleTypes.ENCHANT, target.getParticleX(1.0D), target.getRandomBodyY(), target.getParticleZ(1.0D), d * 10, e * 10, f * 10);
        }
    }
}