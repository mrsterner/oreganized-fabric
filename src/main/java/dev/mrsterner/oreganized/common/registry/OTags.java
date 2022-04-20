package dev.mrsterner.oreganized.common.registry;

import dev.mrsterner.oreganized.Oreganized;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class OTags {

    public static final TagKey<Block> BUSH_HAMMER_BREAKABLE_BLOCKTAG = TagKey.of(Registry.BLOCK_KEY, new Identifier(Oreganized.MODID, "bush_hammer_breakable"));
    public static final TagKey<Block> ENGRAVEABLE_BLOCKTAG = TagKey.of(Registry.BLOCK_KEY, new Identifier(Oreganized.MODID, "engraveable"));
    public static final TagKey<Block> ENGRAVED_TEXTURED_BLOCKS_BLOCKTAG = TagKey.of(Registry.BLOCK_KEY, new Identifier(Oreganized.MODID, "engraved_textured_blocks"));
    public static final TagKey<Block> FIRE_SOURCE_BLOCKTAG = TagKey.of(Registry.BLOCK_KEY, new Identifier(Oreganized.MODID, "fire_source"));

    public static final TagKey<Item> LEAD_SOURCE_ITEMTAG = TagKey.of(Registry.ITEM_KEY, new Identifier(Oreganized.MODID, "lead_source"));
    public static final TagKey<Item> EDIBLE_ITEMTAG = TagKey.of(Registry.ITEM_KEY, new Identifier(Oreganized.MODID, "edible"));
    public static final TagKey<Item> LEAD_INGOTS_ITEMTAG = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "ingots/lead"));

}
