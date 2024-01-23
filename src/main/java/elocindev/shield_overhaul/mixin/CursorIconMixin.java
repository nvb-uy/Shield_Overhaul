package elocindev.shield_overhaul.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import elocindev.shield_overhaul.ShieldOverhaul;
import elocindev.shield_overhaul.util.ShieldUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
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

    private static final Identifier SHIELD_ICONS = new Identifier(ShieldOverhaul.MODID, "textures/gui/shield_sprites.png");
    private static final Identifier ICONS = new Identifier("textures/gui/icons.png");

    public CursorIconMixin(MinecraftClient client) {
        this.client = client;
    }

    @Inject(at = @At("TAIL"), method = "renderCrosshair")
    private void $shield_overhaul_renderHotbar(DrawContext context, CallbackInfo ci) {
        if (this.client.options.getAttackIndicator().getValue() == AttackIndicator.CROSSHAIR && this.client.player.isHolding(Items.SHIELD)) {
            drawBashShield(context);
            drawParryShield(context);
        }
        RenderSystem.defaultBlendFunc();
    }

    private void drawBashShield(DrawContext context) {
        float f = this.client.player.getItemCooldownManager().getCooldownProgress(Items.SHIELD, 0.0F);
        boolean bl = false;
        if (this.client.targetedEntity != null && this.client.targetedEntity instanceof LivingEntity && f >= 1.0F) {
            ItemStack shield = this.client.player.getStackInHand(client.player.getActiveHand());
            //bl = ShieldUtils.isParrying(shield, client.player.getWorld());
            bl = this.client.player.getAttackCooldownProgressPerTick() > 5.0f;
            bl &= this.client.targetedEntity.isAlive();
        }

        int j = this.scaledHeight / 2 - 7 + 16;
        int k = this.scaledWidth / 2 - 8;

        // Bash shield
        if (bl) {
            context.drawTexture(SHIELD_ICONS, k - 13, j - 19, 0, 56, 16, 18);
        } else if (f < 1.0F) {
            int l = (int)(f * 16.0f);
            context.drawTexture(SHIELD_ICONS, k - 13, j - 19, 0, 56, 16, 18);
            context.drawTexture(SHIELD_ICONS, k - 13, j - 19, 16, 56, l, 18);
        }
    }

    private void drawParryShield(DrawContext context) {
        boolean parrying = false;
        if (this.client != null && 1 >= 1.0F) {
            ItemStack shield = this.client.player.getStackInHand(client.player.getActiveHand());
            parrying = ShieldUtils.isParrying(client.player);
        }

        int j = this.scaledHeight / 2 - 7 + 16;
        int k = this.scaledWidth / 2 - 8;

        // Parry shield
        if (parrying) {
            context.drawTexture(SHIELD_ICONS, k + 15, j - 17, 0, 0, 16, 18);
            context.drawTexture(SHIELD_ICONS, k + 16, j - 17, 16, 0, 16, 18);
        } else {
            context.drawTexture(SHIELD_ICONS, k + 15, j - 17, 0, 0, 16, 18);
        }
    }

}