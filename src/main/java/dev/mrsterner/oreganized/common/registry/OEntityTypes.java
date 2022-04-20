package dev.mrsterner.oreganized.common.registry;

import dev.mrsterner.oreganized.Oreganized;
import dev.mrsterner.oreganized.common.entities.PrimedShrapnelBombEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class OEntityTypes {
    private static final Map<EntityType<?>, Identifier> ENTITY_TYPES = new LinkedHashMap<>();

    public static EntityType<PrimedShrapnelBombEntity> SHRAPNEL_BOMB_ENTITY = register("shrapnel", PrimedShrapnelBombEntity::new);;


    private static <T extends Entity> EntityType<T> register(String id, EntityType.EntityFactory<T> factory) {
        EntityType<T> type = FabricEntityTypeBuilder.create(SpawnGroup.MISC, factory).dimensions(new EntityDimensions(0.98F, 0.98F, true)).fireImmune().trackRangeBlocks(10).trackedUpdateRate(10).build();
        ENTITY_TYPES.put(type, new Identifier(Oreganized.MODID, id));
        return type;
    }

    public static void init() {
        ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registry.ENTITY_TYPE, ENTITY_TYPES.get(entityType), entityType));
    }
}
