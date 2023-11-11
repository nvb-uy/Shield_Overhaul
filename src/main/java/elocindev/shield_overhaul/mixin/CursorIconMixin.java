package elocindev.shield_overhaul.mixin;

import elocindev.shield_overhaul.ShieldOverhaul;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class CursorIconMixin {
    @Shadow
    private int scaledWidth;
    @Shadow private int scaledHeight;

    private static final Identifier OVERLAY = new Identifier(ShieldOverhaul.MODID, "textures/gui/shield.png");

    @Inject(at = @At("HEAD"), method = "renderHotbar")
    private void $shield_overhaul_renderHotbar(float tickDelta, DrawContext context, CallbackInfo ci) {
        int i = this.scaledWidth / 2;
        context.drawTexture(OVERLAY, i - 91-73, this.scaledHeight - 17, 0, 0, 60, 18, 60, 18);
    }
}