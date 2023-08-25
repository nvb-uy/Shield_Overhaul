package elocindev.shield_overhaul.registry;

import elocindev.shield_overhaul.ShieldOverhaul;
import elocindev.shield_overhaul.entity.ShieldBashEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
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
}
