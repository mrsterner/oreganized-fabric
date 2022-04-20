package dev.mrsterner.oreganized;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Oreganized implements ModInitializer {
	public static final String MODID = "oreganized";
	public static final ItemGroup OREGANIZED_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(Blocks.ACACIA_BUTTON));
	public static OConfig config;
	public static final Logger LOGGER = LoggerFactory.getLogger("Oreganized");

	@Override
	public void onInitialize() {
		AutoConfig.register(OConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(OConfig.class).getConfig();

		LOGGER.info("Hello Fabric world!");
	}
}
