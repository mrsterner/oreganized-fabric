package dev.mrsterner.oreganized.common.item;

import dev.mrsterner.oreganized.common.registry.OMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

public class OPickaxeItem extends PickaxeItem {

    public OPickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
}
