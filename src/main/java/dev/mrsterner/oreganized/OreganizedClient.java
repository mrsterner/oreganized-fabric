package dev.mrsterner.oreganized;

import dev.mrsterner.oreganized.client.particle.CustomDrippingParticle;
import dev.mrsterner.oreganized.client.particle.LeadShrapnelParticle;
import dev.mrsterner.oreganized.common.registry.OParticleTypes;
import dev.mrsterner.oreganized.common.registry.OSounds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.ExplosionSmokeParticle;

public class OreganizedClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(OParticleTypes.DRIPPING_LEAD, CustomDrippingParticle.LeadHangProvider::new);
        ParticleFactoryRegistry.getInstance().register(OParticleTypes.FALLING_LEAD, CustomDrippingParticle.LeadFallProvider::new);
        ParticleFactoryRegistry.getInstance().register(OParticleTypes.LANDING_LEAD, CustomDrippingParticle.LeadLandProvider::new);
        ParticleFactoryRegistry.getInstance().register(OParticleTypes.LEAD_SHRAPNEL, ExplosionSmokeParticle.Factory::new);
    }
}
