package dev.mrsterner.oreganized;

import dev.mrsterner.oreganized.client.particle.CustomDrippingParticle;
import dev.mrsterner.oreganized.client.renderer.ShrapnelBombRenderer;
import dev.mrsterner.oreganized.common.block.CrystalGlassBase;
import dev.mrsterner.oreganized.common.block.CrystalGlassPaneColoredBlock;
import dev.mrsterner.oreganized.common.registry.OEntityTypes;
import dev.mrsterner.oreganized.common.registry.OObjects;
import dev.mrsterner.oreganized.common.registry.OParticleTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.particle.ExplosionSmokeParticle;
import net.minecraft.client.render.RenderLayer;

public class OreganizedClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(OParticleTypes.DRIPPING_LEAD, CustomDrippingParticle.LeadHangProvider::new);
        ParticleFactoryRegistry.getInstance().register(OParticleTypes.FALLING_LEAD, CustomDrippingParticle.LeadFallProvider::new);
        ParticleFactoryRegistry.getInstance().register(OParticleTypes.LANDING_LEAD, CustomDrippingParticle.LeadLandProvider::new);
        ParticleFactoryRegistry.getInstance().register(OParticleTypes.LEAD_SHRAPNEL, ExplosionSmokeParticle.Factory::new);

        EntityRendererRegistry.register(OEntityTypes.SHRAPNEL_BOMB_ENTITY, ShrapnelBombRenderer::new);

        for(Block block : OObjects.BLOCKS.keySet()) {
            if(block instanceof CrystalGlassBase crystalGlassBase) {
                BlockRenderLayerMap.INSTANCE.putBlock(crystalGlassBase, RenderLayer.getTranslucent());
            }
            if(block instanceof CrystalGlassPaneColoredBlock crystalGlassPaneColoredBlock) {
                BlockRenderLayerMap.INSTANCE.putBlock(crystalGlassPaneColoredBlock, RenderLayer.getTranslucent());
            }

        }


    }

}
