package dev.mrsterner.oreganized.client.particle;


import dev.mrsterner.oreganized.common.registry.OParticleTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class CustomDrippingParticle extends SpriteBillboardParticle {
    private final Fluid fluid;
    protected boolean isGlowing;
    CustomDrippingParticle(ClientWorld pLevel, double pX, double pY, double pZ, Fluid fluid) {
        super(pLevel, pX, pY, pZ);
        this.setBoundingBoxSpacing(0.01F, 0.01F);
        this.gravityStrength = 0.06F;
        this.fluid = fluid;
    }

    public Fluid getFluid() {
        return this.fluid;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getBrightness(float tint) {
        return this.isGlowing ? 240 : super.getBrightness(tint);
    }


    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        this.preMoveUpdate();
        if (!this.dead) {
            this.velocityY -= (double)this.gravityStrength;
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.postMoveUpdate();
            if (!this.dead) {
                this.velocityX *= (double)0.98F;
                this.velocityY *= (double)0.98F;
                this.velocityZ *= (double)0.98F;
                BlockPos blockpos = new BlockPos(this.x, this.y, this.z);
                FluidState fluidstate = this.world.getFluidState(blockpos);
                if (fluidstate.getFluid() == this.fluid && this.y < (double)((float)blockpos.getY() + fluidstate.getHeight(this.world, blockpos))) {
                    this.markDead();
                }

            }
        }
    }

    protected void preMoveUpdate() {
        if (this.maxAge-- <= 0) {
            this.markDead();
        }

    }

    protected void postMoveUpdate() {
    }

    @Environment(EnvType.CLIENT)
    static class CoolingDripHangParticle extends DripHangParticle {
        CoolingDripHangParticle(ClientWorld world, double x, double y, double z, Fluid fluid, ParticleEffect nextParticle) {
            super(world, x, y, z, fluid, nextParticle);
        }

        protected void preMoveUpdate() {
            this.red = Math.max( 0.55F - ((float) (40 - this.maxAge) / 10.0F) / 8.0F , 0.35F );
            this.green = Math.max(0.44F - ((float)(40 - this.maxAge) / 10.0F) / 8.0F, 0.24F);
            this.blue = Math.max(0.62F - ((float)(40 - this.maxAge) / 10.0F) / 8.0F, 0.42F);
            super.preMoveUpdate();
        }
    }

    @Environment(EnvType.CLIENT)
    static class DripHangParticle extends CustomDrippingParticle{
        private final ParticleEffect fallingParticle;

        DripHangParticle(ClientWorld world, double pX, double pY, double pZ, Fluid pType, ParticleEffect pFallingParticle) {
            super(world, pX, pY, pZ, pType);
            this.fallingParticle = pFallingParticle;
            this.gravityStrength *= 0.02F;
            this.maxAge = 40;
        }

        protected void preMoveUpdate() {
            if (this.maxAge-- <= 0) {
                this.markDead();
                this.world.addParticle(this.fallingParticle, this.x, this.y, this.z, this.velocityX, this.velocityY, this.velocityZ);
            }

        }

        protected void postMoveUpdate() {
            this.velocityX *= 0.02D;
            this.velocityY *= 0.02D;
            this.velocityZ *= 0.02D;
        }
    }

    @Environment(EnvType.CLIENT)
    static class DripLandParticle extends CustomDrippingParticle{
        DripLandParticle(ClientWorld world, double x, double y, double z, Fluid fluid) {
            super(world, x, y, z, fluid);
            this.maxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
        }
    }

    @Environment(EnvType.CLIENT)
    static class FallAndLandParticle extends FallingParticle {
        protected final ParticleEffect landParticle;

        FallAndLandParticle(ClientWorld world, double pX, double pY, double pZ, Fluid pType, ParticleEffect pLandParticle) {
            super(world, pX, pY, pZ, pType);
            this.landParticle = pLandParticle;
        }

        protected void postMoveUpdate() {
            if (this.onGround) {
                this.markDead();
                this.world.addParticle(this.landParticle, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
            }

        }
    }

    @Environment(EnvType.CLIENT)
    static class FallingParticle extends CustomDrippingParticle{
        FallingParticle(ClientWorld world, double x, double y, double z, Fluid fluid) {
            this(world, x, y, z, fluid, (int)(64.0D / (Math.random() * 0.8D + 0.2D)));
        }

        FallingParticle(ClientWorld pLevel, double pX, double pY, double pZ, Fluid pType, int pLifetime) {
            super(pLevel, pX, pY, pZ, pType);
            this.maxAge = pLifetime;
        }

        protected void postMoveUpdate() {
            if (this.onGround) {
                this.markDead();
            }

        }
    }

    @Environment(EnvType.CLIENT)
    public static class LeadFallProvider implements ParticleFactory<DefaultParticleType> {
        protected final SpriteProvider sprite;

        public LeadFallProvider(SpriteProvider pSprites) {
            this.sprite = pSprites;
        }

        public Particle createParticle(DefaultParticleType pType, ClientWorld pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            CustomDrippingParticle.FallAndLandParticle dripparticle = new CustomDrippingParticle.FallAndLandParticle(pLevel, pX, pY, pZ, Fluids.LAVA, (ParticleEffect) OParticleTypes.LANDING_LEAD);
            dripparticle.setColor(0.35F, 0.24F, 0.43F);
            dripparticle.setSprite(this.sprite);
            return dripparticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class LeadHangProvider implements ParticleFactory<DefaultParticleType> {
        protected final SpriteProvider sprite;

        public LeadHangProvider(SpriteProvider pSprites) {
            this.sprite = pSprites;
        }

        public Particle createParticle(DefaultParticleType pType, ClientWorld pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            CustomDrippingParticle.CoolingDripHangParticle dripparticle$coolingdriphangparticle = new CustomDrippingParticle.CoolingDripHangParticle(pLevel, pX, pY, pZ, Fluids.LAVA, (ParticleEffect)  OParticleTypes.FALLING_LEAD);
            dripparticle$coolingdriphangparticle.setColor(0.35F, 0.24F, 0.43F);
            dripparticle$coolingdriphangparticle.setSprite(this.sprite);
            return dripparticle$coolingdriphangparticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class LeadLandProvider implements ParticleFactory<DefaultParticleType> {
        protected final SpriteProvider sprite;

        public LeadLandProvider(SpriteProvider pSprites) {
            this.sprite = pSprites;
        }

        public Particle createParticle(DefaultParticleType pType, ClientWorld pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            CustomDrippingParticle dripparticle = new CustomDrippingParticle.DripLandParticle(pLevel, pX, pY, pZ, Fluids.LAVA);
            dripparticle.setColor(0.35F, 0.24F, 0.43F);
            dripparticle.setSprite(this.sprite);
            return dripparticle;
        }
    }


}