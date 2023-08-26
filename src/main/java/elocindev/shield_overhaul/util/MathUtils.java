package elocindev.shield_overhaul.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MathUtils {
    public static Vec3d getLookingVec(PlayerEntity player, float speed) {
        float yaw = player.getYaw(); float pitch = player.getPitch();
        
        float x = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float y = -MathHelper.sin(pitch * 0.017453292F);
        float z = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        
        float m = MathHelper.sqrt(x * x + y * y + z * z);
        float n = 3.0F * ((1.0F) / 4.0F);

        return new Vec3d(x*n/m, y*n/m, z*n/m);
    }
}
