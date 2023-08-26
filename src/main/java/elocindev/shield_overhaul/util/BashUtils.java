package elocindev.shield_overhaul.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class BashUtils {
    public static void doBash(PlayerEntity player, float speed) {
        float yaw = MathHelper.DEGREES_PER_RADIAN * (player.getYaw() + 90); float pitch = MathHelper.DEGREES_PER_RADIAN * (player.getPitch() + 90);
        double x = -MathHelper.sin(yaw) * MathHelper.cos(pitch); double y = -MathHelper.sin(pitch); double z = MathHelper.cos(yaw) * MathHelper.cos(pitch);

        Vec3d direction = new Vec3d(x, y, z).normalize();

        player.setVelocity(direction.x * speed, direction.y * speed, direction.z * speed);
    }
}
