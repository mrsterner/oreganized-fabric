package dev.mrsterner.oreganized.common.registry;

import dev.mrsterner.oreganized.Oreganized;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class OParticleTypes {
    private static final Map<ParticleType<?>, Identifier> PARTICLE_TYPES = new LinkedHashMap<>();
    public static final ParticleType<DefaultParticleType> DRIPPING_LEAD = register("cauldron_bubble", FabricParticleTypes.simple());
    public static final ParticleType<DefaultParticleType> LEAD_SHRAPNEL = register("lead_shrapnel", FabricParticleTypes.simple());

    private static <T extends ParticleEffect> ParticleType<T> register(String name, ParticleType<T> type) {
        PARTICLE_TYPES.put(type, new Identifier(Oreganized.MODID, name));
        return type;
    }

    public static void init() {
        PARTICLE_TYPES.keySet().forEach(particleType -> Registry.register(Registry.PARTICLE_TYPE, PARTICLE_TYPES.get(particleType), particleType));
    }
}
