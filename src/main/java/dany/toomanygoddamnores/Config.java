package dany.toomanygoddamnores;

import java.io.File;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

public class Config
{
	private static Configuration config;
	public static ConfigCategory replacements;
	public static ConfigCategory exclusions;
	public static ConfigCategory itemReplacements;
	public static int mergeItemsEvery;
	
	public static void initConfiguration(File file)
	{
		config = new Configuration(file);
	}
	
	public static void reloadConfiguration()
	{
		config.load();
		
		replacements = config.getCategory("replacements");
		exclusions = config.getCategory("exclusions");
		itemReplacements = config.getCategory("item replacements");
		mergeItemsEvery = config.getInt("Merge Items Every X Ticks", Refs.MOD_ID, 100, 1, 60 * 20, "Try increasing this value if you experience lag");
		
		if (config.hasChanged())
		{
			config.save();
		}
	}
	
	public static Configuration config()
	{
		return config;
	}
}