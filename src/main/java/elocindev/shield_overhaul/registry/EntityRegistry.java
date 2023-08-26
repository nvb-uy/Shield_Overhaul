package elocindev.shield_overhaul.registry;

import elocindev.shield_overhaul.ShieldOverhaul;
import elocindev.shield_overhaul.entity.ShieldBashEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityRegistry {
    public static final EntityType<ShieldBashEntity> SHIELD_BASH_ENTITY = Registry.register(
    Registry.ENTITY_TYPE, new Identifier(ShieldOverhaul.MODID, "shield_bash_entity"),
    FabricEntityTypeBuilder.<ShieldBashEntity>create(SpawnGroup.MISC, elocindev.shield_overhaul.entity.ShieldBashEntity::new)
            .dimensions(EntityDimensions.fixed(1F, 1F)).trackRangeBlocks(4).trackedUpdateRate(10).build());


    public static void registerRenderers() {
        EntityRendererRegistry.register(EntityRegistry.SHIELD_BASH_ENTITY, (context) ->
                new ProjectileEntityRenderer<ShieldBashEntity>(context) {
                @Override
                public Identifier getTexture(ShieldBashEntity entity) {
                    return new Identifier(ShieldOverhaul.MODID, "textures/misc/shield_bash.png");
                }
            });
    }
}
