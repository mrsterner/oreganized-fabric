package dev.mrsterner.oreganized.common.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import org.jetbrains.annotations.Nullable;

public class ODamageSource {
    public static DamageSource MOLTEN_LEAD = new LeadDamageSource("molten_lead").setFire();


    private static class LeadDamageSource extends DamageSource{

        public LeadDamageSource(String name) {
            super(name);
        }

        public static DamageSource causeLeadProjectileDamage(Entity arrow, @Nullable Entity indirectEntityIn) {
            return (new ProjectileDamageSource("lead_nugget", arrow, indirectEntityIn)).setProjectile();
        }
    }
}
