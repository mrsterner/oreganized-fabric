package dev.mrsterner.oreganized.common.item;

import dev.mrsterner.oreganized.common.registry.OObjects;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class ElectrumArmorItem extends ArmorItem {
    private static final ElectrumArmorMaterial material = new ElectrumArmorMaterial();
    public ElectrumArmorItem(EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }



    private static class ElectrumArmorMaterial implements ArmorMaterial {
        private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};

        @Override
        public int getProtectionAmount(EquipmentSlot slot) {
            int[] defensePerSlot = {3, 6, 8, 3};
            return defensePerSlot[slot.getEntitySlotId()];
        }


        @Override
        public int getDurability(EquipmentSlot slot) {
            return HEALTH_PER_SLOT[slot.getEntitySlotId()] * 37;
        }

        @Override
        public int getEnchantability() {
            return 15;
        }


        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ITEM_ARMOR_EQUIP_CHAIN;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(OObjects.ELECTRUM_INGOT);
        }

        @Override
        public String getName() {
            return "electrum";
        }

        @Override
        public float getToughness() {
            return 3.0F;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.0F;
        }
    }
}
