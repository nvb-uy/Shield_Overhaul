package elocindev.shield_overhaul.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MathUtils {
    public static Vec3d getLookingVec(PlayerEntity player, float speed) {
        float yaw = player.getYaw(); float pitch = player.getPitch();
        
        float radian = 0.017453292F;

        float x = -MathHelper.sin(yaw * radian) * MathHelper.cos(pitch * radian);
        float y = -MathHelper.sin(pitch * radian);
        float z = MathHelper.cos(yaw * radian) * MathHelper.cos(pitch * radian);
        
        float m = MathHelper.sqrt(x * x + y * y + z * z);
        float n = 0.75F;

        return new Vec3d(x*n/m, y*n/m * 0.5F, z*n/m);
    }
}