package elocindev.shield_overhaul.networking;

import elocindev.shield_overhaul.util.ShieldAnimationUtils;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.particle.TotemParticle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class ParryEffectS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        PlayerEntity target = client.world.getPlayerByUuid(buf.readUuid());

        target.getWorld().playSound(target.getX(), target.getY(), target.getZ(), SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, 1, 1, true);

        for(int i = 0; i < 5; ++i) {
            double d = target.getRandom().nextGaussian() * 0.02D;
            double e = target.getRandom().nextGaussian() * 0.02D;
            double f = target.getRandom().nextGaussian() * 0.02D;
            target.getWorld().addParticle(ParticleTypes.SCULK_SOUL, target.getParticleX(1.0D), target.getRandomBodyY(), target.getParticleZ(1.0D), d * -5, e, f * -5);
        }

        for(int i = 0; i < 5; ++i) {
            double d = target.getRandom().nextGaussian() * 0.02D;
            double e = target.getRandom().nextGaussian() * 0.02D;
            double f = target.getRandom().nextGaussian() * 0.02D;
            target.getWorld().addParticle(ParticleTypes.SNOWFLAKE, target.getParticleX(1.0D), target.getRandomBodyY(), target.getParticleZ(1.0D), d, e, f);
        }

        for(int i = 0; i < 15; ++i) {
            double d = target.getRandom().nextGaussian() * 0.02D;
            double e = target.getRandom().nextGaussian() * 0.02D;
            double f = target.getRandom().nextGaussian() * 0.02D;
            target.getWorld().addParticle(ParticleTypes.ENCHANT, target.getParticleX(1.0D), target.getRandomBodyY(), target.getParticleZ(1.0D), d * 10, e * 10, f * 10);
        }
    }
}