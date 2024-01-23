package elocindev.shield_overhaul.util;

import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ShieldUtils {
    private static String key = "parry_window"; 

    public static boolean isParrying(PlayerEntity player) {
        if (!(player.getActiveItem().getItem() instanceof ShieldItem)) return false;
        
        return isParrying(player.getActiveItem(), player.getWorld());
    }

    public static boolean isParrying(ItemStack stack, World world) {
        return stack.getNbt().getLong(key) > world.getTime();
    }

    public static void setParryWindow(ItemStack stack, World world, long ticks) {
        stack.getNbt().putLong(key, world.getTime() + ticks);
    }

    public static void resetParryWindow(ItemStack stack) {
        stack.getNbt().putLong(key, 0);
    }
}
