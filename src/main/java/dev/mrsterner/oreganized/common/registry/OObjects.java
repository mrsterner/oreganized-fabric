package dev.mrsterner.oreganized.common.registry;

import dev.mrsterner.oreganized.Oreganized;
import dev.mrsterner.oreganized.common.block.*;
import dev.mrsterner.oreganized.common.item.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static dev.mrsterner.oreganized.common.registry.OSounds.*;
import static net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.copyOf;

public class OObjects {

    private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
    private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();



    public static final Item SILVER_INGOT = register("silver_ingot", new SilverIngot(gen()));
    public static final Item LEAD_INGOT = register("lead_ingot", new Item(gen()));
    public static final Item ELECTRUM_INGOT = register("electrum_ingot", new Item(gen()));

    public static final Item RAW_SILVER = register("raw_silver", new Item(gen()));
    public static final Item RAW_LEAD = register("raw_lead", new Item(gen()));

    public static final Item SILVER_NUGGET = register("silver_nugget", new Item(gen()));
    public static final Item LEAD_NUGGET = register("lead_nugget", new Item(gen()));
    public static final Item ELECTRUM_NUGGET = register("electrum_nugget", new Item(gen()));
    public static final Item NETHERITE_NUGGET = register("netherite_nugget", new Item(gen()));

    public static final Item MOLTEN_LEAD_BUCKET = register("molten_lead_bucket", new PowderSnowBucketItem(OObjects.MOLTEN_LEAD_BLOCK, SoundEvents.ITEM_BUCKET_EMPTY_LAVA, gen()));

    public static final Item SILVER_MIRROR = register("silver_mirror", new SilverMirrorItem(gen()));
    //Music Discs
    public static final Item MUSIC_DISC_PILLAGED_ITEM = register("music_disc_pillaged", new OMusicDisc(13, MUSIC_DISC_PILLAGED, gen()));
    public static final Item MUSIC_DISC_18_ITEM = register("music_disc_18", new OMusicDisc(14, MUSIC_DISC_18, gen()));
    public static final Item MUSIC_DISC_SHULK_ITEM = register("music_disc_shulk", new OMusicDisc(15, MUSIC_DISC_SHULK, gen()));

    public static final Block SILVER_ORE = register("silver_ore", new Block(copyOf(Blocks.GOLD_ORE)), true);
    public static final Block DEEPSLATE_SILVER_ORE = register("deepslate_silver_ore", new Block(copyOf(Blocks.DEEPSLATE_GOLD_ORE)), true);

    public static final Block LEAD_ORE = register("lead_ore", new Block(copyOf(Blocks.GOLD_ORE)), true);
    public static final Block DEEPSLATE_LEAD_ORE = register("deepslate_lead_ore", new Block(copyOf(Blocks.DEEPSLATE_GOLD_ORE)), true);

    public static final Block RAW_SILVER_BLOCK = register("raw_silver_block", new Block(copyOf(Blocks.RAW_IRON_BLOCK)), true);
    public static final Block RAW_LEAD_BLOCK = register("raw_lead_block", new Block(copyOf(Blocks.RAW_IRON_BLOCK)), true);
    public static final Block SILVER_BLOCK = register("silver_block", new SilverBlock(copyOf(Blocks.IRON_BLOCK)), true);
    public static final Block LEAD_BLOCK = register("lead_block", new Block(copyOf(Blocks.IRON_BLOCK).requiresTool().strength(5.0F, 6.0F)), true);

    public static final Block ELECTRUM_BLOCK = register("electrum_block", new Block(copyOf(Blocks.IRON_BLOCK).requiresTool().strength(5.0F, 6.0F)), true);

    public static final Block GLANCE = register("glance", new Block(copyOf(Blocks.DIORITE)), true);
    public static final Block GLANCE_SLAB = register("glance_slab", new SlabBlock(copyOf(GLANCE)), true);
    public static final Block GLANCE_BRICKS_SLAB = register("glance_brick_slab", new SlabBlock(copyOf(GLANCE)), true);
    public static final Block GLANCE_STAIRS = register("glance_stairs", new StairBlock(GLANCE, copyOf(GLANCE)), true);

    public static final Block GLANCE_BRICKS_STAIRS = register("glance_bricks_stairs", new StairBlock(GLANCE, copyOf(GLANCE)), true);
    public static final WallBlock GLANCE_WALL = register("glance_wall", new WallBlock(copyOf(GLANCE)), true);
    public static final WallBlock GLANCE_BRICKS_WALL = register("glance_bricks_wall", new WallBlock(copyOf(GLANCE)), true);

    public static final Block POLISHED_GLANCE = register("polished_glance", new StairBlock(GLANCE, copyOf(GLANCE)), true);
    public static final Block GLANCE_BRICKS = register("glance_bricks", new StairBlock(GLANCE, copyOf(GLANCE)), true);
    public static final Block CHISELED_GLANCE = register("chiseled_glance", new StairBlock(GLANCE, copyOf(GLANCE)), true);
    public static final Block SPOTTED_GLANCE = register("spotted_glance", new SpottedGlance(copyOf(GLANCE)), true);
    public static final Block WAXED_SPOTTED_GLANCE = register("waxed_spotted_glance", new Block(copyOf(SPOTTED_GLANCE)), true);
    public static final Block MOLTEN_LEAD_BLOCK = register("molten_lead_block", new MoltenLeadBlock(), true);
    public static final Block LEAD_CAULDRON = register("cauldron", new ModCauldron(copyOf(GLANCE)), true);



    public static final Block ENGRAVED_NETHER_BRICKS = register("engraved_nether_bricks", new EngravedBlock(copyOf(Blocks.NETHER_BRICKS).strength(2.0F, 6.0F)), true);
    public static final Block ENGRAVED_RED_NETHER_BRICKS = register("engraved_red_nether_bricks", new EngravedBlock(copyOf(Blocks.NETHER_BRICKS).strength(2.0F, 6.0F)), true);
    public static final Block ENGRAVED_POLISHED_BLACKSTONE_BRICKS = register("engraved_polished_blackstone_bricks", new EngravedBlock(copyOf(Blocks.POLISHED_BLACKSTONE_BRICKS)), true);
    public static final Block ENGRAVED_BRICKS = register("engraved_bricks", new EngravedBlock(copyOf(Blocks.BRICKS)), true);

    public static final Block ENGRAVED_WAXED_OXIDIZED_CUT_COPPER = register("engraved_waxed_oxidized_cut_copper", new CopperEngravedBlock(copyOf(Blocks.OXIDIZED_CUT_COPPER)), true);
    public static final Block ENGRAVED_WAXED_WEATHERED_CUT_COPPER = register("engraved_waxed_weathered_cut_copper", new CopperEngravedBlock(copyOf(Blocks.WEATHERED_CUT_COPPER)), true);
    public static final Block ENGRAVED_WAXED_EXPOSED_CUT_COPPER = register("engraved_waxed_exposed_cut_copper", new CopperEngravedBlock(copyOf(Blocks.EXPOSED_CUT_COPPER)), true);
    public static final Block ENGRAVED_WAXED_CUT_COPPER = register("engraved_waxed_cut_copper", new CopperEngravedBlock(copyOf(Blocks.CUT_COPPER)), true);
    public static final Block ENGRAVED_CUT_COPPER = register("engraved_cut_copper", new OxidizableBlock(Oxidizable.OxidationLevel.UNAFFECTED, AbstractBlock.Settings.of(Material.METAL, MapColor.ORANGE).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER)), true);
    public static final Block ENGRAVED_EXPOSED_CUT_COPPER = register("engraved_exposed_cut_copper", new OxidizableBlock(Oxidizable.OxidationLevel.EXPOSED, AbstractBlock.Settings.of(Material.METAL, MapColor.TERRACOTTA_LIGHT_GRAY).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER)), true);
    public static final Block ENGRAVED_WEATHERED_CUT_COPPER = register("engraved_weathered_cut_copper", new OxidizableBlock(Oxidizable.OxidationLevel.WEATHERED, AbstractBlock.Settings.of(Material.METAL, MapColor.DARK_AQUA).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER)), true);
    public static final Block ENGRAVED_OXIDIZED_CUT_COPPER = register("engraved_oxidized_cut_copper", new OxidizableBlock(Oxidizable.OxidationLevel.OXIDIZED, AbstractBlock.Settings.of(Material.METAL, MapColor.TEAL).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER)), true);


    public static final Block ENGRAVED_DEEPSLATE_BRICKS = register("engraved_deepslate_bricks", new EngravedBlock(copyOf(Blocks.DEEPSLATE_BRICKS)), true);
    public static final Block ENGRAVED_END_STONE_BRICKS = register("engraved_end_stone_bricks", new EngravedBlock(copyOf(Blocks.END_STONE_BRICKS)), true);
    public static final Block ENGRAVED_PRISMARINE_BRICKS = register("engraved_prismarine_bricks", new EngravedBlock(copyOf(Blocks.PRISMARINE_BRICKS)), true);
    public static final Block ENGRAVED_QUARTZ_BRICKS = register("engraved_quartz_bricks", new EngravedBlock(copyOf(Blocks.QUARTZ_BRICKS)), true);
    public static final Block ENGRAVED_STONE_BRICKS = register("engraved_stone_bricks", new EngravedBlock(copyOf(Blocks.STONE_BRICKS)), true);
    public static final Block ENGRAVED_GLANCE_BRICKS = register("engraved_glance_bricks", new EngravedBlock(copyOf(GLANCE_BRICKS)), true);


    public static final Block BLACK_CRYSTAL_GLASS = register("black_crystal_glass", new CrystalGlassColoredBlock(copyOf(Blocks.GLASS), DyeColor.BLACK), true);
    public static final Block BLUE_CRYSTAL_GLASS = register("blue_crystal_glass", new CrystalGlassColoredBlock(copyOf(Blocks.GLASS), DyeColor.BLUE), true);
    public static final Block BROWN_CRYSTAL_GLASS = register("brown_crystal_glass", new CrystalGlassColoredBlock(copyOf(Blocks.GLASS), DyeColor.BROWN), true);
    public static final Block CYAN_CRYSTAL_GLASS = register("cyan_crystal_glass", new CrystalGlassColoredBlock(copyOf(Blocks.GLASS), DyeColor.CYAN), true);
    public static final Block GRAY_CRYSTAL_GLASS = register("gray_crystal_glass", new CrystalGlassColoredBlock(copyOf(Blocks.GLASS), DyeColor.GRAY), true);
    public static final Block GREEN_CRYSTAL_GLASS = register("green_crystal_glass", new CrystalGlassColoredBlock(copyOf(Blocks.GLASS), DyeColor.GREEN), true);
    public static final Block LIGHT_BLUE_CRYSTAL_GLASS = register("light_blue_crystal_glass", new CrystalGlassColoredBlock(copyOf(Blocks.GLASS), DyeColor.LIGHT_BLUE), true);
    public static final Block LIGHT_GRAY_CRYSTAL_GLASS = register("light_gray_crystal_glass", new CrystalGlassColoredBlock(copyOf(Blocks.GLASS), DyeColor.LIGHT_GRAY), true);
    public static final Block LIME_CRYSTAL_GLASS = register("lime_crystal_glass", new CrystalGlassColoredBlock(copyOf(Blocks.GLASS), DyeColor.LIME), true);
    public static final Block MAGENTA_CRYSTAL_GLASS = register("magenta_crystal_glass", new CrystalGlassColoredBlock(copyOf(Blocks.GLASS), DyeColor.MAGENTA), true);
    public static final Block ORANGE_CRYSTAL_GLASS = register("orange_crystal_glass", new CrystalGlassColoredBlock(copyOf(Blocks.GLASS), DyeColor.ORANGE), true);
    public static final Block PINK_CRYSTAL_GLASS = register("pink_crystal_glass", new CrystalGlassColoredBlock(copyOf(Blocks.GLASS), DyeColor.PINK), true);
    public static final Block PURPLE_CRYSTAL_GLASS = register("purple_crystal_glass", new CrystalGlassColoredBlock(copyOf(Blocks.GLASS), DyeColor.PURPLE), true);
    public static final Block RED_CRYSTAL_GLASS = register("red_crystal_glass", new CrystalGlassColoredBlock(copyOf(Blocks.GLASS), DyeColor.RED), true);
    public static final Block WHITE_CRYSTAL_GLASS = register("white_crystal_glass", new CrystalGlassColoredBlock(copyOf(Blocks.GLASS), DyeColor.WHITE), true);
    public static final Block YELLOW_CRYSTAL_GLASS = register("yellow_crystal_glass", new CrystalGlassColoredBlock(copyOf(Blocks.GLASS), DyeColor.YELLOW), true);

    public static final Block BLACK_CRYSTAL_GLASS_PANE = register("black_crystal_glass_pane", new CrystalGlassPaneColoredBlock(DyeColor.BLACK), true);
    public static final Block BLUE_CRYSTAL_GLASS_PANE = register("blue_crystal_glass_pane", new CrystalGlassPaneColoredBlock(DyeColor.BLUE), true);
    public static final Block BROWN_CRYSTAL_GLASS_PANE = register("brown_crystal_glass_pane", new CrystalGlassPaneColoredBlock(DyeColor.BROWN), true);
    public static final Block CYAN_CRYSTAL_GLASS_PANE = register("cyan_crystal_glass_pane", new CrystalGlassPaneColoredBlock(DyeColor.CYAN), true);
    public static final Block GRAY_CRYSTAL_GLASS_PANE = register("gray_crystal_glass_pane", new CrystalGlassPaneColoredBlock(DyeColor.GRAY), true);
    public static final Block GREEN_CRYSTAL_GLASS_PANE = register("green_crystal_glass_pane", new CrystalGlassPaneColoredBlock(DyeColor.GREEN), true);
    public static final Block LIGHT_BLUE_CRYSTAL_GLASS_PANE = register("light_blue_crystal_glass_pane", new CrystalGlassPaneColoredBlock(DyeColor.LIGHT_BLUE), true);
    public static final Block LIGHT_GRAY_CRYSTAL_GLASS_PANE = register("light_gray_crystal_glass_pane", new CrystalGlassPaneColoredBlock(DyeColor.LIGHT_GRAY), true);
    public static final Block LIME_CRYSTAL_GLASS_PANE = register("lime_crystal_glass_pane", new CrystalGlassPaneColoredBlock(DyeColor.LIME), true);
    public static final Block MAGENTA_CRYSTAL_GLASS_PANE = register("magenta_crystal_glass_pane", new CrystalGlassPaneColoredBlock(DyeColor.MAGENTA), true);
    public static final Block ORANGE_CRYSTAL_GLASS_PANE = register("orange_crystal_glass_pane", new CrystalGlassPaneColoredBlock(DyeColor.ORANGE), true);
    public static final Block PINK_CRYSTAL_GLASS_PANE = register("pink_crystal_glass_pane", new CrystalGlassPaneColoredBlock(DyeColor.PINK), true);
    public static final Block PURPLE_CRYSTAL_GLASS_PANE = register("purple_crystal_glass_pane", new CrystalGlassPaneColoredBlock(DyeColor.PURPLE), true);
    public static final Block RED_CRYSTAL_GLASS_PANE = register("red_crystal_glass_pane", new CrystalGlassPaneColoredBlock(DyeColor.RED), true);
    public static final Block WHITE_CRYSTAL_GLASS_PANE = register("white_crystal_glass_pane", new CrystalGlassPaneColoredBlock(DyeColor.WHITE), true);
    public static final Block YELLOW_CRYSTAL_GLASS_PANE = register("yellow_crystal_glass_pane", new CrystalGlassPaneColoredBlock(DyeColor.YELLOW), true);

    //Waxed Concrete Powder
    public static final FallingBlock WAXED_WHITE_CONCRETE_POWDER = register("waxed_white_concrete_powder", new FallingBlock(FabricBlockSettings.of(Material.SOIL, DyeColor.WHITE).strength(0.5F).sounds(BlockSoundGroup.SAND)), true);



    public static final FallingBlock WAXED_ORANGE_CONCRETE_POWDER = register("waxed_orange_concrete_powder", new FallingBlock(FabricBlockSettings.of(Material.SOIL, DyeColor.ORANGE).strength(0.5F).sounds(BlockSoundGroup.SAND)), true);
    public static final FallingBlock WAXED_MAGENTA_CONCRETE_POWDER = register("waxed_magenta_concrete_powder", new FallingBlock(FabricBlockSettings.of(Material.SOIL, DyeColor.MAGENTA).strength(0.5F).sounds(BlockSoundGroup.SAND)), true);
    public static final FallingBlock WAXED_LIGHT_BLUE_CONCRETE_POWDER = register("waxed_light_blue_concrete_powder", new FallingBlock(FabricBlockSettings.of(Material.SOIL, DyeColor.LIGHT_BLUE).strength(0.5F).sounds(BlockSoundGroup.SAND)), true);
    public static final FallingBlock WAXED_YELLOW_CONCRETE_POWDER = register("waxed_yellow_concrete_powder",  new FallingBlock(FabricBlockSettings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SAND)), true);
    public static final FallingBlock WAXED_LIME_CONCRETE_POWDER = register("waxed_lime_concrete_powder",  new FallingBlock(FabricBlockSettings.of(Material.SOIL, DyeColor.LIME).strength(0.5F).sounds(BlockSoundGroup.SAND)), true);
    public static final FallingBlock WAXED_PINK_CONCRETE_POWDER = register("waxed_pink_concrete_powder",  new FallingBlock(FabricBlockSettings.of(Material.SOIL, DyeColor.PINK).strength(0.5F).sounds(BlockSoundGroup.SAND)), true);
    public static final FallingBlock WAXED_GRAY_CONCRETE_POWDER = register("waxed_gray_concrete_powder", new FallingBlock(FabricBlockSettings.of(Material.SOIL, DyeColor.GRAY).strength(0.5F).sounds(BlockSoundGroup.SAND)), true);
    public static final FallingBlock WAXED_LIGHT_GRAY_CONCRETE_POWDER = register("waxed_light_gray_concrete_powder",  new FallingBlock(FabricBlockSettings.of(Material.SOIL, DyeColor.LIGHT_GRAY).strength(0.5F).sounds(BlockSoundGroup.SAND)), true);
    public static final FallingBlock WAXED_CYAN_CONCRETE_POWDER = register("waxed_cyan_concrete_powder",  new FallingBlock(FabricBlockSettings.of(Material.SOIL, DyeColor.CYAN).strength(0.5F).sounds(BlockSoundGroup.SAND)), true);
    public static final FallingBlock WAXED_PURPLE_CONCRETE_POWDER = register("waxed_purple_concrete_powder",  new FallingBlock(FabricBlockSettings.of(Material.SOIL, DyeColor.PURPLE).strength(0.5F).sounds(BlockSoundGroup.SAND)), true);
    public static final FallingBlock WAXED_BLUE_CONCRETE_POWDER = register("waxed_blue_concrete_powder",  new FallingBlock(FabricBlockSettings.of(Material.SOIL, DyeColor.BLUE).strength(0.5F).sounds(BlockSoundGroup.SAND)), true);
    public static final FallingBlock WAXED_BROWN_CONCRETE_POWDER = register("waxed_brown_concrete_powder",  new FallingBlock(FabricBlockSettings.of(Material.SOIL, DyeColor.BROWN).strength(0.5F).sounds(BlockSoundGroup.SAND)), true);
    public static final FallingBlock WAXED_GREEN_CONCRETE_POWDER = register("waxed_green_concrete_powder",  new FallingBlock(FabricBlockSettings.of(Material.SOIL, DyeColor.GREEN).strength(0.5F).sounds(BlockSoundGroup.SAND)), true);
    public static final FallingBlock WAXED_RED_CONCRETE_POWDER = register("waxed_red_concrete_powder",  new FallingBlock(FabricBlockSettings.of(Material.SOIL, DyeColor.RED).strength(0.5F).sounds(BlockSoundGroup.SAND)), true);
    public static final FallingBlock WAXED_BLACK_CONCRETE_POWDER = register("waxed_black_concrete_powder",  new FallingBlock(FabricBlockSettings.of(Material.SOIL, DyeColor.BLACK).strength(0.5F).sounds(BlockSoundGroup.SAND)), true);
    //Redstone Components
    public static final Block EXPOSER = register("exposer", new ExposerBlock(FabricBlockSettings.copy(Blocks.OBSERVER)), true);
    public static final Block SHRAPNEL_BOMB = register("shrapnel_bomb", new ShrapnelBombBlock(FabricBlockSettings.copy(Blocks.TNT)), true);

    public static final Item ELECTRUM_SWORD = register("electrum_sword", new SwordItem(OMaterials.ELECTRUM, 3, -2.6F, new Item.Settings().group(ItemGroup.COMBAT).maxCount(1)));

    public static final Item ELECTRUM_PICKAXE = register("electrum_pickaxe", new OPickaxeItem(OMaterials.ELECTRUM, 1, -2.8F, new Item.Settings().maxCount(1).group(ItemGroup.TOOLS)));
    public static final Item ELECTRUM_SHOVEL = register("electrum_shovel", new ShovelItem(OMaterials.ELECTRUM, 1.5F, -3.0F, new Item.Settings().maxCount(1).group(ItemGroup.TOOLS)));
    public static final Item ELECTRUM_AXE = register("electrum_axe", new OAxeItem(OMaterials.ELECTRUM, 5.0F, -3.0F, new Item.Settings().maxCount(1).group(ItemGroup.TOOLS)));
    public static final Item ELECTRUM_HOE = register("electrum_hoe", new OHoeItem(OMaterials.ELECTRUM, -4, 0.0F, new Item.Settings().maxCount(1).group(ItemGroup.TOOLS)));
    public static final Item BUSH_HAMMER = register("bush_hammer", new BushHammerItem());


    public static final Item ELECTRUM_HELMET = register("electrum_helmet", new ElectrumArmorItem(EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT)));
    public static final Item ELECTRUM_CHESTPLATE = register("electrum_chestplate", new ElectrumArmorItem(EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT)));
    public static final Item ELECTRUM_LEGGINGS = register("electrum_leggings", new ElectrumArmorItem(EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT)));
    public static final Item ELECTRUM_BOOTS = register("electrum_boots", new ElectrumArmorItem(EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT)));



    public static <T extends Block> T register(String name, T block, boolean createItem) {
        BLOCKS.put(block, new Identifier(Oreganized.MODID, name));
        if (createItem) {
            ITEMS.put(new BlockItem(block, gen()), BLOCKS.get(block));
        }
        return block;
    }

    private static <T extends Item> T register(String name, T item) {
        ITEMS.put(item, new Identifier(Oreganized.MODID, name));
        return item;
    }

    private static Item.Settings gen() {
        return new Item.Settings().group(Oreganized.OREGANIZED_GROUP);
    }

    public static void init() {
        BLOCKS.keySet().forEach(block -> Registry.register(Registry.BLOCK, BLOCKS.get(block), block));
        ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
    }
}
