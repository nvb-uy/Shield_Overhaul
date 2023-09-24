package elocindev.shield_overhaul.registry;

import elocindev.shield_overhaul.ShieldOverhaul;
import elocindev.shield_overhaul.entity.ShieldBashEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EntityRegistry {
    public static final EntityType<ShieldBashEntity> SHIELD_BASH_ENTITY = Registry.register(
    Registries.ENTITY_TYPE, new Identifier(ShieldOverhaul.MODID, "shield_bash_entity"),
    FabricEntityTypeBuilder.<ShieldBashEntity>create(SpawnGroup.MISC, elocindev.shield_overhaul.entity.ShieldBashEntity::new)
            .dimensions(EntityDimensions.fixed(2F, 2F)).trackRangeBlocks(4).trackedUpdateRate(10).build());


    public static void reg() { }
}
