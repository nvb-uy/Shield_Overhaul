package elocindev.shield_overhaul.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import elocindev.shield_overhaul.ShieldOverhaul;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
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
    @Shadow
    private final MinecraftClient client;

    private static final Identifier SHIELD_ICONS = new Identifier(ShieldOverhaul.MODID, "textures/gui/shield.png");
    private static final Identifier ICONS = new Identifier("textures/gui/icons.png");

    public CursorIconMixin(MinecraftClient client) {
        this.client = client;
    }

    @Inject(at = @At("TAIL"), method = "renderCrosshair")
    private void $shield_overhaul_renderHotbar(DrawContext context, CallbackInfo ci) {
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.ONE_MINUS_DST_COLOR, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        if (this.client.options.getAttackIndicator().getValue() == AttackIndicator.CROSSHAIR && this.client.player.isHolding(Items.SHIELD)) {
            float f = this.client.player.getItemCooldownManager().getCooldownProgress(Items.SHIELD, 0.0F);
            boolean bl = false;
            if (this.client.targetedEntity != null && this.client.targetedEntity instanceof LivingEntity && f >= 1.0F) {
                bl = this.client.player.getAttackCooldownProgressPerTick() > 5.0F;
                bl &= this.client.targetedEntity.isAlive();
            }

            int j = this.scaledHeight / 2 - 7 + 16;
            int k = this.scaledWidth / 2 - 8;

            if (bl) {
                context.drawTexture(ICONS, k - 16, j - 14, 68, 94, 16, 16);
            } else if (f < 1.0F) {
                int l = (int)(f * 17.0F);
                context.drawTexture(ICONS, k - 16, j - 14, 36, 94, 16, 4);
                context.drawTexture(ICONS, k - 16, j - 14, 52, 94, l, 4);
            }
        }
        RenderSystem.defaultBlendFunc();

        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.ONE_MINUS_DST_COLOR, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        if (this.client.options.getAttackIndicator().getValue() == AttackIndicator.CROSSHAIR && this.client.player.isHolding(Items.SHIELD)) {
            float f = this.client.player.getItemCooldownManager().getCooldownProgress(Items.SHIELD, 0.0F);
            boolean bl = false;
            if (this.client.targetedEntity != null && this.client.targetedEntity instanceof LivingEntity && f >= 1.0F) {
                bl = this.client.player.getAttackCooldownProgressPerTick() > 5.0F;
                bl &= this.client.targetedEntity.isAlive();
            }

            int j = this.scaledHeight / 2 - 7 + 16;
            int k = this.scaledWidth / 2 - 8;

            if (bl) {
                context.drawTexture(ICONS, k - 16, j - 7, 68, 94, 16, 16);
            } else if (f < 1.0F) {
                int l = (int)(f * 17.0F);
                context.drawTexture(ICONS, k - 16, j - 7, 36, 94, 16, 4);
                context.drawTexture(ICONS, k - 16, j - 7, 52, 94, l, 4);
            }
        }
        RenderSystem.defaultBlendFunc();
    }
}