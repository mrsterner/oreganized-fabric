package dev.mrsterner.oreganized.client.renderer;

import dev.mrsterner.oreganized.common.registry.OObjects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.TntMinecartEntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.TntEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class ShrapnelBombRenderer extends EntityRenderer<TntEntity> {
    public ShrapnelBombRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.shadowRadius = 0.5F;
    }

    @Override
    public void render(TntEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.translate(0.0D, 0.5D, 0.0D);
        int i = entity.getFuse();
        if ((float)i - tickDelta + 1.0F < 10.0F) {
            float f = 1.0F - ((float)i - tickDelta + 1.0F) / 10.0F;
            f = MathHelper.clamp(f, 0.0F, 1.0F);
            f *= f;
            f *= f;
            float f1 = 1.0F + f * 0.3F;
            matrices.scale(f1, f1, f1);
        }

        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
        matrices.translate(-0.5D, -0.5D, 0.5D);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
        TntMinecartEntityRenderer.renderFlashingBlock(OObjects.SHRAPNEL_BOMB.getDefaultState(), matrices, vertexConsumers, light, i / 5 % 2 == 0);
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(TntEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
