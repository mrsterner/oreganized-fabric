package dev.mrsterner.oreganized.common.registry;

import dev.mrsterner.oreganized.Oreganized;
import dev.mrsterner.oreganized.common.block.entity.ExposerBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class OBlockEntityTypes {
    private static final Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();


    public static final BlockEntityType<ExposerBlockEntity> EXPOSER_BLOCK_ENTITY = register("exposer", FabricBlockEntityTypeBuilder.create(ExposerBlockEntity::new, OObjects.EXPOSER).build(null));


    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        BLOCK_ENTITY_TYPES.put(type, new Identifier(Oreganized.MODID, name));
        return type;
    }
    public static void init() {
        BLOCK_ENTITY_TYPES.keySet().forEach(blockEntityType -> Registry.register(Registry.BLOCK_ENTITY_TYPE, BLOCK_ENTITY_TYPES.get(blockEntityType), blockEntityType));
    }
}
