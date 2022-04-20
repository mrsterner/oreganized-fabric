package dev.mrsterner.oreganized.common.registry;


import dev.mrsterner.oreganized.Oreganized;
import dev.mrsterner.oreganized.mixin.BrewingRecipeRegistryAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class OPotions {
    private static final Map<Potion, Identifier> POTIONS = new LinkedHashMap<>();

    public static final Potion STUNNING_POTION = register("stunned", new Potion(new StatusEffectInstance(OStatusEffects.STUNNED, 40 * 20)));
    public static final Potion STUNNING_POTION_LONG = register("stunned", new Potion(new StatusEffectInstance(OStatusEffects.STUNNED, 80 * 20)));
    public static final Potion STUNNING_POTION_POTENT = register("stunned", new Potion(new StatusEffectInstance(OStatusEffects.STUNNED, 40 * 20 , 1 )));

    private static <T extends Potion> T register(String name, T potion) {
        POTIONS.put(potion, new Identifier(Oreganized.MODID, name));
        return potion;
    }

    public static void init(){
        Registry.ITEM.stream().filter(item -> item.getDefaultStack().isIn(OTags.LEAD_INGOTS_ITEMTAG)).forEach(ingredients -> {
            BrewingRecipeRegistryAccessor.oreganized$registerPotionRecipe(Potions.AWKWARD, ingredients, OPotions.STUNNING_POTION);
            BrewingRecipeRegistryAccessor.oreganized$registerPotionRecipe(OPotions.STUNNING_POTION, ingredients, OPotions.STUNNING_POTION_LONG);
            BrewingRecipeRegistryAccessor.oreganized$registerPotionRecipe(OPotions.STUNNING_POTION, ingredients, OPotions.STUNNING_POTION_POTENT);
        });
    }
}
