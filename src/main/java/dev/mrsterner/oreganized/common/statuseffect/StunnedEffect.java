package dev.mrsterner.oreganized.common.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.Vec3d;

public class StunnedEffect extends StatusEffect {
    public StunnedEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.isOnGround() || entity.isTouchingWater()) {
            entity.setVelocity(Vec3d.ZERO);
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int pDuration, int pAmplifier) {
        return true;
    }
    /*TODO Mixin
    @SubscribeEvent
    public static void onInputUpdate(MovementInputUpdateEvent event) {
        Input input = event.getInput();
        if (event.getPlayer().hasEffect(ModPotions.STUNNED)) {
            input.up = false;
            input.down = false;
            input.left = false;
            input.right = false;
            input.forwardImpulse = 0;
            input.leftImpulse = 0;
            input.jumping = false;
            input.shiftKeyDown = false;
        }
    }

     */
}
