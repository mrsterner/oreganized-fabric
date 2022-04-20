package dev.mrsterner.oreganized.common.entities;

import dev.mrsterner.oreganized.common.registry.*;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class PrimedShrapnelBombEntity extends TntEntity {
    public PrimedShrapnelBombEntity(EntityType<? extends PrimedShrapnelBombEntity> entityType, World world) {
        super(entityType, world);
    }

    public PrimedShrapnelBombEntity(World world, double x, double y, double z, @Nullable LivingEntity livingEntity) {
        super(OEntityTypes.SHRAPNEL_BOMB_ENTITY, world);
        this.setPos(x, y, z);
        double d0 = world.random.nextDouble() * (double)((float)Math.PI * 2F);
        this.setVelocity(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(60);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
    }

    @Override
    public void tick() {
        if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
        }

        this.move(MovementType.SELF, this.getVelocity());
        this.setVelocity(this.getVelocity().multiply(0.98));
        if (this.onGround) {
            this.setVelocity(this.getVelocity().multiply(0.7, -0.5, 0.7));
        }

        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {
            this.discard();
            if (!this.world.isClient) {
                this.exploder();
            }
        } else {
            this.updateWaterState();
            if (this.world.isClient) {
                this.world.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 0.0, 0.0, 0.0);
            }
        }

    }


    protected void exploder() {
        this.world.createExplosion(this, this.getX(), this.getY() + 0.0625D, this.getZ(), 6.0F, Explosion.DestructionType.NONE);
        if(!this.world.isClient()) ((ServerWorld)this.world).spawnParticles((ParticleEffect) OParticleTypes.LEAD_SHRAPNEL, this.getX(), this.getY() +0.0625D , this.getZ() , 100, 0.0D , 0.0D , 0.0D, 5 );

        for (Entity entity :this.world.getOtherEntities(this, new Box(this.getX() - 30, this.getY() - 4, this.getZ() - 30,
                this.getX() + 30, this.getY() + 4, this.getZ() + 30))){
            int random = (int) (Math.random() * 100);
            boolean shouldPoison = false;
            if(entity.squaredDistanceTo(this) <= 4*4){
                shouldPoison = true;
            } else if (entity.squaredDistanceTo(this) <= 8*8) {
                if(random < 60) shouldPoison = true;
            } else if (entity.squaredDistanceTo(this) <= 15*15) {
                if(random < 30) shouldPoison = true;
            } else if (entity.squaredDistanceTo(this) <= 30*30) {
                if(random < 5) shouldPoison = true;
            }
            if(shouldPoison){
                if(entity instanceof LivingEntity livingEntity){
                    livingEntity.damage(DamageSource.MAGIC, 2);
                    livingEntity.addStatusEffect(new StatusEffectInstance(OStatusEffects.STUNNED , 40 * 20 ) );
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON , 260 ) );
                }
            }
        }
    }
}
