package elocindev.shield_overhaul.util;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ShieldUtils {
    public static boolean isParrying(ItemStack stack, World world) {
        return stack.getNbt().getLong("parry_window") > world.getTime();
    }

    public static void setParryWindow(ItemStack stack, World world, long ticks) {
        stack.getNbt().putLong("parry_window", world.getTime() + ticks);
    }

    public static void resetParryWindow(ItemStack stack) {
        stack.getNbt().putLong("parry_window", 0);
    }
}
