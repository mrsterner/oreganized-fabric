package dev.mrsterner.oreganized.common.registry;

import com.google.common.base.Supplier;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Lazy;

public enum OMaterials implements ToolMaterial {
        LEAD(0, 150, 7.0F, 1.5F, 7, () -> {
            return Ingredient.fromTag(OTags.LEAD_SOURCE_ITEMTAG);
        }),
        ELECTRUM(4, 1411, 8.2F, 4.0F, 15, () -> {
            return Ingredient.ofItems(OObjects.ELECTRUM_INGOT);
        });

        private final int harvestLevel;
        private final int durability;
        private final float efficiency;
        private final float attackDamage;
        private final int enchantability;
        private final Lazy<Ingredient> repairMaterial;

    OMaterials(int harvestLevelIn, int durability, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
            this.harvestLevel = harvestLevelIn;
            this.durability = durability;
            this.efficiency = efficiencyIn;
            this.attackDamage = attackDamageIn;
            this.enchantability = enchantabilityIn;
            this.repairMaterial = new Lazy<>(repairMaterialIn);
        }


    @Override
    public int getDurability() {
        return this.durability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
        public Ingredient getRepairIngredient() {
            return this.repairMaterial.get();
        }
}
